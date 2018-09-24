package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.OrderTransactionRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.BookService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.BookNotAvailableException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.DuplicateTransactionCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DbTransactionService implements TransactionService{

    private final BookService bookService;
    private final OrderTransactionRepository orderTransactionRepository;
    private final GeneratorTransactionIdSequenceService generatorTransactionIdSequenceService;

    @Autowired
    public DbTransactionService(
            BookService bookService,
            OrderTransactionRepository orderTransactionRepository,
            GeneratorTransactionIdSequenceService generatorTransactionIdSequenceService
    ) {
        this.bookService = bookService;
        this.orderTransactionRepository = orderTransactionRepository;
        this.generatorTransactionIdSequenceService = generatorTransactionIdSequenceService;
    }

    @Override
    public synchronized OrderTransaction createTransaction(OrderTransaction transaction, List<Book> bookList) {
        if(transaction.getPublicId() == null){
            String transactionId = generatorTransactionIdSequenceService.nextIdSequence();
            transaction.setPublicId(transactionId);
        }
        OrderTransaction existTransaction = orderTransactionRepository.findByPublicId(transaction.getPublicId());
        if(existTransaction != null){
            throw new DuplicateTransactionCodeException(transaction.getPublicId());
        }

        transaction.getItems().forEach(i -> {
            Book book = bookService.findById(i.getBookId());
            if(book.getStock() < i.getQuantity()){
                throw new BookNotAvailableException(book.getCode());
            }
        });

        // replace with trx
        OrderTransaction saved = orderTransactionRepository.save(transaction);
        if(saved != null){
           saved.getItems().forEach(i -> bookService.subtractStockByBookId(i.getBookId(), i.getQuantity()));
        }
        return saved;
    }

    @Override
    public synchronized OrderTransaction completeTransaction(String transactionId, ZonedDateTime returnZonedDate) {
        OrderTransaction transaction = orderTransactionRepository.findByPublicId(transactionId);
        if(transaction == null){
            throw new TransactionNotFoundException(transactionId);
        }
        Date returnDate = Date.from(returnZonedDate.toInstant());
        transaction.setReturnDate(returnDate);
        return orderTransactionRepository.save(transaction);
    }

    @Override
    public OrderTransaction approveTransaction(String transactionId) throws TransactionNotFoundException {
        OrderTransaction transaction = orderTransactionRepository.findByPublicId(transactionId);
        if(transaction == null){
            throw new TransactionNotFoundException(transactionId);
        }
        transaction.setIsApproved(true);

        transaction.getItems().forEach(item -> {
            bookService.addStockByBookId(item.getBookId(), item.getQuantity());
        });
        return orderTransactionRepository.save(transaction);
    }

    @Override
    public OrderTransaction findTransaction(String publicId) {
        OrderTransaction transaction = orderTransactionRepository.findByPublicId(publicId);
        if(transaction == null){
            throw new TransactionNotFoundException(publicId);
        }
        return transaction;
    }

    @Override
    public OrderTransaction destroyTransaction(String publicId) {
        OrderTransaction existTransaction = orderTransactionRepository.findByPublicId(publicId);
        if(existTransaction == null){
            throw new TransactionNotFoundException(publicId);
        }
        orderTransactionRepository.delete(existTransaction);
        return existTransaction;
    }

    @Override
    public Page<OrderTransaction> retrieveTransaction(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdOn");
        Pageable pageable = new PageRequest(page, size, sort);
        return orderTransactionRepository.findAll(pageable);
    }

    @Override
    public Page<OrderTransaction> retrieveInCompleteTransactionByMember(String member) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdOn");
        Pageable pageable = new PageRequest(0, 255, sort);
        return orderTransactionRepository.findInCompleteTransactionByMemberId(member, pageable);
    }

    @Override
    public Page<OrderTransaction> retrieveTransactionPending() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdOn");
        Pageable pageable = new PageRequest(0, 255, sort);
        return orderTransactionRepository.findAllInCompleteTransactionBy(pageable);
    }

    @Override
    public Page<OrderTransaction> retrieveTransactionBetween(ZonedDateTime from, ZonedDateTime to) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdOn");
        Pageable pageable = new PageRequest(0, 1000, sort);
        Date start = Date.from(from.toInstant());
        Date end = Date.from(to.toInstant());
        return orderTransactionRepository.findCompleteHistoryTransaction(start, end, pageable);
    }


}
