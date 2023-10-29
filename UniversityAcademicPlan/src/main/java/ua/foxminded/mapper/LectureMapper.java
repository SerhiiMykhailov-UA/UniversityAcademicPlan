package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.LectureDto;
import ua.foxminded.entity.Lecture;

@Mapper(componentModel = "spring")
public interface LectureMapper {
	
	LectureDto lectureToLectureDto (Lecture lecture, @Context CycleAvoidingMappingContext context);
	
	Lecture lectureDtoToLecture (LectureDto lectureDto, @Context CycleAvoidingMappingContext context);

}
