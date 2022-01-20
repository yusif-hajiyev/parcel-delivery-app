package az.guavapay.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    @NotEmpty(message = "The username can't be null or empty")
    @Size(min = 5, message = "The username must be at least 5 characters long")
    private String username;

    @NotEmpty(message = "The email can't be null or empty")
    @Email
    private String email;

    @NotEmpty(message = "The password can't be null or empty")
    @Size(min = 3, message = "The password must be at least 3 characters long")
    private String password;

    @NotEmpty(message = "The full name can't be null or empty")
    private String fullName;

    @NotNull(message = "The address can't be null")
    private AddressDto address;
}
