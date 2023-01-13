package com.grirdynamics.yvoronovskyi.carsharing.repository;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

}
