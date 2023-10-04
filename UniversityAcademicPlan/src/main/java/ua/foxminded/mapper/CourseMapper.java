package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	
//	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
	
	CourseDto courseToCourseDto (Course course);
	
	Course courseDtoToCourse (CourseDto courseDto);

}
