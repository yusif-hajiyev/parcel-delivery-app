package az.guavapay.user.dao.repository;

import az.guavapay.user.dao.entity.CourierEntity;
import az.guavapay.user.model.enums.CourierStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierRepository extends CrudRepository<CourierEntity, Long> {

    List<CourierEntity>findAll();

    List<CourierEntity> findByCourierStatus(CourierStatus courierStatus);
}
