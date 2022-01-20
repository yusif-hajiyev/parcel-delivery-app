package az.guavapay.user.service;

import az.guavapay.user.model.dto.ChangeCourierStatusDto;
import az.guavapay.user.model.view.CourierView;

import java.util.List;

public interface CourierService {

    List<CourierView> getCourierList();

    List<CourierView> getCourierListByStatus(Integer status);

    void changeCourierStatus(ChangeCourierStatusDto statusDto);
}
