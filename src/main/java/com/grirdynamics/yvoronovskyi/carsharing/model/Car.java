package com.grirdynamics.yvoronovskyi.carsharing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.CascadeType.*;

@Builder
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int constructionYear;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private double fuelLevel;

    @Column(nullable = false)
    private double fuelConsumption;

    @Column(nullable = false, unique = true)
    private String licencePlate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarBodyStyle carBodyStyle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarClass carClass;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MachineDriveType machineDriveType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    private long carOwnerId;

    @OneToOne(fetch = FetchType.EAGER, cascade = {PERSIST, REFRESH, MERGE}, targetEntity = Coordinates.class)
    @JoinColumns({
            @JoinColumn(name = "coordinates_id", referencedColumnName = "id"),
            @JoinColumn(name = "latitude", referencedColumnName = "latitude"),
            @JoinColumn(name = "longitude", referencedColumnName = "longitude")
    })
    private Coordinates coordinates;
}
