package com.grirdynamics.yvoronovskyi.carsharing.service.impl;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.repository.ICarRepository;
import com.grirdynamics.yvoronovskyi.carsharing.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService implements ICarService {

    private final ICarRepository carRepository;

    @Autowired
    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Car get(long carId) {
        return carRepository.getReferenceById(carId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public void delete(long carId) {
        carRepository.deleteById(carId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getCarByLocation(Double latitude, Double longitude, Long count) {
        return carRepository.findNearestCarByLocation(latitude, longitude, count);
    }
}
