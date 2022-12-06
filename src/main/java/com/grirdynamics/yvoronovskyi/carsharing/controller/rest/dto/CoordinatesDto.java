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
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private double latitude;

    @Min(-180)
    @Max(180)
    @NotBlank
    private double longitude;
}
