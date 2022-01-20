package az.guavapay.user.controller;

import az.guavapay.user.model.dto.AuthRequestDto;
import az.guavapay.user.model.dto.CourierDto;
import az.guavapay.user.model.dto.CustomerDto;
import az.guavapay.user.model.view.UserView;
import az.guavapay.user.model.response.UserResponse;
import az.guavapay.user.service.UserService;
import az.guavapay.user.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-customer")
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        userService.addCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-customer")
    public UserView getCustomer(@RequestBody AuthRequestDto authRequestDto) {
        return userService.getUser(authRequestDto);
    }

    @PostMapping("/create-courier")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN')")
    public ResponseEntity<Void> createCourier(@RequestBody CourierDto courierDto,
                                              @RequestHeader(Constants.HEADER_API_KEY) @NotBlank String apiKey,
                                              @RequestHeader(Constants.HEADER_USERNAME) @NotBlank String username) {
        userService.addCourier(courierDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-user")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
}
