package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.LectureDto;
import ua.foxminded.entity.Lecture;

@Mapper(componentModel = "spring")
public interface LectureMapper {
	
	LectureDto lectureToLectureDto (Lecture lecture);
	
	Lecture lectureDtoToLecture (LectureDto lectureDto);

}
