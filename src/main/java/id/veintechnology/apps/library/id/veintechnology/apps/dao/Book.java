package id.veintechnology.apps.library.id.veintechnology.apps.dao;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "editor")
    private String editor;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private int year;

    @Column(name = "stock")
    private int stock;

    @Column(name = "total_stock")
    private int totalStock;

    @Column(name = "created_on")
    private Date createdOn;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "category_book",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories = new HashSet<>();


    public Book() {
        this.totalStock = 1;
        this.stock = 1;
        this.createdOn = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
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
        private Set<Category> categories = new HashSet<>();

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder code(String code){
            this.code = code;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder author(String author){
            this.author = author;
            return this;
        }

        public Builder editor(String editor){
            this.editor = editor;
            return this;
        }

        public Builder publisher(String publisher){
            this.publisher = publisher;
            return this;
        }

        public Builder year(int year){
            this.year = year;
            return this;
        }

        public Builder stock(int stock){
            this.stock = stock;
            return this;
        }

        public Builder totalStock(int totalStock){
            this.totalStock = totalStock;
            return this;
        }

        public Builder category(Set<Category> categories){
            this.categories = categories;
            return this;
        }

        public Book build(){
            Book book = new Book();
            book.setCode(code);
            book.setTitle(title);
            book.setAuthor(author);
            book.setEditor(editor);
            book.setPublisher(publisher);
            book.setYear(year);
            book.setStock(stock);
            book.setTotalStock(totalStock);
            book.setCategories(categories);
            return book;
        }
    }
}
