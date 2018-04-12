package id.veintechnology.apps.library.id.veintechnology.apps.service.book;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.BookNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface BookService {

    Book createNewBook(Book book);

    Book findByCode(String code);

    Book findById(Long bookId);

    List<Book> findByCodes(List<String> codes);

    Page<Book> retrieveBook(int size, int page);

    Book updateBook(Book book);

    Book borrowBook(Book book);

    void updateStockBooks(Book book, int quantity);

    void addStockByBookId(Long bookId, int quantity);

    void subtractStockByBookId(Long bookId, int quantity);

    Book destroyBook(Book book);

    Page<Book> searchBook(String query, int size, int page);

    List<Book> validateBook(List<String> codes) throws BookNotFoundException;

    Page<Book> fetchBookByCategory(String categoryCode, int size, int page);

}
