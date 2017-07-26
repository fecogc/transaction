package com.test.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler 
{
	
    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        
        logger.warn(errors);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        
        logger.warn(errors);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = ex.getRequestPartName() + " part is missing";
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = ex.getParameterName() + " parameter is missing";
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    //

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        logger.warn(errors);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    @ExceptionHandler({ DuplicateKeyException.class })
    public ResponseEntity<Object> handleDuplicateKey(final DuplicateKeyException ex, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = "Record already exists, " + ex.getMessage();
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccess(final EmptyResultDataAccessException ex, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final String error = "No record found, " + ex.getMessage();
        logger.warn(error);
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, errMsg, error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        logger.warn(builder.toString());
        final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, errMsg, builder.toString());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    	logger.warn(ex.getClass().getName());
        logger.warn(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        logger.warn(builder.toString());
        final ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, errMsg, builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 500

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
    	logger.error(ex.getClass().getName());
        logger.error(ex.getLocalizedMessage());
        
        String errMsg = ex.getLocalizedMessage();
        
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errMsg, "error occurred");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
