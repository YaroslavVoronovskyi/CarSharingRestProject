package com.grirdynamics.yvoronovskyi.carsharing.service.impl;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.repository.ICarRepository;
import com.grirdynamics.yvoronovskyi.carsharing.service.ICarService;
import com.grirdynamics.yvoronovskyi.carsharing.service.exception.DublicateLicensePlateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CarService implements ICarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final ICarRepository carRepository;

    @Autowired
    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Car getCarBuId(long carId) {
        LOGGER.debug("Try get car wih id {} from DB", carId);
        return carRepository.getReferenceById(carId);
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        LOGGER.debug("Try get all cars from DB");
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public Car createNewCar(Car car) {
        LOGGER.debug("Try create new car and save in DB");
        try {
            return carRepository.save(car);
        } catch (DataIntegrityViolationException exception) {
            throw new DublicateLicensePlateException("A car with such a license plate already exists");
        }
    }

    @Override
    @Transactional
    public Car updateCar(Car car) {
        LOGGER.debug("Try update car wih id {} from DB", car.getId());
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteCar(long carId) {
        LOGGER.debug("Try delete car wih id {} from DB", carId);
        try {
            carRepository.deleteById(carId);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Car with id " + carId + " not found!");
        }
        LOGGER.debug("Car wih id {} was successfully deleted from DB", carId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getCarByLocation(Double latitude, Double longitude, Long count) {
        LOGGER.debug("Try get cars wih latitude {} longitude {} and count {} from DB", latitude, longitude, count);
        return carRepository.findNearestCarByLocation(latitude, longitude, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Car> getCarByParameters(Specification<Car> carSpecification, Pageable pageable) {
        LOGGER.debug("Try get cars by parameters from DB");
        return carRepository.findAll(carSpecification, pageable);
    }
}
