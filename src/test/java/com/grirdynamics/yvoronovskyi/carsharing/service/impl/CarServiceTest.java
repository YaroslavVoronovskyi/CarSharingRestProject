package com.grirdynamics.yvoronovskyi.carsharing.service.impl;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarBodyStyle;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarClass;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarStatus;
import com.grirdynamics.yvoronovskyi.carsharing.model.Coordinates;
import com.grirdynamics.yvoronovskyi.carsharing.model.EngineType;
import com.grirdynamics.yvoronovskyi.carsharing.model.MachineDriveType;
import com.grirdynamics.yvoronovskyi.carsharing.model.Transmission;
import com.grirdynamics.yvoronovskyi.carsharing.repository.ICarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarServiceTest {

    private final static long TEST_CAR_ID = 1;

    @Mock
    ICarRepository carRepositoryMock;
    @InjectMocks
    CarService carService;

    @BeforeEach
    public void init() {
        initMocksForCars();
        initMocksForCar();
    }

    @Test
    public void shouldReturnExpectedCar() {
        assertEquals(carService.getCarBuId(TEST_CAR_ID), createTestCar());
    }

    @Test
    public void shouldReturnExpectedCarsList() {
        assertEquals(carService.getAllCars(), List.of(createTestCar()));
    }

    @Test
    public void shouldRegisterNewCar() {
        Car car = createTestCar();
        carService.createNewCar(car);
        Mockito.verify(carRepositoryMock).save(car);
    }

    @Test
    public void shouldUpdateCarInformation() {
        Car car = carService.getCarBuId(TEST_CAR_ID);
        car.setCarStatus(CarStatus.FREE);
        carService.updateCar(car);
        Mockito.verify(carRepositoryMock).save(car);
    }

    @Test
    public void shouldDeleteCarInformation() {
        carService.deleteCar(TEST_CAR_ID);
        Mockito.verify(carRepositoryMock).deleteById(TEST_CAR_ID);
    }

    @Test
    public void shouldReturnCarByCoordinates() {
        Mockito.when(carRepositoryMock.findNearestCarByLocation(19.834216, 14.032527, 1L))
                .thenReturn(List.of(createTestCar()));
        assertEquals(carService.getCarByLocation(19.834216, 14.032527, 1L), List.of(createTestCar()));
    }

    private void initMocksForCar() {
        Mockito.when(carRepositoryMock.getReferenceById(TEST_CAR_ID)).thenReturn(createTestCar());
    }

    private void initMocksForCars() {
        Mockito.when(carRepositoryMock.findAll()).thenReturn(List.of(createTestCar()));
    }

    private Car createTestCar() {
        return Car.builder()
                .id(1)
                .brand("Renault")
                .model("Meagan 3")
                .constructionYear(2011)
                .mileage(18000)
                .fuelLevel(30.3)
                .fuelConsumption(7.5)
                .licencePlate("ВС2222СР")
                .carBodyStyle(CarBodyStyle.SEDAN)
                .carClass(CarClass.MEDIUM)
                .carStatus(CarStatus.RENTED)
                .engineType(EngineType.DIESEL)
                .machineDriveType(MachineDriveType.FRONT_WHEEL_DRIVE)
                .transmission(Transmission.MANUAL_TRANSMISSION)
                .carOwnerId(1)
                .coordinates(Coordinates.builder()
                        .id(1)
                        .latitude(19.834216)
                        .longitude(14.032527)
                        .build())
                .build();
    }
}
