package com.guavapay.parcel.controller;

import com.guavapay.parcel.model.dto.AssignParcelDto;
import com.guavapay.parcel.model.dto.ChangeParcelDestDto;
import com.guavapay.parcel.model.dto.ChangeParcelStatusDto;
import com.guavapay.parcel.model.dto.ParcelDto;
import com.guavapay.parcel.model.view.ParcelView;
import com.guavapay.parcel.service.ParcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.guavapay.parcel.util.Constants.*;

@RestController
@RequestMapping("/parcel")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping("/create")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'CUSTOMER')")
    public ResponseEntity<Void> createParcel(@Valid @RequestBody ParcelDto parcelDto,
                                             @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                             @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        parcelService.createParcel(parcelDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-destination")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'CUSTOMER')")
    public ResponseEntity<Void> changeParcelDestination(@Valid @RequestBody ChangeParcelDestDto changeParcelDestDto,
                                                        @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                        @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        parcelService.changeParcelDestination(changeParcelDestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'CUSTOMER')")
    public ResponseEntity<Void> cancelParcel(@NotNull @RequestParam("parcelId") Long parcelId,
                                             @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                             @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        parcelService.cancelParcel(parcelId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/details")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'CUSTOMER') or " +
            "@jwtService.hasPermission(#apiKey, #username, 'COURIER')")
    public ResponseEntity<ParcelView> getParcelDetailsCustomer(@NotNull @RequestParam("parcelId") Long parcelId,
                                                               @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                               @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        return ResponseEntity.ok().body(parcelService.getParcelDetails(parcelId));
    }

    @GetMapping("/all-for-admin")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN')")
    public ResponseEntity<List<ParcelView>> getAllParcelsForAdmin(@RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                                  @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        return ResponseEntity.ok().body(parcelService.getAllParcelsForAdmin());
    }

    @GetMapping("/all-by-user")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'CUSTOMER') or " +
            "@jwtService.hasPermission(#apiKey, #username, 'COURIER')")
    public ResponseEntity<List<ParcelView>> getAllParcelsByUser(@NotNull @RequestParam("userId") Long userId,
                                                                @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                                @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        return ResponseEntity.ok().body(parcelService.getAllParcelsByUser(userId));
    }

    @PutMapping("/change-status")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN') or " +
            "@jwtService.hasPermission(#apiKey, #username, 'COURIER')")
    public ResponseEntity<Void> changeParcelStatus(@Valid @RequestBody ChangeParcelStatusDto changeParcelStatusDto,
                                                   @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                   @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        parcelService.changeParcelStatus(changeParcelStatusDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/assign")
    @PreAuthorize("@jwtService.hasPermission(#apiKey, #username, 'ADMIN')")
    public ResponseEntity<Void> assignParcelToCourier(@Valid @RequestBody AssignParcelDto assignParcelDto,
                                                      @RequestHeader(HEADER_API_KEY) @NotBlank String apiKey,
                                                      @RequestHeader(HEADER_USERNAME) @NotBlank String username) {
        parcelService.assignParcelToCourier(assignParcelDto);
        return ResponseEntity.ok().build();
    }
}
