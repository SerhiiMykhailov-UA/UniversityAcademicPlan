package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Groups;

public interface GroupsJPARepository extends JpaRepository<Groups, Integer> {

}
