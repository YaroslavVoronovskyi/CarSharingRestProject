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

//    @Query(nativeQuery = true, value = "SELECT * FROM cars where latitude = ?  AND longitude = ?  LIMIT ?")

//    @Query(nativeQuery = true, value = "SELECT (3959 * acos(cos(RADIANS(latitude = ?))" +
//            "* cos(RADIANS(lat))" +
//            "* cos(RADIANS(lng)" +
//            "- radians(longitude = ?)) " +
//            "+ sin(RADIANS(longitude)) " +
//            "* sin(RADIANS(lat)))) AS distance " +
//            "FROM cars ")

//    @Query(nativeQuery = true, value = "SELECT *, 3956 * 2 * ASIN(SQRT( POWER(SIN((latitude = ? - abs(dest.lat)) " +
//            "* pi()/180 / 2), 2) + COS(latitude = ? * pi()/180 ) * COS(abs(dest.lat) * pi()/180) " +
//            "* POWER(SIN((longitude = ? - dest.lon) * pi()/180 / 2), 2) )) as distance " +
//            "FROM cars " +
//            "having distance < distance ?")

//    @Query(nativeQuery = true, value = "select * from (SELECT *,( 3959 * acos( cos( radians(latitude = ?) ) " +
//            "* cos( radians( latitude ) ) * cos( radians( longitude ) - radians(longitude = ?) ) + sin( radians(latitude) ) " +
//            "* sin( radians( latitude ) ) ) ) AS distance " +
//            "FROM cars) AS distance " +
//            "LIMIT ?")

//    @Query(nativeQuery = true, value = "SELECT * FROM (" +
//            "SELECT  *,(6371 * acos( cos( radians(latitude = ?) ) " +
//            "* cos( radians( latitude ) ) " +
//            "* cos( radians( longitude = ?) - radians(latitude) ) " +
//            "+ sin( radians(longitude) ) " +
//            "* sin( radians( latitude ) ) ) ) AS distance " +
//            "FROM cars) al" +
//            "ORDER BY distance ASC LIMIT 5")
//    List<Car> findNearestCarByLocation(@RequestParam Double latitude, @RequestParam Double longitude,
//                                       @Param("count") Long count);

//    @Query(nativeQuery = true, value = "SELECT * FROM (" +
//            "SELECT  *,( 6371 * acos( cos( radians( latitude = ? ) ) " +
//            "* cos( radians( latitude ) ) " +
//            "* cos( radians( longitude = ? ) - radians( latitude ) ) " +
//            "+ sin( radians( longitude) ) " +
//            "* sin( radians( latitude ) ) ) ) AS distance " +
//            "FROM cars ) al " +
//            "LIMIT ?")

    @Query(nativeQuery = true, value = "SELECT * FROM ( " +
            "SELECT  *,(6371 * acos( cos( radians( latitude = ? ) ) " +
            "* cos( radians( latitude ) ) " +
            "* cos( radians( longitude ) - radians( latitude ) ) " +
            "+ sin( radians( longitude = ? ) ) " +
            "* sin( radians( latitude ) ) ) ) AS distance " +
            "FROM cars) al " +
            //-- WHERE distance < 600
            "ORDER BY distance LIMIT ?;")
    List<Car> findNearestCarByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
                                       @Param("count") Long count);
}
