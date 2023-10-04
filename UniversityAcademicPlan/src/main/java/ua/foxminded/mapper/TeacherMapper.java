package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
	
//	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
	
	TeacherDto teacherToTeacherDto (Teacher teacher);
	
	Teacher teacherDtoToTeacher (TeacherDto teacherDto);

}
