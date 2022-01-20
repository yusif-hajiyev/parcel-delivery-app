package az.guavapay.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    USER_NOT_FOUND("exception.user-not-found", "user not found"),
    ROLE_NOT_FOUND("exception.role-not-found", "role not found"),
    USER_ALREADY_REGISTERED("exception.user-already-registered", "user already registered"),
    USER_IS_EXIST("exception.user-is-exist", "user is exist"),
    COURIER_NOT_FOUND("exception.courier-not-found", "courier not found"),
    CLIENT_ERROR("exception.client-service-failed", "client service failed");

    private final String code;
    private final String message;
}
