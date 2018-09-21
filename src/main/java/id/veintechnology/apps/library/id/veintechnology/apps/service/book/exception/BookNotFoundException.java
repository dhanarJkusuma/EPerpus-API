package id.veintechnology.apps.library.id.veintechnology.apps.service.book.exception;

import id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response.ExceptionResponse;

public class BookNotFoundException extends RuntimeException implements ExceptionResponse {
    private String code;

    public BookNotFoundException(String code) {
        super("[Exception] Book with code : " + code + " doesn't exist. ");
        this.code = code;
    }

    @Override
    public String getMessage(){
        return "Book with code : " + code + " doesn't exist. ";
    }
}
