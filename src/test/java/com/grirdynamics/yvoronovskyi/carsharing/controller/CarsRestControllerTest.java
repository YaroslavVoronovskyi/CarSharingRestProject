package com.grirdynamics.yvoronovskyi.carsharing.controller;

import com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto.CarDto;
import com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto.CoordinatesDto;
import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarBodyStyle;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarClass;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarStatus;
import com.grirdynamics.yvoronovskyi.carsharing.model.Coordinates;
import com.grirdynamics.yvoronovskyi.carsharing.model.EngineType;
import com.grirdynamics.yvoronovskyi.carsharing.model.MachineDriveType;
import com.grirdynamics.yvoronovskyi.carsharing.model.Transmission;
import com.grirdynamics.yvoronovskyi.carsharing.service.ICarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarsRestControllerTest {

    private final static long TEST_CAR_ID = 1;

    @Mock
    private ICarService carServiceMock;

    @Mock
    private ModelMapper modelMapperMock = new ModelMapper();

    @InjectMocks
    private CarsRestController carsRestController;

    @Test
    public void shouldReturnExpectedCarDtoById() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCarDto());
        Mockito.when(carServiceMock.getCarBuId(TEST_CAR_ID)).thenReturn(createTestCar());
        assertEquals(carsRestController.getCarById(TEST_CAR_ID), createTestCarDto());
    }

    @Test
    public void shouldReturnExpectedCarsDtoList() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCarDto());
        Mockito.when(carServiceMock.getAllCars()).thenReturn(List.of((createTestCar())));
        assertEquals(carsRestController.showAllCarsOnMap(), List.of(createTestCarDto()));
    }

    @Test
    public void shouldDeleteExceptionCarDtoById() {
        carsRestController.deleteCarInformation(TEST_CAR_ID);
        Mockito.verify(carServiceMock).deleteCar(TEST_CAR_ID);
    }

    @Test
    public void shouldRegisterNewCar() {
        Mockito.when(carServiceMock.createNewCar(modelMapperMock.map(createTestCarDto(), Car.class))).thenReturn(createTestCar());
        Mockito.when(modelMapperMock.map(createTestCar(), CarDto.class)).thenReturn(createTestCarDto());
        CarDto carDto = carsRestController.registerNewCar(createTestCarDto());
        assertEquals(carDto, createTestCarDto());
    }


    @Test
    public void shouldUpdateExceptionCarDtoById() {
        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCar());
        CarDto carDto = createTestCarDto();
        createTestCarDto().setCarStatus("FREE");
        carsRestController.updateCarInformation(TEST_CAR_ID, carDto);
        Mockito.verify(carServiceMock).updateCar(createTestCar());
    }

    @Test
    public void shouldReturnCarByCoordinates() {
        Mockito.when(carServiceMock.getCarByLocation(19.834216, 14.032527, 1L)).thenReturn(List.of(createTestCar()));
        List<CarDto> carsDtoList = carsRestController.findNearestCarByLocation(19.834216, 14.032527, 1L);
        assertEquals(carsDtoList.size(), 1);
    }

//    @Disabled
//    @Test
//    public void shouldThrowExceptionWhenTryUpdateCarInformation() {
//        Mockito.when(modelMapperMock.map(any(), any())).thenReturn(createTestCar());
//        CarDto carDto = createTestCarDto();
//        carDto.setFuelLevel(-1);
//        assertThrows(ConstraintViolationException.class, () -> carsRestController.updateCarInformation(TEST_CAR_ID, carDto));
//    }

    private CarDto createTestCarDto() {
        return CarDto.builder()
                .brand("Renault")
                .model("Meagan 3")
                .constructionYear(2011)
                .mileage(18000)
                .fuelLevel(30.3)
                .fuelConsumption(7.5)
                .licencePlate("BC9876CB")
                .carBodyStyle("SEDAN")
                .carClass("MEDIUM")
                .carStatus("RENTED")
                .engineType("DIESEL")
                .machineDriveType("FRONT_WHEEL_DRIVE")
                .transmission("MANUAL_TRANSMISSION")
                .carOwnerId(1L)
                .coordinates(CoordinatesDto.builder()
                        .latitude(19.834216)
                        .longitude(14.032527)
                        .build())
                .build();
    }

    private Car createTestCar() {
        return Car.builder()
                .brand("Renault")
                .model("Meagan 3")
                .constructionYear(2011)
                .mileage(18000)
                .fuelLevel(30.3)
                .fuelConsumption(7.5)
                .licencePlate("BC2222CB")
                .carBodyStyle(CarBodyStyle.SEDAN)
                .carClass(CarClass.MEDIUM)
                .carStatus(CarStatus.RENTED)
                .engineType(EngineType.DIESEL)
                .machineDriveType(MachineDriveType.FRONT_WHEEL_DRIVE)
                .transmission(Transmission.MANUAL_TRANSMISSION)
                .carOwnerId(1L)
                .coordinates(Coordinates.builder()
                        .latitude(19.834216)
                        .longitude(14.032527)
                        .build())
                .build();
    }
}
