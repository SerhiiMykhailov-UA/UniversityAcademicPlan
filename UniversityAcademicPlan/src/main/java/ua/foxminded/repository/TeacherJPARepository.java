package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Teacher;

public interface TeacherJPARepository extends JpaRepository<Teacher, Integer> {

}
