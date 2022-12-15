package com.grirdynamics.yvoronovskyi.carsharing.service;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;

import java.util.List;

public interface ICarService {
    
    Car get(long carId);
    List<Car> getAll();
    Car create(Car car);
    Car update(Car car);
    void delete(long carId);
    List<Car> getCarByLocation(Double latitude, Double longitude, Long count);
}
