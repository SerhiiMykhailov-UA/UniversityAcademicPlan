package ua.foxminded.databaseconfiguration;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.StudentService;

@Profile("!test")
@Component
public class DataBaseConfiguration {
	private CourseService courseService;
	private StudentService studentService;
	private GroupsService groupService;
//	private final List<Student> studentsListStart = StudentsRandomNameGenerate.studentsRandomNameGenerate();
	private static final List<String> groupsListStart = GroupGenerate.groupGenerate();
	private static final List<Course> coursesListStart = CoursesListGenerate.coursesList();
	private static final List<Teacher> teachers = TeacherGenerate.teacherGenerate();
	
	private DataBaseConfiguration(CourseService courseService, StudentService studentService,
			GroupsService groupService) {
		this.courseService = courseService;
		this.studentService = studentService;
		this.groupService = groupService; 
	}

	public void dataBaseConfiguration() throws SQLException, IOException, DataAccessException {

		// Fill-up courses Data table
		for (Course s:coursesListStart) {
			courseService.add(s);
		}
		// Fill-up group Data table
		for(String s:groupsListStart) {
			groupService.add(new Groups(s));
		}
		// Fill-up student Data table
		List<Groups> groupList = groupService.getAll();
		List<Student> studentListAssignGroup = AssignStudentsToGroups.assign(groupList);
		for(Student st:studentListAssignGroup) {
			studentService.add(st);
		}
		
	}
}
