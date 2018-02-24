package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.BookDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.UpdateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.BookService;
import id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception.DuplicateBookCodeException;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/book")
public class BookApi {
    private final BookService bookService;
    private final CategoryService categoryService;

    public BookApi(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity retrieveBook(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "255") int size
    ){
        Page<Book> results = bookService.retrieveBook(size, page);
        List<BookDto> data = results.getContent().stream().map(BookMapper::toBookDto).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", results.getTotalPages());
        response.put("totalElements", results.getTotalElements());
        response.put("page", results.getNumber());
        response.put("size", results.getSize());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBook(@RequestBody @Validated CreateBookPayload payload){
        Set<Category> categories = categoryService.findByCategoryCodes(payload.getCategoryCode());
        Set<String> categoryCodes = new HashSet<>(payload.getCategoryCode());
        Map<String, Object> response = new HashMap<>();

        if(categories.size() != categoryCodes.size()){
            Set<String> foundedCategories = categories.stream().map(Category::getCode).collect(Collectors.toSet());
            categoryCodes.removeAll(foundedCategories);

            response.put("success", false);
            response.put("message", "Gagal menambahkan buku, Beberapa kategori tidak ditemukan. kode kategori: " + collectUnknownCategory(categoryCodes));
            return ResponseEntity.badRequest().body(response);
        }
        Book book = BookMapper.toBookDao(payload);
        book.setCategories(categories);
        try{
            Book createdBook = bookService.createNewBook(book);
            response.put("success", true);
            response.put("message", "Berhasil menambahkan data buku. ");
            response.put("data", BookMapper.toBookDto(createdBook));
            return ResponseEntity.ok(response);
        }catch (DuplicateBookCodeException e){
            response.put("success", false);
            response.put("message", "Gagal menambahkan data buku, " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping(path = "/{code}")
    public ResponseEntity updateBook(@PathVariable("code") String code, @RequestBody @Validated UpdateBookPayload payload){
        Book book = bookService.findByCode(code);

        if(book == null){
            return ResponseEntity.notFound().build();
        }

        Set<Category> categories = categoryService.findByCategoryCodes(payload.getCategoryCode());
        Set<String> categoryCodes = new HashSet<>(payload.getCategoryCode());
        Map<String, Object> response = new HashMap<>();

        if(categories.size() != categoryCodes.size()){
            Set<String> foundedCategories = categories.stream().map(Category::getCode).collect(Collectors.toSet());
            categoryCodes.removeAll(foundedCategories);

            response.put("success", false);
            response.put("message", "Gagal mengubah buku, kode buku: " + code + ", Beberapa kategori tidak ditemukan. kode kategori: " + collectUnknownCategory(categoryCodes));
            return ResponseEntity.badRequest().body(response);
        }

        book = BookMapper.toBookDao(book, payload);
        book.setCategories(categories);
        Book updatedBook = bookService.updateBook(book);
        response.put("success", true);
        response.put("message", "Berhasil mengubah data buku. kode buku " + code);
        response.put("data", BookMapper.toBookDto(updatedBook));
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity detailBook(@PathVariable("code") String code){
        Book book = bookService.findByCode(code);
        if(book == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(BookMapper.toBookDto(book));
    }

    @GetMapping(path = "/search")
    public ResponseEntity searchBook(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "size", defaultValue = "255") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ){
        Page<Book> results = bookService.searchBook(query, size, page);
        List<BookDto> data = results.getContent().stream().map(BookMapper::toBookDto).collect(Collectors.toList());
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
        List<BookDto> data = results.getContent().stream().map(BookMapper::toBookDto).collect(Collectors.toList());
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
