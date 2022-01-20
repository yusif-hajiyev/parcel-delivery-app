package az.guavapay.auth.model;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    FORBIDDEN("exception.auth-forbidden","username or password is incorrect"),
    CLIENT_ERROR("exception.client-service-failed", "client service failed");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
