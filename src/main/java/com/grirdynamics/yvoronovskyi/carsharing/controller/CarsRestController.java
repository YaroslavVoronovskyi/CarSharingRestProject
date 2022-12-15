package com.grirdynamics.yvoronovskyi.carsharing.controller;

import com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto.CarDto;
import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.service.ICarService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarsRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarsRestController.class);
    private final ICarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarsRestController(ICarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public CarDto getCarById(@PathVariable("id") long carId) {
        LOGGER.debug("Try get car wih id {}", carId);
        Car car = carService.get(carId);
        LOGGER.debug("Car wih id {} was successfully got", carId);
        return convertToDto(car);
    }

    @GetMapping()
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<CarDto> showAllCarsOnMap() {
        LOGGER.debug("Try get all cars");
        List<CarDto> carsDtoList = convertToDtoList(carService.getAll());
        LOGGER.debug("All cars was successfully got");
        return carsDtoList;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public CarDto registerNewCar(@RequestBody @Valid CarDto carDto) {
        LOGGER.debug("Try register new car");
        Car car = carService.create(convertToEntity(carDto));
        LOGGER.debug("New car was registered");
        return convertToDto(car);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public CarDto updateCarInformation(@PathVariable("id") long carId, @RequestBody @Valid CarDto carDto) {
        LOGGER.debug("Try update car wih id {}", carId);
        carDto.setId(carId);
        carService.update(convertToEntity(carDto));
        LOGGER.debug("Car was updated wih id {}", carId);
        return carDto;
    }

    @DeleteMapping("/{id}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public void deleteCarInformation(@PathVariable("id") long carId) {
        LOGGER.debug("Try delete car wih id {}", carId);
        carService.delete(carId);
        LOGGER.debug("Car was deleted wih id {}", carId);
    }

    @GetMapping("/find/location")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<CarDto> findNearestCarByLocation(@RequestParam Double latitude, @RequestParam Double longitude,
                                                 @Param("count") Long count) {
        LOGGER.debug("Try get cars with latitude {} longitude {} and count {}", latitude, longitude, count);
        List<Car> carsList = carService.getCarByLocation(latitude, longitude, count);
        LOGGER.debug("Cars with latitude {} longitude {} and count {} was successfully got", latitude, longitude, count);
        return convertToDtoList(carsList);
    }

    private CarDto convertToDto(Car car) {
        try {
            return modelMapper.map(car, CarDto.class);
        } catch (org.modelmapper.MappingException exception) {
            throw new EntityNotFoundException("Car not found!");
        }
    }

    private Car convertToEntity(CarDto carDto) {
        return modelMapper.map(carDto, Car.class);
    }

    private List<CarDto> convertToDtoList(List<Car> carsList) {
        return carsList.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
