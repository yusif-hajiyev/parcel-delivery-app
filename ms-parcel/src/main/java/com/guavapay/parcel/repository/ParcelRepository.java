package com.guavapay.parcel.repository;

import com.guavapay.parcel.entity.ParcelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends CrudRepository<ParcelEntity, Long> {

    List<ParcelEntity> findAll();

    List<ParcelEntity> findByCustomerId(Long id);

    List<ParcelEntity> findByCourierId(Long id);
}
