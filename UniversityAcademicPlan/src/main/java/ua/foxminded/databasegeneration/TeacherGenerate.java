package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;

import ua.foxminded.entity.Teacher;


public class TeacherGenerate {
	
	public static List<Teacher> teacherGenerate() {
		List<Teacher> teachersList = new ArrayList<>();
		teachersList.add(new Teacher("Sheren", "Stown"));
		teachersList.add(new Teacher("Gans", "Anderson"));
		teachersList.add(new Teacher("Jon", "Tolkien"));
		teachersList.add(new Teacher("Pamela", "Anderson"));
		teachersList.add(new Teacher("Ace", "Ventura"));
		teachersList.add(new Teacher("Don", "Juan"));
		teachersList.add(new Teacher("Avraam", "Lincoln"));
		teachersList.add(new Teacher("Demy", "Moor"));
		teachersList.add(new Teacher("Merlin", "Monro"));
		return teachersList;
	}

}
