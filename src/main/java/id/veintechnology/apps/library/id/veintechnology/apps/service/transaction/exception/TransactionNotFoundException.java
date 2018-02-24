package id.veintechnology.apps.library.id.veintechnology.apps.service.transaction.exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String code) {
        super("Unknown Transaction Exception Code : " + code + " !!!");
    }
}
