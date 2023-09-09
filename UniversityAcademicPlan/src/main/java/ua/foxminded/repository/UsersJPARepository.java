package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Users;

public interface UsersJPARepository extends JpaRepository<Users, Integer> {

}
