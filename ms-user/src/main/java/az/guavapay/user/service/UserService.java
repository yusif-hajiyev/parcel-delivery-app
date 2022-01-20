package az.guavapay.user.service;

import az.guavapay.user.model.dto.AuthRequestDto;
import az.guavapay.user.model.dto.CourierDto;
import az.guavapay.user.model.dto.CustomerDto;
import az.guavapay.user.model.view.UserView;
import az.guavapay.user.model.response.UserResponse;

public interface UserService {

    void addCustomer(CustomerDto customerDto);

    void addCourier(CourierDto courierDto);

    UserView getUser(AuthRequestDto authRequestDto);

    UserResponse getUserById(Long id);
}
