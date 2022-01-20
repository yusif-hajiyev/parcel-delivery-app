package com.guavapay.parcel.mapper;

import com.guavapay.parcel.entity.AddressEntity;
import com.guavapay.parcel.model.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public abstract class AddressMapper {

    public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    public abstract AddressEntity dtoToEntity(AddressDto addressDto);
}
