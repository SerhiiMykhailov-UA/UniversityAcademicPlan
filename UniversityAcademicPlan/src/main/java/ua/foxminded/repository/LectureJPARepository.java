package ua.foxminded.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.foxminded.entity.Lecture;

public interface LectureJPARepository extends JpaRepository<Lecture, Long> {

	boolean existsByName (String name);
	
	Optional<Lecture> findByName (String name);
}
