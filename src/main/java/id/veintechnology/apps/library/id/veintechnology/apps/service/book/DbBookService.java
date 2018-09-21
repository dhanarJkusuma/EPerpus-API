package id.veintechnology.apps.library.id.veintechnology.apps.service.book;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.BookRepository;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.DuplicateBookCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.BookNotFoundException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class DbBookService implements BookService{

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    @Autowired
    public DbBookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Book createNewBook(Book book, Set<String> categoryCodes) throws RuntimeException {
        if(categoryCodes.size() > 0){
            Set<Category> categories = categoryService.findByCategoryCodes(categoryCodes);
            if(categories.size() != categoryCodes.size()){
                throw new DataIntegrityViolationException("Failed to insert new book, some category doesn't exist.");
            }
        }
        Book existBook = bookRepository.findFirstByCode(book.getCode());
        if(existBook != null){
            throw new DuplicateBookCodeException(existBook.getCode());
        }
        return bookRepository.save(book);
    }

    @Override
    public Book removeAllCategories(Book book) {
        book.getCategories().removeAll(book.getCategories());
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book findByCode(String code) {
        return bookRepository.findFirstByCode(code);
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> findByCodes(List<String> codes) {
        return bookRepository.findByCodeIn(codes);
    }

    @Override
    public Page<Book> retrieveBook(int size, int page) {
        Sort sort = new Sort(Sort.Direction.ASC, "title");
        Pageable pageRequest = new PageRequest(page, size, sort);
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public Book updateBook(Book book, Set<String> categoryCodes) {
        Set<Category> categories = new HashSet<>();
        // check existence of categories by categoryCodes
        if(categoryCodes.size() > 0){
            categories = categoryService.findByCategoryCodes(categoryCodes);
            if(categories.size() != categoryCodes.size()){
                throw new DataIntegrityViolationException("Failed to insert new book, some category doesn't exist.");
            }
        }

        // delete old relation by categories
        book = removeAllCategories(book);

        // attach new many2many relation
        if(categories.size() > 0){
            book.setCategories(categories);
        }
        return bookRepository.save(book);
    }

    @Override
    public Book borrowBook(Book book) {
        book.setStock(book.getStock() - 1);
        return bookRepository.save(book);
    }

    @Override
    public void updateStockBooks(Book book, int quantity) {
        bookRepository.updateStock(book.getId(), quantity);
    }

    @Override
    public void addStockByBookId(Long bookId, int quantity) {
        bookRepository.addStock(bookId, quantity);
    }

    @Override
    public void subtractStockByBookId(Long bookId, int quantity) {
        bookRepository.subtractStock(bookId, quantity);
    }

    @Override
    public Book destroyBook(Book book) {
        bookRepository.delete(book);
        return book;
    }

    @Override
    public Page<Book> searchBook(String query, int size, int page) {
        Pageable pageRequest = new PageRequest(page, size);
        return bookRepository.searchBookByTitle(query, pageRequest);
    }

    @Override
    public List<Book> validateBook(List<String> codes) throws BookNotFoundException {
        Set<Book> existBook = bookRepository.findByCodes(codes);
        Set<String> existBookCode = existBook.stream().map(Book::getCode).collect(Collectors.toSet());
        Set<String> setQueryCodes = new HashSet<>(codes);
        setQueryCodes.removeAll(existBookCode);
        if(setQueryCodes.size() > 0){
            String unknownCodes = String.join(",", setQueryCodes);
            throw new BookNotFoundException(unknownCodes);
        }
        return new ArrayList<>(existBook);
    }

    @Override
    public Page<Book> fetchBookByCategory(String categoryCode, int size, int page) {
        Pageable pageable = new PageRequest(page, size);
        return bookRepository.findByCategoryCode(categoryCode, pageable);
    }
}
