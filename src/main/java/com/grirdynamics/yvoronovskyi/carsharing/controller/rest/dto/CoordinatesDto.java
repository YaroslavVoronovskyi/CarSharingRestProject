package com.grirdynamics.yvoronovskyi.carsharing.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    @Min(-90)
    @Max(90)
    @NotNull
    @NotNull(message = "Latitude can not be null or empty")
    private double latitude;

    @Min(-180)
    @Max(180)
    @NotNull(message = "Longitude can not be null or empty")
    private double longitude;
}
