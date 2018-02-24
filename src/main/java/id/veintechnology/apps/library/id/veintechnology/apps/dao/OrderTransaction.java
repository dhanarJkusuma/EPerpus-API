package id.veintechnology.apps.library.id.veintechnology.apps.dao;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_transaction")
public class OrderTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "borrow_date")
    private Date borrowDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "created_on")
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<OrderTransactionItem> items;

    public OrderTransaction() {
        this.createdOn = new Date();
    }

    protected OrderTransaction(Builder builder){
        setPublicId(builder.publicId);
        setBorrowDate(builder.borrowDate);
        setReturnDate(builder.returnDate);
        setCreatedOn(builder.createdOn);
        setMember(builder.member);
        setItems(builder.items);
        this.createdOn = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<OrderTransactionItem> getItems() {
        return items;
    }

    public void setItems(List<OrderTransactionItem> items) {
        this.items = items;
    }

    public static class Builder{
        String publicId;
        Date borrowDate;
        Date returnDate;
        Date createdOn;
        Member member;
        List<OrderTransactionItem> items;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder publicId(String val){
            this.publicId = val;
            return this;
        }

        public Builder borrowDate(Date val){
            this.borrowDate = val;
            return this;
        }

        public Builder returnDate(Date val){
            this.returnDate = val;
            return this;
        }

        public Builder createdOn(Date val){
            this.createdOn = val;
            return this;
        }

        public Builder member(Member val){
            this.member = val;
            return this;
        }

        public Builder items(List<OrderTransactionItem> val){
            this.items = val;
            return this;
        }

        public OrderTransaction build(){
            return new OrderTransaction(this);
        }

    }
}
