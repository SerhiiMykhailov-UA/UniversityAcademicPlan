package ua.foxminded.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Lecture;

public interface LectureJPARepository extends JpaRepository<Lecture, Integer> {

}
