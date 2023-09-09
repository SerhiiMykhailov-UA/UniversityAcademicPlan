package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Location;

public interface LocationJPARepository extends JpaRepository<Location, Integer> {

}
