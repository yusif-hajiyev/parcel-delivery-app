package az.guavapay.auth.handler;

import az.guavapay.auth.exception.AuthException;
import az.guavapay.auth.model.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.guavapay.auth.model.ExceptionCode.CLIENT_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandling {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "UNEXPECTED_EXCEPTION");
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handle(AuthException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(ex.getMessage(),
                ex.getCode());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(FeignException ex) {
        log.error("Exception ", ex);
        return new ErrorResponse(CLIENT_ERROR.getMessage(),
                CLIENT_ERROR.getCode());
    }
}