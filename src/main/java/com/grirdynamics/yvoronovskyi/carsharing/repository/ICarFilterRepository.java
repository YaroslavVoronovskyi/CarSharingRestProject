package com.grirdynamics.yvoronovskyi.carsharing.repository;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarClass;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarStatus;
import com.grirdynamics.yvoronovskyi.carsharing.model.EngineType;
import com.grirdynamics.yvoronovskyi.carsharing.model.Transmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "filters", path = "filters")
public interface ICarFilterRepository extends JpaRepository<Car, Long> {

    @RestResource(path = "by-car-status", rel = "by-car-status")
    Page<Car> findByCarStatus(@Param("status") CarStatus status, Pageable pageable);

    @RestResource(path = "by-transmission", rel = "by-transmission")
    Page<Car> findByTransmission(@Param("transmission") Transmission transmission, Pageable pageable);

    @RestResource(path = "by-car-class", rel = "by-car-class")
    Page<Car> findByCarClass(@Param("class") CarClass carclass, Pageable pageable);

    @RestResource(path = "by-model", rel = "by-model")
    Page<Car> findByModel(@Param("model") String model, Pageable pageable);

    @RestResource(path = "by-brand", rel = "by-brand")
    Page<Car> findByBrand(@Param("brand") String brand, Pageable pageable);

    @RestResource(path = "by-engine-type", rel = "by-engine-type")
    Page<Car> findByEngineType(@Param("type") EngineType engineType, Pageable pageable);
}
