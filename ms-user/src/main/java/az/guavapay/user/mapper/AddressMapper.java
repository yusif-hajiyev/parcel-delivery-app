package az.guavapay.user.mapper;

import az.guavapay.user.dao.entity.AddressEntity;
import az.guavapay.user.model.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class AddressMapper {

    public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    public abstract AddressEntity mapEntity(AddressDto addressDto);

}
