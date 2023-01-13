package com.grirdynamics.yvoronovskyi.carsharing.service;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.Coordinates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICarService {
    
    Car getCarBuId(long carId);
    List<Car> getAllCars();
    Car createNewCar(Car car);
    Car updateCar(Car car);
    void deleteCar(long carId);
    List<Car> getCarByLocation(Coordinates coordinates, Long count);
    Page<Car> getCarByParameters(Specification<Car> carSpecification, Pageable pageable);
}
