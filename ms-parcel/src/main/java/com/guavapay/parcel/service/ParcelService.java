package com.guavapay.parcel.service;

import com.guavapay.parcel.model.dto.AssignParcelDto;
import com.guavapay.parcel.model.dto.ChangeParcelDestDto;
import com.guavapay.parcel.model.dto.ChangeParcelStatusDto;
import com.guavapay.parcel.model.dto.ParcelDto;
import com.guavapay.parcel.model.view.ParcelView;

import java.util.List;

public interface ParcelService {

    void createParcel(ParcelDto parcelDto);

    void changeParcelDestination(ChangeParcelDestDto changeParcelDestDto);

    void cancelParcel(Long parcelId);

    ParcelView getParcelDetails(Long parcelId);

    List<ParcelView> getAllParcelsForAdmin();

    List<ParcelView> getAllParcelsByUser(Long userId);

    void changeParcelStatus(ChangeParcelStatusDto changeParcelStatusDto);

    void assignParcelToCourier(AssignParcelDto assignParcelDto);
}
