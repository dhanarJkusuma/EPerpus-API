package id.veintechnology.apps.library.id.veintechnology.apps.service.category.exception;

import id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response.ExceptionResponse;

public class CategoryNotFoundException extends RuntimeException implements ExceptionResponse {
    private String code;

    public CategoryNotFoundException(String code) {
        super("[Exception] Category with code : " + code + " doesn't exist. ");
        this.code = code;
    }

    @Override
    public String getMessage(){
        return "Category with code : " + code + " doesn't exist. ";
    }
}
