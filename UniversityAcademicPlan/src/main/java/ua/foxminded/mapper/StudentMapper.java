package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.entity.Student;

@Mapper
public interface StudentMapper {
	
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	StudentDto studentToStudentDto (Student student);

}
