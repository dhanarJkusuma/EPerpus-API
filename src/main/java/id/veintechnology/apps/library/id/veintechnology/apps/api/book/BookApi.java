package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.BookDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.UpdateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.error_handler.BaseErrorHandler;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.BookNotFoundException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.exception.CategoryNotFoundException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.BookService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.DuplicateBookCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.CategoryService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.storage.StorageService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.storage.exception.StorageErrorException;
import javassist.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/book")
public class BookApi extends BaseErrorHandler {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final StorageService storageService;

    public BookApi(BookService bookService, CategoryService categoryService, StorageService storageService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity retrieveBook(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "255") int size
    ){
        Page<Book> results = bookService.retrieveBook(size, page);
        List<BookDto> data = results.getContent().stream().map(r -> {
            r = bookService.fillCoverImage(r);
            return BookMapper.toBookDto(r);
        }).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", results.getTotalPages());
        response.put("totalElements", results.getTotalElements());
        response.put("page", results.getNumber());
        response.put("size", results.getSize());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBook(@RequestBody @Valid CreateBookPayload payload){
        Map<String, Object> response = new HashMap<>();
        Book book = BookMapper.toBookDao(payload);
        try{
            Book createdBook = bookService.createNewBook(book, payload.getCategoryCode());
            response.put("success", true);
            response.put("message", "Book inserted successfully. ");
            response.put("data", createdBook);
            return ResponseEntity.ok(response);
        }catch (DuplicateBookCodeException e){
            response.put("success", false);
            response.put("message", "Duplicate Book, Book's code is already exist. ");
            return new ResponseEntity<>(response, BAD_REQUEST);
        }catch (DataIntegrityViolationException e){
            response.put("success", false);
            response.put("message", "Failed to insert new book, some category doesn't exist.");
            return new ResponseEntity<>(response, BAD_REQUEST);
        }catch (RuntimeException e){
            response.put("success", false);
            response.put("message", "Internal Server Error");
            return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/upload/{code}")
    public ResponseEntity uploadImage(
            @PathVariable("code") String code,
            @RequestParam("cover") MultipartFile file
    ){
        Map<String, Object> response = new HashMap<>();
        Book updatedBook = bookService.uploadCover(code, file);
        try {
            response.put("success", true);
            response.put("message", "Book updated successfully.");
            response.put("data", BookMapper.toBookDto(updatedBook));
            return ResponseEntity.ok(response);
        }catch (BookNotFoundException e){
            response.put("success", false);
            response.put("message", "Book not found. ");
            return new ResponseEntity<>(response, NOT_FOUND);
        }catch (StorageErrorException e){
            response.put("success", false);
            response.put("message", "Internal Server Error");
            return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{code}")
    public ResponseEntity updateBook(@PathVariable("code") String code, @RequestBody @Valid UpdateBookPayload payload){
        Book book = bookService.findByCode(code);
        if(book == null){
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();

        int deltaStock = book.getTotalStock() - payload.getTotalStock();

        book = BookMapper.toBookDao(book, payload);
        book.setTotalStock(deltaStock);

        Book updatedBook = bookService.updateBook(book, payload.getCategoryCode());
        try {
            response.put("success", true);
            response.put("message", "Book updated successfully.");
            response.put("data", BookMapper.toBookDto(updatedBook));
            return ResponseEntity.ok(response);
        }catch (DataIntegrityViolationException e){
            response.put("success", false);
            response.put("message", "Failed to update book, some category doesn't exist.");
            return new ResponseEntity<>(response, BAD_REQUEST);
        }catch (RuntimeException e){
            response.put("success", false);
            response.put("message", "Internal Server Error");
            return new ResponseEntity<>(response, INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/{code}")
    public ResponseEntity detailBook(@PathVariable("code") String code){
        Book book = bookService.findByCode(code);
        if(book == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BookMapper.toBookDto(book));
    }

    @DeleteMapping(path = "/{code}")
    public ResponseEntity deleteBook(@PathVariable("code") String code){
        Book book = bookService.findByCode(code);
        if(book == null){
            return ResponseEntity.notFound().build();
        }
        bookService.destroyBook(book);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/search")
    public ResponseEntity searchBook(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "size", defaultValue = "255") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ){
        Page<Book> results = bookService.searchBook(query, size, page);
        List<BookDto> data = results.getContent().stream().map(r -> {
            r = bookService.fillCoverImage(r);
            return BookMapper.toBookDto(r);
        }).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", results.getTotalPages());
        response.put("totalElements", results.getTotalElements());
        response.put("page", results.getNumber());
        response.put("size", results.getSize());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/category/{categoryCode}")
    public ResponseEntity getByCategory(
            @PathVariable(name = "categoryCode") String category,
            @RequestParam(name = "size", defaultValue = "255") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ){
        Page<Book> results = bookService.fetchBookByCategory(category, size, page);
        List<BookDto> data = results.getContent().stream().map(r -> {
            r = bookService.fillCoverImage(r);
            return BookMapper.toBookDto(r);
        }).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", results.getTotalPages());
        response.put("totalElements", results.getTotalElements());
        response.put("page", results.getNumber());
        response.put("size", results.getSize());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    private String collectUnknownCategory(Set<String> categoryCodes){
        StringBuffer sb = new StringBuffer();
        for(String code: categoryCodes){
            sb.append(code);
            sb.append(", ");
        }
        sb.delete(sb.length()-2, sb.length());
        return sb.toString();
    }
}
