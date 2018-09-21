package id.veintechnology.apps.library.id.veintechnology.apps.error_handler;

import id.veintechnology.apps.library.id.veintechnology.apps.error_handler.response.ErrorResponseRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class BaseErrorHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        // log error
        logger.info(ex.getClass().getName());

        // collecting errors
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, List<String>> errorFields = new HashMap<>();
        for(FieldError fieldError: errors){
            List<String> errMessages = errorFields.get(fieldError.getField());
            if(errMessages == null){
                errMessages = new ArrayList<>();
            }
            errMessages.add(fieldError.getDefaultMessage());
            errorFields.put(fieldError.getField(), errMessages);
        }

        // create error response object
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while sending request");
        response.setErrors(errorFields);
        return handleExceptionInternal(ex, response, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        // log error
        logger.info(ex.getClass().getName());

        // collecting errors
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, List<String>> errorFields = new HashMap<>();
        for(FieldError fieldError: errors){
            List<String> errMessages = errorFields.get(fieldError.getField());
            if(errMessages == null){
                errMessages = new ArrayList<>();
            }
            errMessages.add(fieldError.getDefaultMessage());
            errorFields.put(fieldError.getField(), errMessages);
        }

        // create error response object
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while sending request");
        response.setErrors(errorFields);
        return handleExceptionInternal(ex, response, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        // log error
        logger.info(ex.getClass().getName());

        // collecting error
        Map<String, List<String>> errorFields = new HashMap<>();
        errorFields.put(ex.getPropertyName(), Collections.singletonList("has value " + ex.getValue() + ". Required type " + ex.getRequiredType()));

        // create error response object
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while sending request");
        response.setErrors(errorFields);

        return new ResponseEntity<>(response, new HttpHeaders(), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        // log error
        logger.info(ex.getClass().getName());

        // collecting error
        Map<String, List<String>> errorFields = new HashMap<>();
        errorFields.put(ex.getParameterName(), Collections.singletonList("parameter cannot be null"));

        // create error response object
        ErrorResponseRequest response = new ErrorResponseRequest();
        response.setMessage("Error while sending request");
        response.setErrors(errorFields);

        return new ResponseEntity<>(response, new HttpHeaders(), BAD_REQUEST);
    }
}
