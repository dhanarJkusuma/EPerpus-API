package id.veintechnology.apps.library.id.veintechnology.apps.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_transaction_item")
public class OrderTransactionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_transaction_id", nullable = false)
    @JsonIgnore
    private OrderTransaction transaction;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_code")
    private String bookCode;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_editor")
    private String bookEditor;

    @Column(name = "book_publisher")
    private String bookPublisher;

    @Column(name = "book_year")
    private Integer bookYear;

    @Column(name = "book_categories")
    private String bookCategories;

    @Column(name = "quantity")
    private Integer quantity;

    public OrderTransactionItem() {
    }

    protected OrderTransactionItem(Builder builder){
        setTransaction(builder.transaction);
        setBookId(builder.bookId);
        setBookCode(builder.bookCode);
        setBookTitle(builder.bookTitle);
        setBookAuthor(builder.bookAuthor);
        setBookEditor(builder.bookEditor);
        setBookPublisher(builder.bookPublisher);
        setBookYear(builder.bookYear);
        setBookCategories(builder.bookCategories);
        setQuantity(builder.quantity);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(OrderTransaction transaction) {
        this.transaction = transaction;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookEditor() {
        return bookEditor;
    }

    public void setBookEditor(String bookEditor) {
        this.bookEditor = bookEditor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public Integer getBookYear() {
        return bookYear;
    }

    public void setBookYear(Integer bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(String bookCategories) {
        this.bookCategories = bookCategories;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static class Builder{

        OrderTransaction transaction;
        Long bookId;
        String bookCode;
        String bookTitle;
        String bookAuthor;
        String bookEditor;
        String bookPublisher;
        Integer bookYear;
        String bookCategories;
        Integer quantity;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder transaction(OrderTransaction val){
            this.transaction = val;
            return this;
        }

        public Builder bookId(long val){
            this.bookId = val;
            return this;
        }

        public Builder bookCode(String val){
            this.bookCode = val;
            return this;
        }

        public Builder bookTitle(String val){
            this.bookTitle = val;
            return this;
        }

        public Builder bookAuthor(String val){
            this.bookAuthor = val;
            return this;
        }

        public Builder bookEditor(String val){
            this.bookEditor = val;
            return this;
        }

        public Builder bookPublisher(String val){
            this.bookPublisher = val;
            return this;
        }

        public Builder bookYear(Integer val){
            this.bookYear = val;
            return this;
        }

        public Builder bookCategories(String val){
            this.bookCategories = val;
            return this;
        }

        public Builder quantity(Integer val){
            quantity = val;
            return this;
        }

        public OrderTransactionItem build(){
            return new OrderTransactionItem(this);
        }
    }
}
