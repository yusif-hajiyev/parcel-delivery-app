package az.guavapay.user.mapper;

import az.guavapay.user.dao.entity.CustomerEntity;
import az.guavapay.user.model.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = AddressMapper.class
)
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mappings({
            @Mapping(target = "user.username", source = "customerDto.username"),
            @Mapping(target = "user.password", source = "customerDto.password"),
    })
    public abstract CustomerEntity mapEntity(CustomerDto customerDto);
}
