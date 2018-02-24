package id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BookDto {

    @NotNull
    private String code;

    @NotNull
    private String title;

    private String author;

    private String editor;

    private String publisher;

    private int year;

    private int stock;
    private int totalStock;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdOn;

    private Set<CategoryDto> categories = new HashSet<>();

    public BookDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Set<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDto> categories) {
        this.categories = categories;
    }

    public static class Builder{

        private String code;
        private String title;
        private String author;
        private String editor;
        private String publisher;
        private int year;
        private int stock;
        private int totalStock;
        private Set<CategoryDto> categories = new HashSet<>();
        private Date createdOn;

        public static BookDto.Builder newBuilder() {
            return new BookDto.Builder();
        }

        public BookDto.Builder code(String code){
            this.code = code;
            return this;
        }

        public BookDto.Builder title(String title){
            this.title = title;
            return this;
        }

        public BookDto.Builder author(String author){
            this.author = author;
            return this;
        }

        public BookDto.Builder editor(String editor){
            this.editor = editor;
            return this;
        }

        public BookDto.Builder publisher(String publisher){
            this.publisher = publisher;
            return this;
        }

        public BookDto.Builder year(int year){
            this.year = year;
            return this;
        }

        public BookDto.Builder stock(int stock){
            this.stock = stock;
            return this;
        }

        public BookDto.Builder totalStock(int stock){
            this.totalStock = stock;
            return this;
        }

        public BookDto.Builder category(Set<CategoryDto> categories){
            this.categories = categories;
            return this;
        }

        public BookDto.Builder createdOn(Date createdOn){
            this.createdOn = createdOn;
            return this;
        }

        public BookDto build(){
            BookDto book = new BookDto();
            book.setCode(code);
            book.setTitle(title);
            book.setAuthor(author);
            book.setEditor(editor);
            book.setPublisher(publisher);
            book.setYear(year);
            book.setStock(stock);
            book.setTotalStock(totalStock);
            book.setCategories(categories);
            book.setCreatedOn(createdOn);
            return book;
        }
    }
}
