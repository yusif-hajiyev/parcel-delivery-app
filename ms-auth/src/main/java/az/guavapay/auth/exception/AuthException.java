package az.guavapay.auth.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private String code;
    private String message;

    public AuthException(String message, String code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
