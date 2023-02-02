package com.grirdynamics.yvoronovskyi.carsharing.controller.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grirdynamics.yvoronovskyi.carsharing.config.AppConfigTest;
import com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto.CarDto;
import com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto.CoordinatesDto;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {AppConfigTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CarsRestControllerTestIT {

    @LocalServerPort
    Integer port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @Order(1)
    @Sql(scripts = {"/ScriptInsertCarService.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnExpectedCarsDtoList() throws JsonProcessingException {
        String response = restTemplate.getForObject("http://localhost:" + port + "/cars/", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CarDto> carsDtoList = mapper.readerForListOf(CarDto.class).readValue(response);
        assertEquals(carsDtoList.size(), 20);
        assertEquals(carsDtoList.get(0), List.of(createTestCarDto()).get(0));
        assertEquals(carsDtoList.get(0).getId(), 1L);
        assertEquals(carsDtoList.get(0).getBrand(), "Renault");
        assertEquals(carsDtoList.get(0).getModel(), "Meagan 3");
        assertEquals(carsDtoList.get(0).getConstructionYear(), 2011);
        assertEquals(carsDtoList.get(0).getMileage(), 18000);
        assertEquals(carsDtoList.get(0).getFuelLevel(), 30.3);
        assertEquals(carsDtoList.get(0).getFuelConsumption(), 7.5);
        assertEquals(carsDtoList.get(0).getLicencePlate(), "BC2222CB");
        assertEquals(carsDtoList.get(0).getCarBodyStyle(), "SEDAN");
        assertEquals(carsDtoList.get(0).getCarClass(), "MEDIUM");
        assertEquals(carsDtoList.get(0).getCarStatus(), "RENTED");
        assertEquals(carsDtoList.get(0).getEngineType(), "DIESEL");
        assertEquals(carsDtoList.get(0).getMachineDriveType(), "FRONT_WHEEL_DRIVE");
        assertEquals(carsDtoList.get(0).getTransmission(), "MANUAL_TRANSMISSION");
        assertEquals(carsDtoList.get(0).getCarOwnerId(), 1L);
        assertEquals(carsDtoList.get(0).getCoordinates().getId(), 1L);
        assertEquals(carsDtoList.get(0).getCoordinates().getLatitude(), 19.834216);
        assertEquals(carsDtoList.get(0).getCoordinates().getLongitude(), 14.032527);
    }

    @Test
    @Order(2)
    void shouldReturnExpectedCarDtoById() {
        CarDto carDto = restTemplate
                .getForObject("http://localhost:" + port + "/cars/1", CarDto.class);
        assertEquals(carDto, createTestCarDto());
        assertNotNull(carDto);
        assertEquals(carDto.getId(), 1L);
        assertEquals(carDto.getBrand(), "Renault");
        assertEquals(carDto.getModel(), "Meagan 3");
        assertEquals(carDto.getConstructionYear(), 2011);
        assertEquals(carDto.getMileage(), 18000);
        assertEquals(carDto.getFuelLevel(), 30.3);
        assertEquals(carDto.getFuelConsumption(), 7.5);
        assertEquals(carDto.getLicencePlate(), "BC2222CB");
        assertEquals(carDto.getCarBodyStyle(), "SEDAN");
        assertEquals(carDto.getCarClass(), "MEDIUM");
        assertEquals(carDto.getCarStatus(), "RENTED");
        assertEquals(carDto.getEngineType(), "DIESEL");
        assertEquals(carDto.getMachineDriveType(), "FRONT_WHEEL_DRIVE");
        assertEquals(carDto.getTransmission(), "MANUAL_TRANSMISSION");
        assertEquals(carDto.getCarOwnerId(), 1L);
        assertEquals(carDto.getCoordinates().getId(), 1L);
        assertEquals(carDto.getCoordinates().getLatitude(), 19.834216);
        assertEquals(carDto.getCoordinates().getLongitude(), 14.032527);
    }

    @Test
    @Order(3)
    public void shouldRegisterNewCarDto() throws JsonProcessingException {
        CarDto carDto = createTestCarDtoWithOutId();
        restTemplate.postForEntity("http://localhost:" + port + "/cars/", carDto, CarDto.class);
        String response = restTemplate.getForObject("http://localhost:" + port + "/cars/", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CarDto> carsDtoList = mapper.readerForListOf(CarDto.class).readValue(response);
        CarDto carDtoExpected = restTemplate.getForObject("http://localhost:" + port + "/cars/21", CarDto.class);
        assertEquals(carsDtoList.size(), 21);
        assertNotNull(carDtoExpected);
        assertEquals(carDtoExpected.getId(), 21L);
        assertEquals(carDtoExpected.getLicencePlate(), "BC9876CB");
    }

    @Test
    @Order(4)
    public void shouldUpdateExpectedCarDto() {
        CarDto carDto = restTemplate.getForObject("http://localhost:" + port + "/cars/1", CarDto.class);
        carDto.setFuelLevel(0.0);
        restTemplate.put("http://localhost:" + port + "/cars/1", carDto);
        CarDto carDtoExpected = restTemplate.getForObject("http://localhost:" + port + "/cars/1", CarDto.class);
        assertNotNull(carDtoExpected);
        assertEquals(carDtoExpected.getId(), 1L);
        assertEquals(carDtoExpected.getFuelLevel(), 0.0);
    }

    @Test
    @Order(5)
    public void shouldDeleteExpectedCarDtoById() throws JsonProcessingException {
        restTemplate.delete("http://localhost:" + port + "/cars/20");
        String response = restTemplate.getForObject("http://localhost:" + port + "/cars/", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<CarDto> carsDtoList = mapper.readerForListOf(CarDto.class).readValue(response);
        assertEquals(carsDtoList.size(), 20);
        assertFalse(carsDtoList.contains(CarDto.builder().id(20L).build()));
    }

//    @Test
//    @Order(6)
//    @Disabled
//    public void shouldReturnCarsDtoListByCoordinates() throws JsonProcessingException {
//        Double latitude = 19.834216;
//        Double longitude = 14.032527;
//        Long count = 2L;
//        String response = restTemplate.getForObject("http://localhost:" + port + "/cars/find/location?count=2", String.class, latitude, longitude, count);
//        ObjectMapper mapper = new ObjectMapper();
//        List<CarDto> carsDtoList = mapper.readerForListOf(CarDto.class).readValue(response);
//        assertEquals(carsDtoList.size(), 2);
//    }

    @Test
    @Order(7)
    public void shouldThrowExceptionWhenTryCreateNewCarDto() {
        CarDto carDto = createTestCarDto();
        carDto.setLicencePlate("BC11111CB");
        assertThrows(HttpClientErrorException.class, () ->
                restTemplate.postForEntity("http://localhost:" + port + "/cars/registration/", carDto, CarDto.class));
    }

    @Test
    @Order(8)
    public void shouldThrowExceptionWhenTryUpdateExpectedCarDto() {
        CarDto carDto = createTestCarDto();
        carDto.setLicencePlate("");
        assertThrows(HttpClientErrorException.class, () ->
                restTemplate.put("http://localhost:" + port + "/cars/1", carDto, CarDto.class));
    }

    @Test
    @Order(9)
    public void shouldThrowExceptionWhenTryDeleteExpectedCarDto() {
        assertThrows(HttpClientErrorException.class, () ->
                restTemplate.delete("http://localhost:" + port + "/cars/100"));
    }

    @Test
    @Order(10)
    public void shouldThrowExceptionWhenTryGetExpectedCarDto() {
        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.getForObject("http://localhost:" + port + "/cars/100", CarDto.class);
        });
    }

//    @Test
//    @Order(11)
//    public void shouldReturnExpectedCarByParameters() throws JsonProcessingException {
//        String response = restTemplate.getForObject("http://localhost:" + port + "/cars/search?brand=Renault&model=Meagan 3&carClass=MEDIUM&carStatus=FREE&engineType=GASOLINE&transmission=AUTOMATIC_TRANSMISSION", String.class);
//        ObjectMapper mapper = new ObjectMapper();
//        List<CarDto> carsDtoList = mapper.readerForListOf(CarDto.class).readValue(response);
//        assertEquals(carsDtoList.size(), 20);
//        assertEquals(carsDtoList.get(0), List.of(createTestCarDto()).get(0));
//        assertEquals(carsDtoList.get(0).getId(), 1L);
//        assertEquals(carsDtoList.get(0).getBrand(), "Renault");
//        assertEquals(carsDtoList.get(0).getModel(), "Meagan 3");
//        assertEquals(carsDtoList.get(0).getConstructionYear(), 2011);
//        assertEquals(carsDtoList.get(0).getMileage(), 18000);
//        assertEquals(carsDtoList.get(0).getFuelLevel(), 30.3);
//        assertEquals(carsDtoList.get(0).getFuelConsumption(), 7.5);
//        assertEquals(carsDtoList.get(0).getLicencePlate(), "BC2222CB");
//        assertEquals(carsDtoList.get(0).getCarBodyStyle(), "SEDAN");
//        assertEquals(carsDtoList.get(0).getCarClass(), "MEDIUM");
//        assertEquals(carsDtoList.get(0).getCarStatus(), "RENTED");
//        assertEquals(carsDtoList.get(0).getEngineType(), "DIESEL");
//        assertEquals(carsDtoList.get(0).getMachineDriveType(), "FRONT_WHEEL_DRIVE");
//        assertEquals(carsDtoList.get(0).getTransmission(), "MANUAL_TRANSMISSION");
//        assertEquals(carsDtoList.get(0).getCarOwnerId(), 1L);
//        assertEquals(carsDtoList.get(0).getCoordinates().getId(), 1L);
//        assertEquals(carsDtoList.get(0).getCoordinates().getLatitude(), 19.834216);
//        assertEquals(carsDtoList.get(0).getCoordinates().getLongitude(), 14.032527);
//    }

    private CarDto createTestCarDto() {
        return CarDto.builder()
                .id(1L)
                .brand("Renault")
                .model("Meagan 3")
                .constructionYear(2011)
                .mileage(18000)
                .fuelLevel(30.3)
                .fuelConsumption(7.5)
                .licencePlate("BC2222CB")
                .carBodyStyle("SEDAN")
                .carClass("MEDIUM")
                .carStatus("RENTED")
                .engineType("DIESEL")
                .machineDriveType("FRONT_WHEEL_DRIVE")
                .transmission("MANUAL_TRANSMISSION")
                .carOwnerId(1L)
                .coordinates(CoordinatesDto.builder()
                        .id(1L)
                        .latitude(19.834216)
                        .longitude(14.032527)
                        .build())
                .build();
    }

    private CarDto createTestCarDtoWithOutId() {
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
                .carOwnerId(10L)
                .coordinates(CoordinatesDto.builder()
                        .latitude(19.834216)
                        .longitude(14.032527)
                        .build())
                .build();
    }
}
