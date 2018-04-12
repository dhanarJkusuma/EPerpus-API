package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.BookNotAvailableException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.DuplicateTransactionCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception.TransactionNotFoundException;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;

import java.time.ZonedDateTime;
import java.util.List;

public interface TransactionService {
    OrderTransaction createTransaction(OrderTransaction transaction, List<Book> books) throws DuplicateTransactionCodeException, BookNotAvailableException;
    OrderTransaction completeTransaction(String transactionId, ZonedDateTime returnZonedDate) throws TransactionNotFoundException;
    OrderTransaction approveTransaction(String transactionId) throws TransactionNotFoundException;
    OrderTransaction findTransaction(String publicId) throws TransactionNotFoundException;
    OrderTransaction destroyTransaction(String publicId) throws TransactionNotFoundException;
    Page<OrderTransaction> retrieveTransaction(int page, int size);
    Page<OrderTransaction> retrieveInCompleteTransactionByMember(String member);
    Page<OrderTransaction> retrieveTransactionPending();
    Page<OrderTransaction> retrieveTransactionBetween(ZonedDateTime from, ZonedDateTime to);
}
