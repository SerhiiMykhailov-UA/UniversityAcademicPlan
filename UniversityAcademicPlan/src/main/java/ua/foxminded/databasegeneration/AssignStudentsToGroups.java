package ua.foxminded.databasegeneration;

import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;

public final class AssignStudentsToGroups {
	
	public static List<Student> assign(List<Groups> groupsList, List<Student> studentList) {
//	    List<Student> studentsList = new ArrayList<>();
		for (Student el: studentList) {
			Random random = new Random();
			int i = random.nextInt(groupsList.size());
			Groups group = groupsList.get(i);
			List<Student> studentInGroupList = group.getStudent();
			if (studentInGroupList.size() < 30) {
				studentInGroupList.add(el);
				group.setStudent(studentInGroupList);
				groupsList.set(i, group);
				el.setGroups(group); 
			}
		}
		return studentList;
	}
}
