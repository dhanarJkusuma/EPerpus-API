package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import id.veintechnology.apps.library.id.veintechnology.apps.api.AuthenticatedMember;
import id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto.TransactionDto;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.TransactionService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.TransactionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/v1/transaction")
public class TransactionAdminApi {

    private final TransactionService trxService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public TransactionAdminApi(TransactionService trxService) {
        this.trxService = trxService;
    }

    @GetMapping(path = "/in-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> retrieveInCompleteTransaction(){
        Page<OrderTransaction> transactionList = trxService.retrieveTransactionPending();
        return transactionList.getContent().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }

    @PutMapping(path = "/{transactionId}/approve")
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
        response.put("message", "Transaction has been approved successfully.");
        response.put("data", TransactionMapper.toTransactionDto(transaction));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/history")
    public List<TransactionDto> retriveTransaction(
            @RequestParam(name = "from") String from,
            @RequestParam(name = "to") String to
    ){
        ZonedDateTime start = ZonedDateTime.parse(from);
        ZonedDateTime end = ZonedDateTime.parse(to);
        Page<OrderTransaction> transactionList = trxService.retrieveTransactionBetween(start, end);
        return transactionList.getContent().stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }

}
