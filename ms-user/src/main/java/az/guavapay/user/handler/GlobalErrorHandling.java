package az.guavapay.user.handler;

import az.guavapay.user.exception.RegistrationException;
import az.guavapay.user.exception.RoleNotFoundException;
import az.guavapay.user.exception.UserExistException;
import az.guavapay.user.exception.UserNotFoundException;
import az.guavapay.user.model.response.ErrorLevel;
import az.guavapay.user.model.response.ErrorResponse;
import az.guavapay.user.model.response.ValidationError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static az.guavapay.user.model.enums.ExceptionCode.CLIENT_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "UNEXPECTED_EXCEPTION", null);
    }


    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(RegistrationException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(ex.getMessage(),
                ex.getCode(), null);
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(UserExistException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(ex.getMessage(),
                ex.getCode(), null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(UserNotFoundException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(ex.getMessage(),
                ex.getCode(), null);
    }


    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(RoleNotFoundException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(ex.getMessage(),
                ex.getCode(), null);
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(FeignException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(CLIENT_ERROR.getMessage(),
                CLIENT_ERROR.getCode(), null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error("Validated exception: {}", ex);

        List<ValidationError> checks = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        checks.addAll(
                bindingResult
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> new ValidationError(
                                ErrorLevel.ERROR,
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        ))
                        .collect(Collectors.toList())
        );
        checks.addAll(
                bindingResult
                        .getGlobalErrors()
                        .stream()
                        .map(globalError -> new ValidationError(
                                ErrorLevel.ERROR,
                                globalError.getObjectName(),
                                globalError.getDefaultMessage()
                        ))
                        .collect(Collectors.toList())
        );

        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        checks),
                headers,
                HttpStatus.BAD_REQUEST
        );
    }

}
