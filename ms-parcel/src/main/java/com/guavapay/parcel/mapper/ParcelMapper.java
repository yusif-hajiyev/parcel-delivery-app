package com.guavapay.parcel.mapper;

import com.guavapay.parcel.entity.ParcelEntity;
import com.guavapay.parcel.model.dto.ParcelDto;

import com.guavapay.parcel.model.enums.ParcelStatus;
import com.guavapay.parcel.model.view.ParcelView;
import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.guavapay.parcel.model.enums.ParcelStatus.NEW;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public abstract class ParcelMapper {

    public static final ParcelMapper INSTANCE = Mappers.getMapper(ParcelMapper.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Mappings({
            @Mapping(target = "parcelCode", expression = "java(generateParcelCode())"),
            @Mapping(target = "parcelStatus", expression = "java(parcelStatus())"),
    })
    public abstract ParcelEntity dtoToEntity(ParcelDto parcelDto);

    public String generateParcelCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    public ParcelStatus parcelStatus() {
        return NEW;
    }

    @Mappings({
            @Mapping(target = "addressFrom", source = "addressFrom.address"),
            @Mapping(target = "addressTo", source = "addressTo.address"),
            @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "createdDate"),
            @Mapping(target = "cancelDate", source = "cancelDate", qualifiedByName = "cancelDate"),
    })
    public abstract ParcelView entityToView(ParcelEntity parcelEntity);

    public abstract List<ParcelView> entityToView(List<ParcelEntity> parcelEntityList);

    @Named("createdDate")
    public String createdDate(LocalDateTime createdDate) {
        return formatter.format(createdDate);
    }

    @Named("cancelDate")
    public String cancelDate(LocalDateTime cancelDate) {
        return formatter.format(cancelDate);
    }
}
