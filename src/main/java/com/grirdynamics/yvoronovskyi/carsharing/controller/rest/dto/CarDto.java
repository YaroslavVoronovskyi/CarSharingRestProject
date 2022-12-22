package com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto;

import com.grirdynamics.yvoronovskyi.carsharing.model.CarBodyStyle;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarClass;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarStatus;
import com.grirdynamics.yvoronovskyi.carsharing.model.EngineType;
import com.grirdynamics.yvoronovskyi.carsharing.model.MachineDriveType;
import com.grirdynamics.yvoronovskyi.carsharing.model.Transmission;
import com.grirdynamics.yvoronovskyi.carsharing.util.IEnumValidator;
import com.grirdynamics.yvoronovskyi.carsharing.util.ILicensePlateValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    @Size(min = 1, max = 10, message = "can not be less then 1 and more then 10 characters")
    @NotBlank(message = " can not be null or empty")
    private String brand;

    @Size(min = 1, max = 10, message = "can not be less then 1 and more then 10 characters")
    @NotBlank(message = " can not be null or empty")
    private String model;

    @Min(1990)
    @Max(2022)
    @NotNull(message = "must be higher then 1990 year and be present date")
    private int constructionYear;

    @Min(0)
    @NotNull(message = "can not be null or empty and less then 0")
    private int mileage;

    @Min(0)
    @Max(100)
    @NotNull(message = "can not be null or empty, less then 0 and more then 70")
    private double fuelLevel;

    @Min(4)
    @Max(25)
    @NotNull(message = "can not be null or empty, less then 4 and more then 25")
    private double fuelConsumption;

    @ILicensePlateValidator(message = "should have format -> BC1111CB and should have 8 characters")
    private String licencePlate;

    @IEnumValidator(enumClass = CarBodyStyle.class, message = "must be any of: MOTORCYCLE, MICRO, SEDAN, " +
            "MINIVAN, CUV, SUV, HATCHBACK, CABRIOLET, SUPERCAR, PICKUP, LIMOUSINE, CAMPER_VAN")
    private String carBodyStyle;

    @IEnumValidator(enumClass = CarClass.class, message = "must be any of: ECONOMY, MEDIUM, BUSINESS, PREMIUM")
    private String carClass;

    @IEnumValidator(enumClass = CarStatus.class, message = "must be any of: LOCKED, FREE, RENTED")
    private String carStatus;

    @IEnumValidator(enumClass = EngineType.class, message = "must be any of: ELECTRIC, DIESEL, GASOLINE, " +
            "GAS, HYBRID")
    private String engineType;

    @IEnumValidator(enumClass = MachineDriveType.class, message = "must be any of: FRONT_WHEEL_DRIVE, " +
            "REAR_WHEEL_DRIVE, ALL_WHEEL_DRIVE")
    private String machineDriveType;

    @IEnumValidator(enumClass = Transmission.class, message = "must be any of: MANUAL_TRANSMISSION, " +
            "AUTOMATIC_TRANSMISSION")
    private String transmission;

    @NotNull(message = "Car owner ID can not be null or empty")
    private long carOwnerId;

    @NotNull(message = "Coordinates can not be null or empty")
    private CoordinatesDto coordinates;
}
