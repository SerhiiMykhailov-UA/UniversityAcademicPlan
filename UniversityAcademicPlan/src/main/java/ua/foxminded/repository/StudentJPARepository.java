package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Student;

public interface StudentJPARepository extends JpaRepository<Student, Integer> {

}
