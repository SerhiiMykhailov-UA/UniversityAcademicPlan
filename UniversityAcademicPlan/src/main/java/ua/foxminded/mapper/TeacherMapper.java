package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Teacher;

@Mapper
public interface TeacherMapper {
	
	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);
	
	TeacherDto teacherToTeacherDto (Teacher teacher);

}
