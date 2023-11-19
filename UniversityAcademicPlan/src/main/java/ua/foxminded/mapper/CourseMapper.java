package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
	
	CourseDto courseToCourseDto (Course course, @Context CycleAvoidingMappingContext context);
	
	Course courseDtoToCourse (CourseDto courseDto,  @Context CycleAvoidingMappingContext context);
	

}
