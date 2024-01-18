package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ua.foxminded.dto.StudentDto;
import ua.foxminded.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
	@Mapping(target = "name", source = "student.nickName")
	StudentDto studentToStudentDto (Student student, @Context CycleAvoidingMappingContext context);
	
	@Mapping(target = "nickName", source = "studentDto.name")
	Student studentDtoToStudent (StudentDto studentDto, @Context CycleAvoidingMappingContext context);

}
