package com.grirdynamics.yvoronovskyi.carsharing.repository;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    @Query(nativeQuery = true, value = "SELECT * FROM cars where latitude = ?  AND longitude = ?  LIMIT ?")
    List<Car> findNearestCarByLocation(@RequestParam Double latitude, @RequestParam Double longitude, @Param("count") Long count);
}
