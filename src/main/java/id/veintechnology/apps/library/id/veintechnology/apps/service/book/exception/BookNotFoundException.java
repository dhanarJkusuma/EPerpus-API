package id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception;

public class BookNotFoundException extends RuntimeException {
    private String codes;

    public BookNotFoundException(String codes) {
        super("Unknown Book Exception, codes : " + codes + " !!!");
        this.codes = codes;
    }

    public String getCodes() {
        return codes;
    }
}
