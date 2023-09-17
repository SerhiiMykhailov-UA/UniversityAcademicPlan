package ua.foxminded.databaseconfiguration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.entity.Users;

public class UserGenerate {

	private List<Teacher> teachers;
	private List<Student> students;
	
	public UserGenerate(List<Student> students, List<Teacher> teachers) {
		this.students = students;
		this.teachers = teachers;
	}
	
	public Map<Users, ? extends Users> name() {
		int mapSize = teachers.size() + students.size();
		Map<Users, ? extends Users> usersMap = new LinkedHashMap<>(mapSize);
		usersMap.entrySet().forEach(null);
		return null;
	}
}
