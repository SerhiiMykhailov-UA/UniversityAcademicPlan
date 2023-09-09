package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Course;

public interface CourseJPARepository extends JpaRepository<Course, Integer> {

}
