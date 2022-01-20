package az.guavapay.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    @NotEmpty(message = "The address can't be null or empty")
    private String address;
    private String region;

    @NotNull(message = "The postal code can't be null")
    private Integer postalCode;

    @NotEmpty(message = "The country can't be null or empty")
    private String country;

    @NotEmpty(message = "The city can't be null or empty")
    private String city;

    @NotEmpty(message = "The phone can't be null or empty")
    private String phone;

    private String longitude;
    private String latitude;
}
