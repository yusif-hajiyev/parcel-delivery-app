package az.guavapay.user.service.impl;

import az.guavapay.user.dao.entity.CourierEntity;
import az.guavapay.user.dao.repository.CourierRepository;
import az.guavapay.user.exception.UserNotFoundException;
import az.guavapay.user.mapper.CourierMapper;
import az.guavapay.user.model.dto.ChangeCourierStatusDto;
import az.guavapay.user.model.view.CourierView;
import az.guavapay.user.service.CourierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.guavapay.user.model.enums.CourierStatus.getEnum;
import static az.guavapay.user.model.enums.ExceptionCode.COURIER_NOT_FOUND;

@Service
@Slf4j
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    public CourierServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public List<CourierView> getCourierList() {
        log.info("*****get courier list start*****");
        List<CourierEntity> courierEntityList = courierRepository.findAll();
        log.info("*****get courier list end*****");
        return CourierMapper.INSTANCE.entityToView(courierEntityList);
    }

    @Override
    public List<CourierView> getCourierListByStatus(Integer status) {
        log.info("*****get courier list by status start*****");
        List<CourierEntity> courierEntityList = courierRepository.findByCourierStatus(getEnum(status));
        log.info("*****get courier list by status end*****");
        return CourierMapper.INSTANCE.entityToView(courierEntityList);
    }

    @Override
    public void changeCourierStatus(ChangeCourierStatusDto statusDto) {
        log.info("*****change courier status start*****");
        CourierEntity courierEntity = courierRepository.findById(statusDto.getId())
                .orElseThrow(() -> new UserNotFoundException(COURIER_NOT_FOUND.getCode(), COURIER_NOT_FOUND.getMessage()));
        courierEntity.setCourierStatus(getEnum(statusDto.getStatus()));
        courierRepository.save(courierEntity);
        log.info("*****change courier status end****");
    }
}
