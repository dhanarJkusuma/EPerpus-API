package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;

public class TransactionPayloadBook {
    private String bookCode;
    private int quantity;

    public TransactionPayloadBook(String bookCode, int quantity) {
        this.bookCode = bookCode;
        this.quantity = quantity;
    }

    public TransactionPayloadBook() {
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
