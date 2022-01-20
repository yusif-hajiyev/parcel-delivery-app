package az.guavapay.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserExistException extends RuntimeException {

    private final String code;
    private final String message;
}
