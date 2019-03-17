package com.jovan.logistics.iFoodVRP.web.controller;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.jovan.logistics.iFoodVRP.exception.EntityAlreadyExistsException;
import com.jovan.logistics.iFoodVRP.exception.EntityNotFoundException;
import com.jovan.logistics.iFoodVRP.exception.InvalidDateFormatException;
import com.jovan.logistics.iFoodVRP.web.response.ErrorResponse;
import com.jovan.logistics.iFoodVRP.web.response.ErrorResponseCodes;
import com.jovan.logistics.iFoodVRP.web.response.ErrorResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

/**
 * @author Jovan Fernandes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 2019-03-17 17:39
 */
@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {


    private final static String MESSAGE_PATTERN = "message=%s, appName=%s %s";
    private final static String APP_NAME = "iFoodVRP";

    @ExceptionHandler({InvalidDateFormatException.class, EntityAlreadyExistsException.class, Exception.class})
    public ResponseEntity<Object> badRequestExceptionHandler(final Exception ex) {
        logError(ex.getMessage(), ex);
        return createErrorResponseWrapper(HttpStatus.BAD_REQUEST, ex);
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> notFoundExceptionHandler(final Exception ex) {
        logError(ex.getMessage(), ex);

        //Request foi processado mas algo não foi encontrado.
        // Como o request foi processado, é retornado 204 ao invés de 404
        return createErrorResponseWrapper(HttpStatus.NO_CONTENT, ex);
    }


    // General ResponseEntityExceptionHandler

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        final List<ErrorResponse> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    return ErrorResponse.builder()
                            .code(ErrorResponseCodes.INVALID_FIELD_VALUE)
                            .message(violation.getPropertyPath().toString())
                            .build();
                }).collect(toList());
        return createErrorResponseWrapper(BAD_REQUEST, errors);
    }


    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);
        final List<ErrorResponse> errors = ex.getFieldErrors()
                .stream()
                .map(fieldError ->
                        ErrorResponse.builder()
                                .code(ErrorResponseCodes.GENERIC_ERROR)
                                .message(fieldError.getField())
                                .build()).collect(toList());
        return createErrorResponseWrapper(status, errors);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body,
                                                             final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);
        return createErrorResponseWrapper(status, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);
        final List<ErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    final String complement = ofNullable(fieldError.getDefaultMessage())
                            .map(message -> " (".concat(StringUtils.trimToNull(message)).concat(")"))
                            .orElse("");
                    return ErrorResponse.builder()
                            .code(ErrorResponseCodes.INVALID_FIELD_VALUE)
                            .message(fieldError.getField().concat(complement))
                            .build();
                }).collect(toList());
        return createErrorResponseWrapper(status, errors);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex.getMessage(), ex);

        return createErrorResponseWrapper(status, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .code(ErrorResponseCodes.GENERIC_ERROR)
                .message(ex.getParameterName())
                .build();
        return createErrorResponseWrapper(status, singletonList(error));
    }

    //Utility Methods for handle exceptions

    private ResponseEntity<Object> createErrorResponseWrapper(final HttpStatus statusCode, final Exception ex) {

        ErrorResponse error = ErrorResponse.builder()
                .code(ErrorResponseCodes.GENERIC_ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(statusCode).body(new ErrorResponseWrapper(Collections.singletonList(error)));
    }

    private ResponseEntity<Object> createErrorResponseWrapper(final HttpStatus statusCode,
                                                              final List<ErrorResponse> errors) {
        return ResponseEntity.status(statusCode).body(new ErrorResponseWrapper(errors));
    }


    private static void logWarn(final String message, final Exception ex) {
        final String msg = String
                .format(MESSAGE_PATTERN, message, APP_NAME, StringUtils.EMPTY);
        log.warn(msg, ex);
    }


    private static void logError(final String message, final Exception ex) {
        final String msg = String
                .format(MESSAGE_PATTERN, message, APP_NAME, StringUtils.EMPTY);
        log.error(msg, ex);
    }
}
