package az.guavapay.user.model.view;

import az.guavapay.user.model.dto.AddressDto;
import az.guavapay.user.model.enums.CourierStatus;
import az.guavapay.user.model.enums.CourierTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierView {

    private String fullName;
    private CourierTypes courierType;
    private String courierCompanyName;
    private AddressDto addressDto;
    private CourierStatus courierStatus;
}
