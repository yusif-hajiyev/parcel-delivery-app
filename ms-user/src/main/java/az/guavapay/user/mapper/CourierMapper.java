package az.guavapay.user.mapper;

import az.guavapay.user.dao.entity.CourierEntity;
import az.guavapay.user.model.dto.CourierDto;
import az.guavapay.user.model.view.CourierView;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = AddressMapper.class
)
public abstract class CourierMapper {

    public static final CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    @Mappings({
            @Mapping(target = "user.username", source = "courierDto.username"),
            @Mapping(target = "user.password", source = "courierDto.password"),
    })
    public abstract CourierEntity dtoToEntity(CourierDto courierDto);

    public abstract CourierView entityToView(CourierEntity courierEntity);

    public abstract List<CourierView> entityToView(List<CourierEntity> courierEntityList);
}
