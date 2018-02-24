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

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "quantity")
    private int quantity;

    public OrderTransactionItem() {
    }

    protected OrderTransactionItem(Builder builder){
        setTransaction(builder.transaction);
        setBook(builder.book);
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class Builder{

        OrderTransaction transaction;
        Book book;
        int quantity;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder transaction(OrderTransaction val){
            this.transaction = val;
            return this;
        }


        public Builder book(Book val){
            this.book = val;
            return this;
        }

        public Builder quantity(int val){
            quantity = val;
            return this;
        }

        public OrderTransactionItem build(){
            return new OrderTransactionItem(this);
        }
    }
}
