package id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception;

public class DuplicateBookCodeException extends RuntimeException {

    public DuplicateBookCodeException(String code) {
        super("Duplicate Book code : " + code + " !!!");
    }

}
