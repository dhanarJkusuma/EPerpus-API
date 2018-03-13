package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction;

import id.veintechnology.apps.library.id.veintechnology.apps.api.AuthenticatedMember;
import id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto.CompleteTransactionPayloadDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto.TransactionDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto.TransactionPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.common.PaginationData;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.BookService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.BookNotFoundException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.TransactionService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.BookNotAvailableException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.DuplicateTransactionCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionApi {

    private final BookService bookService;
    private final TransactionService trxService;
    private final Logger logger = LoggerFactory.getLogger(TransactionApi.class.getName());

    @Autowired
    public TransactionApi(BookService bookService, TransactionService trxService) {
        this.bookService = bookService;
        this.trxService = trxService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public synchronized ResponseEntity createTransaction(@RequestBody @Validated TransactionPayload payload, @AuthenticatedMember Member member){
        Map<String, Object> response = new HashMap<>();
        List<String> bookCodes = new ArrayList<>();
        List<Book> filteredBooks;
        payload.getBookCodes().forEach(b -> bookCodes.add(b.getBookCode()));
        try{
           filteredBooks = bookService.validateBook(bookCodes);
        }catch (BookNotFoundException e){
            logger.error(e.getMessage());
            response.put("status", false);
            response.put("message", "Code buku tidak diketahui : " + e.getCodes());
            return ResponseEntity.badRequest().body(response);
        }

        OrderTransaction transaction = TransactionMapper.toOrderTransaction(payload, member, filteredBooks);
        try{
            transaction = trxService.createTransaction(transaction, filteredBooks);
        }catch(DuplicateTransactionCodeException e){
            logger.error(e.getMessage());
            response.put("status", false);
            response.put("message", "Gagal menambahkan transaksi, transaksi dengan kode : " + e.getInvalidCode());
            return ResponseEntity.badRequest().body(response);
        }catch (BookNotAvailableException e){
            logger.error(e.getMessage());
            response.put("status", false);
            response.put("message", "Gagal menambahkan transaksi. Buku tidak tersedia, kode buku : " + e.getBookCode());
            return ResponseEntity.badRequest().body(response);
        }

        TransactionDto transactionResponse = TransactionMapper.toTransactionDto(transaction);
        response.put("status", true);
        response.put("message", "Berhasil menambahkan transaksi. ");
        response.put("data", transactionResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity retrieveTransaction(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "255") int size
    ){
        Page<OrderTransaction> transactions = trxService.retrieveTransaction(page, size);
        List<TransactionDto> transactionDtos = transactions.getContent().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
        PaginationData data = PaginationData.Builder.newBuilder()
                .contents(transactionDtos)
                .page(page)
                .size(size)
                .totalElements(transactions.getTotalPages())
                .build();
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/in-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> retrieveInCompleteTransaction(@AuthenticatedMember Member member){
        Page<OrderTransaction> transactionList = trxService.retrieveInCompleteTransactionByMember(member.getPublicId());
        return transactionList.getContent().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }

    @PutMapping(path = "/return/{publicId}")
    public ResponseEntity completeTransaction(
            @AuthenticatedMember Member member,
            @RequestBody @Validated CompleteTransactionPayloadDto payloadDto,
            @PathVariable("publicId") String publicId
    ){
        OrderTransaction transaction;
        try{
            transaction = trxService.completeTransaction(publicId, payloadDto.getReturnDate());
        }catch (TransactionNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Berhasil menyelesaikan transaksi. ");
        response.put("data", TransactionMapper.toTransactionDto(transaction));
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{transactionId}/approved")
    public ResponseEntity approveTransactionReturn(
            @PathVariable("transactionId") String transactionId
    ){
        OrderTransaction transaction;
        try{
            transaction = trxService.approveTransaction(transactionId);
        }catch (TransactionNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Berhasil menyetujui pengembalian transaksi. ");
        response.put("data", TransactionMapper.toTransactionDto(transaction));
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findTransaction(
            @PathVariable("transactionId") String transactionId
    ){
        OrderTransaction transaction;
        try{
            transaction = trxService.findTransaction(transactionId);
        }catch (TransactionNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping(path = "/{transactionId}")
    public ResponseEntity deleteTransaction(
            @PathVariable("transactionId") String transactionId
    ){
        OrderTransaction deletedTransaction;
        Map<String, Object> response = new HashMap<>();

        try{
            deletedTransaction = trxService.destroyTransaction(transactionId);
        }catch(TransactionNotFoundException e){
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
        response.put("success", true);
        response.put("message", "Berhasil menghapus transaksi dengan code : " + transactionId);
        response.put("data", deletedTransaction);
        return ResponseEntity.ok(response);
    }


}
