package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;

@Mapper
public interface CourseMapper {
	
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
	
	CourseDto courseToCourseDto (Course course);

}
