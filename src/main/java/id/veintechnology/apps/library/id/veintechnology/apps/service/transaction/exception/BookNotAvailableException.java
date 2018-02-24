package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception;

public class BookNotAvailableException extends RuntimeException{

    private String bookCode;
    public BookNotAvailableException(String bookCode) {
        super("Book Not Available, bookCode:" + bookCode);
        this.bookCode = bookCode;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }
}
