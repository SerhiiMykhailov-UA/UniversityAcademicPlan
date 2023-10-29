package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
//	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	StudentDto studentToStudentDto (Student student, @Context CycleAvoidingMappingContext context);
	
	Student studentDtoToStudent (StudentDto studentDto, @Context CycleAvoidingMappingContext context);

}
