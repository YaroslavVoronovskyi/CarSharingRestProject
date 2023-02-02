package com.grirdynamics.yvoronovskyi.carsharing.service.impl;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.Coordinates;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService implements ICarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private static final Double MAX_DISTANCE = 10.000000;
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
        List<Car> carsList = carRepository.findAll();
        if (carsList.isEmpty()) {
            throw new EntityNotFoundException("Cars not fount!");
        }
        LOGGER.debug("All cars was successfully got from DB");
        return carsList;
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
            throw new EntityNotFoundException("Car with id " + carId + " does not exist or has been deleted");
        }
        LOGGER.debug("Car wih id {} was successfully deleted from DB", carId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getCarByLocation(Double latitude, Double longitude, Long count) {
        LOGGER.debug("Try get cars wih latitude {} longitude {} and count {} from DB", latitude, longitude, count);
//        List<Car> carsList = carRepository.findAll().stream()
//                .filter(car -> ((
//                        (car.getCoordinates().getLatitude() == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() + MAX_DISTANCE == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() + MAX_DISTANCE &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() + MAX_DISTANCE == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude() + MAX_DISTANCE)
//                                | (car.getCoordinates().getLatitude() - MAX_DISTANCE == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() - MAX_DISTANCE &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() - MAX_DISTANCE == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude() - MAX_DISTANCE)
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() - MAX_DISTANCE &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude() - MAX_DISTANCE)
//                                | (car.getCoordinates().getLatitude() == coordinates.getLatitude() + MAX_DISTANCE &&
//                                car.getCoordinates().getLongitude() == coordinates.getLongitude() + MAX_DISTANCE)
//                                | (car.getCoordinates().getLatitude() - MAX_DISTANCE == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() - MAX_DISTANCE == coordinates.getLongitude())
//                                | (car.getCoordinates().getLatitude() + MAX_DISTANCE == coordinates.getLatitude() &&
//                                car.getCoordinates().getLongitude() + MAX_DISTANCE == coordinates.getLongitude())
//                )))
//                .limit(count)
//                .sorted(Comparator.comparing(car -> car.getCoordinates().getLatitude()))
//                .collect(Collectors.toList());
        List<Car> carsList = carRepository.findNearestCarByLocation(latitude, longitude, count);
        if (carsList.isEmpty()) {
            throw new EntityNotFoundException("Cars by coordinates not fount, try another coordinates");
        }
        LOGGER.debug("Cars with latitude {} longitude {} and count {} was successfully got from DB",
                latitude, longitude, count);
        return carsList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Car> getCarByParameters(Specification<Car> carSpecification, Pageable pageable) {
        LOGGER.debug("Try get cars by parameters from DB");
        Page<Car> cars = carRepository.findAll(carSpecification, pageable);
        if (cars.isEmpty()) {
            throw new EntityNotFoundException("Cars by parameters not fount, set some parameters");
        }
        LOGGER.debug("Cars by parameters was successfully got from DB");
        return cars;
    }
}
