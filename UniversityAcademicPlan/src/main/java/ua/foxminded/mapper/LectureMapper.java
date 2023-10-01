package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.LectureDto;
import ua.foxminded.entity.Lecture;

@Mapper
public interface LectureMapper {
	
	LectureMapper INSTANCE = Mappers.getMapper(LectureMapper.class);
	
	LectureDto lectureToLectureDto (Lecture lecture);

}
