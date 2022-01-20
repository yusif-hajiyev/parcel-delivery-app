package az.guavapay.user.controller;

import az.guavapay.user.model.dto.ChangeCourierStatusDto;
import az.guavapay.user.model.view.CourierView;
import az.guavapay.user.service.CourierService;
import az.guavapay.user.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/courier")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping("/list-all")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN')")
    public ResponseEntity<List<CourierView>> getCourierList(@RequestHeader(Constants.HEADER_API_KEY) @NotBlank String apiKey,
                                                            @RequestHeader(Constants.HEADER_USERNAME) @NotBlank String username) {
        return ResponseEntity.ok().body(courierService.getCourierList());
    }

    @GetMapping("/list-by-status")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN')")
    public ResponseEntity<List<CourierView>> getCourierListByStatus(@RequestParam("status") Integer status,
                                                                    @RequestHeader(Constants.HEADER_API_KEY) @NotBlank String apiKey,
                                                                    @RequestHeader(Constants.HEADER_USERNAME) @NotBlank String username) {
        return ResponseEntity.ok().body(courierService.getCourierListByStatus(status));
    }

    @PutMapping("/change-status")
    public ResponseEntity<Void> changeCourierStatus(@RequestBody ChangeCourierStatusDto statusDto) {
        courierService.changeCourierStatus(statusDto);
        return ResponseEntity.ok().build();
    }
}
