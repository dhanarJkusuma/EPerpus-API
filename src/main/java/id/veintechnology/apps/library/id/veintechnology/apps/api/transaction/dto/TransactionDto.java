package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class TransactionDto {

    private String publicId;
    private ZonedDateTime borrowDate;
    private ZonedDateTime returnDate;
    private ZonedDateTime createdOn;
    private TransactionMemberDto member;
    private List<TransactionBookDto> books;

    public TransactionDto() {
    }

    protected TransactionDto(Builder builder){
        setPublicId(builder.publicId);
        setBorrowDate(builder.borrowDate);
        setReturnDate(builder.returnDate);
        setCreatedOn(builder.createdOn);
        setMember(builder.member);
        setBooks(builder.books);
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public ZonedDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(ZonedDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public TransactionMemberDto getMember() {
        return member;
    }

    public void setMember(TransactionMemberDto member) {
        this.member = member;
    }

    public List<TransactionBookDto> getBooks() {
        return books;
    }

    public void setBooks(List<TransactionBookDto> books) {
        this.books = books;
    }

    public static class Builder {

        String publicId;
        ZonedDateTime borrowDate;
        ZonedDateTime returnDate;
        ZonedDateTime createdOn;
        TransactionMemberDto member;
        List<TransactionBookDto> books;

        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder publicId(String val){
            this.publicId = val;
            return this;
        }

        public Builder borrowDate(ZonedDateTime val){
            borrowDate = val;
            return this;
        }

        public Builder returnDate(ZonedDateTime val){
            returnDate = val;
            return this;
        }

        public Builder createdOn(ZonedDateTime val){
            createdOn = val;
            return this;
        }

        public Builder member(TransactionMemberDto val){
            member = val;
            return this;
        }

        public Builder books(List<TransactionBookDto> val){
            books = val;
            return this;
        }

        public TransactionDto build(){
            return new TransactionDto(this);
        }
    }
}
