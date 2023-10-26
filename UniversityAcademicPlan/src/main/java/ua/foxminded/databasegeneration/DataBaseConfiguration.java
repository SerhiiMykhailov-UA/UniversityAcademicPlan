package ua.foxminded.databasegeneration;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.mapper.CourseMapper;
import ua.foxminded.mapper.GroupsMapper;
import ua.foxminded.mapper.StudentMapper;
import ua.foxminded.mapper.TeacherMapper;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;

//@Profile("!test")
//@Component
public class DataBaseConfiguration {
	private CourseService courseService;
	private StudentService studentService;
	private GroupsService groupService;
	private TeacherService teacherService;
	private GroupsMapper groupsMapper;
	private StudentMapper studentMapper;
	private CourseMapper courseMapper;
	private TeacherMapper teacherMapper;
//	private final List<Student> studentsListStart = StudentsRandomNameGenerate.studentsRandomNameGenerate();
	private static final List<String> groupsListStart = GroupGenerate.groupGenerate();
	private static final List<Course> coursesListStart = CoursesListGenerate.coursesList();
	private static final List<Teacher> teachers = TeacherGenerate.teacherGenerate();
	
	private DataBaseConfiguration(CourseService courseService, StudentService studentService,
			GroupsService groupService, TeacherService teacherService, GroupsMapper groupsMapper, StudentMapper studentMapper,
			CourseMapper courseMapper, TeacherMapper teacherMapper) {
		this.courseService = courseService;
		this.studentService = studentService;
		this.groupService = groupService; 
		this.courseMapper = courseMapper;
		this.groupsMapper = groupsMapper;
		this.studentMapper = studentMapper;
		this.teacherService = teacherService;
		this.teacherMapper = teacherMapper;
	}

	public void dataBaseConfiguration() throws SQLException, IOException, DataAccessException {

		// Fill-up courses Data table
		for (Course s:coursesListStart) {
//			courseService.add(courseMapper.courseToCourseDto(s));
			new Course(0, null, null, teachers, null, null, null, null);
		}
		// Fill-up group Data table
		for(String s:groupsListStart) {
			groupService.add(groupsMapper.groupsToGroupsDto(new Groups(s)));
		}
		// Fill-up student Data table
		List<Groups> groupList = groupService.getAll().stream().map(groupsMapper::groupsDtoToGroups).collect(Collectors.toList());
	List<Student> studentListAssignGroup = AssignStudentsToGroups.assign(groupList);
		for(Student student:studentListAssignGroup) {
			studentService.add(studentMapper.studentToStudentDto(student));
		}
		
		// Fill-up teacher Data table
		for (Teacher teacher:teachers) {
			teacherService.add(teacherMapper.teacherToTeacherDto(teacher));
		}
	}
}
