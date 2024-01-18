package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
	
	@Mapping(target = "name", source = "teacher.nickName")
	TeacherDto teacherToTeacherDto (Teacher teacher, @Context CycleAvoidingMappingContext context);
	
	@Mapping(target = "nickName", source = "teacherDto.name")
	Teacher teacherDtoToTeacher (TeacherDto teacherDto, @Context CycleAvoidingMappingContext context);

}
