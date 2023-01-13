package com.grirdynamics.yvoronovskyi.carsharing.service.specification;

import com.grirdynamics.yvoronovskyi.carsharing.model.Car;
import com.grirdynamics.yvoronovskyi.carsharing.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CarSpecificationStatus implements Specification<Car> {

    private CarStatus[] carStatusesArray;

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (carStatusesArray == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        List<Predicate> predicatesList = Arrays.stream(carStatusesArray)
                .map(element -> criteriaBuilder.like(root.get("carStatus").as(String.class), element.toString()))
                .collect(Collectors.toList());
        return criteriaBuilder.or(predicatesList.toArray(new Predicate[]{}));
    }
}
