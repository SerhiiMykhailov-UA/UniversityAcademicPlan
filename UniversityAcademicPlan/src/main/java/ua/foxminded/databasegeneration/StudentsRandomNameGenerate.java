package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Student;

public class StudentsRandomNameGenerate {
	
	public static List<Student> studentsRandomNameGenerate () {
		List<Student> studentsList = new ArrayList<>();
		String[] firstName = {"Bob", "Brian", "Eric", "Stiven", "Arnold", "Chack", "John", "Kevin", "Daniel", "Tony", 
				"Frank", "Donald", "Roy", "Kris", "Pol", "Michael", "David", "Robert", "Mark", "Tom"};
		String[] lastName = {"Marley", "Spilberg", "Pit", "Jobs", "Shvarz", "Travolta", "Stalone", "Chan", "Spiars", "Cruise", 
				"Senatra", "Trump", "Jons", "Tacker", "Mask", "Shumaher", "Bowie", "Downey", "Zuckerberg", "Shakespeare"};
	
		Random randomFirst = new Random();
		Random randomLast = new Random();
		while (studentsList.size()<200) {
			Student student = new Student();
					student.setFirstName(firstName[randomFirst.nextInt(19)]);
					student.setLastName(lastName[randomLast.nextInt(19)]);
			if (!studentsList.contains(student)) {
				studentsList.add(student);
			}
		}
		return studentsList;
	}
}
