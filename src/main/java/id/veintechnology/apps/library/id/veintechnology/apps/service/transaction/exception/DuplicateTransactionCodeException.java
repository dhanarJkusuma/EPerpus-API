package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception;

public class DuplicateTransactionCodeException extends RuntimeException {

    private String invalidCode;

    public DuplicateTransactionCodeException(String code) {
        super("Duplicate Transaction Code : " + code + " !!!");
    }

    public String getInvalidCode() {
        return invalidCode;
    }
}
