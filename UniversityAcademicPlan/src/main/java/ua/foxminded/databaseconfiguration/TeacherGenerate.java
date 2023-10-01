package ua.foxminded.databaseconfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Teacher;

public class TeacherGenerate {
	private static List<Course> coursesList = CoursesListGenerate.coursesList();
	
	public static List<Teacher> teacherGenerate() {
		List<Teacher> teachersList = new ArrayList<>();
		teachersList.add(new Teacher("Sheren", "Stown", new ArrayList<>(Arrays.asList(coursesList.get(0), coursesList.get(5)))));
		teachersList.add(new Teacher("Gans", "Anderson", new ArrayList<>(Arrays.asList(coursesList.get(1), coursesList.get(6)))));
		teachersList.add(new Teacher("Jon", "Tolkien", new ArrayList<>(Arrays.asList(coursesList.get(2), coursesList.get(7)))));
		teachersList.add(new Teacher("Pamela", "Anderson", new ArrayList<>(Arrays.asList(coursesList.get(3), coursesList.get(8)))));
		teachersList.add(new Teacher("Ace", "Ventura", new ArrayList<>(Arrays.asList(coursesList.get(4), coursesList.get(9)))));
		return teachersList;
	}

}
