package com.guavapay.parcel.service.impl;

import com.guavapay.parcel.client.CourierClient;
import com.guavapay.parcel.client.UserClient;
import com.guavapay.parcel.entity.ParcelEntity;
import com.guavapay.parcel.exception.ParcelNotFoundException;
import com.guavapay.parcel.exception.UserNotFoundException;
import com.guavapay.parcel.mapper.AddressMapper;
import com.guavapay.parcel.mapper.ParcelMapper;
import com.guavapay.parcel.model.dto.AssignParcelDto;
import com.guavapay.parcel.model.dto.ChangeParcelDestDto;
import com.guavapay.parcel.model.dto.ChangeParcelStatusDto;
import com.guavapay.parcel.model.dto.ParcelDto;
import com.guavapay.parcel.model.enums.CourierStatus;
import com.guavapay.parcel.model.request.ChangeCourierStatusRequest;
import com.guavapay.parcel.model.response.UserResponse;
import com.guavapay.parcel.model.view.ParcelView;
import com.guavapay.parcel.repository.ParcelRepository;
import com.guavapay.parcel.service.ParcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.guavapay.parcel.model.enums.ExceptionCode.*;
import static com.guavapay.parcel.model.enums.ParcelStatus.ASSIGNED;
import static com.guavapay.parcel.model.enums.ParcelStatus.CANCELED;
import static com.guavapay.parcel.model.enums.Roles.CUSTOMER;

@Service
@Slf4j
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final UserClient userClient;
    private final CourierClient courierClient;

    public ParcelServiceImpl(ParcelRepository parcelRepository, UserClient userClient, CourierClient courierClient) {
        this.parcelRepository = parcelRepository;
        this.userClient = userClient;
        this.courierClient = courierClient;
    }

    @Override
    public void createParcel(ParcelDto parcelDto) {
        log.info("*****create parcel start*****");
        UserResponse userResponse = userClient.getUserById(parcelDto.getUserId());
        Optional.ofNullable(userResponse)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        ParcelEntity parcelEntity = ParcelMapper.INSTANCE.dtoToEntity(parcelDto);
        parcelEntity.setCustomerId(userResponse.getCustomerId());
        parcelRepository.save(parcelEntity);
        log.info("*****create parcel end*****");
    }

    @Override
    public void changeParcelDestination(ChangeParcelDestDto changeParcelDestDto) {
        log.info("*****change parcel destination start*****");
        ParcelEntity parcelEntity = parcelRepository.findById(changeParcelDestDto.getParcelId())
                .orElseThrow(() -> new ParcelNotFoundException(PARCEL_NOT_FOUND.getCode(), PARCEL_NOT_FOUND.getMessage()));
        parcelEntity.setAddressFrom(AddressMapper.INSTANCE.dtoToEntity(changeParcelDestDto.getAddressDto()));
        parcelEntity.setUpdateDate(LocalDateTime.now());
        parcelRepository.save(parcelEntity);
        log.info("*****change parcel destination end*****");
    }

    @Override
    public void cancelParcel(Long parcelId) {
        log.info("*****cancel parcel start*****");
        ParcelEntity parcelEntity = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(PARCEL_NOT_FOUND.getCode(), PARCEL_NOT_FOUND.getMessage()));
        parcelEntity.setParcelStatus(CANCELED);
        parcelEntity.setCancelDate(LocalDateTime.now());
        parcelRepository.save(parcelEntity);
        log.info("*****cancel parcel end*****");
    }

    @Override
    public ParcelView getParcelDetails(Long parcelId) {
        log.info("*****get parcel details start*****");
        ParcelEntity parcelEntity = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ParcelNotFoundException(PARCEL_NOT_FOUND.getCode(), PARCEL_NOT_FOUND.getMessage()));
        log.info("*****get parcel details end*****");
        return ParcelMapper.INSTANCE.entityToView(parcelEntity);
    }

    @Override
    public List<ParcelView> getAllParcelsForAdmin() {
        log.info("*****get all parcels for admin start*****");
        List<ParcelEntity> parcelEntityList = parcelRepository.findAll();
        log.info("*****get all parcels for admin end*****");
        return ParcelMapper.INSTANCE.entityToView(parcelEntityList);
    }

    @Override
    public List<ParcelView> getAllParcelsByUser(Long userId) {
        log.info("*****get all parcels by user start*****");
        UserResponse userResponse = userClient.getUserById(userId);
        Optional.ofNullable(userResponse)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage()));
        List<ParcelEntity> parcelEntityList = userResponse.getRoleId().equals((long) CUSTOMER.ordinal()) ?
                parcelRepository.findByCustomerId(userResponse.getCustomerId()) :
                parcelRepository.findByCourierId(userResponse.getCourierId());
        log.info("*****get all parcels by user end*****");
        return ParcelMapper.INSTANCE.entityToView(parcelEntityList);
    }

    @Override
    public void changeParcelStatus(ChangeParcelStatusDto changeParcelStatusDto) {
        log.info("*****change parcel status start*****");
        ParcelEntity parcelEntity = parcelRepository.findById(changeParcelStatusDto.getParcelId())
                .orElseThrow(() -> new ParcelNotFoundException(PARCEL_NOT_FOUND.getCode(), PARCEL_NOT_FOUND.getMessage()));
        parcelEntity.setParcelStatus(changeParcelStatusDto.getParcelStatus());
        parcelEntity.setUpdateDate(LocalDateTime.now());
        log.info("*****change parcel status end*****");
    }

    @Override
    public void assignParcelToCourier(AssignParcelDto assignParcelDto) {
        log.info("*****assign parcel start*****");
        ParcelEntity parcelEntity = parcelRepository.findById(assignParcelDto.getParcelId())
                .orElseThrow(() -> new ParcelNotFoundException(PARCEL_NOT_FOUND.getCode(), PARCEL_NOT_FOUND.getMessage()));
        parcelEntity.setCourierId(assignParcelDto.getCourierId());
        parcelEntity.setParcelStatus(ASSIGNED);
        parcelEntity.setUpdateDate(LocalDateTime.now());
        parcelRepository.save(parcelEntity);
        courierClient.changeCourierStatus(ChangeCourierStatusRequest.builder()
                .id(assignParcelDto.getCourierId())
                .status(CourierStatus.IN_EXECUTION.ordinal())
                .build());
        log.info("*****assign parcel end*****");
    }
}
