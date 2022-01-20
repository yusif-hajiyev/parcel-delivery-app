package az.guavapay.auth.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AuthRequest {

    @NotEmpty(message = "The username can't be null or empty")
    private String username;

    @NotEmpty(message = "The password can't be null or empty")
    private String password;
}
