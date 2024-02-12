package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;

public final class AssignStudentsToGroups {
	
	public static List<Student> assign(List<Groups> groupsList, List<Student> studentList) {
	    List<Groups> groupsTempList = new ArrayList<>();
	    groupsTempList.addAll(groupsList);
		
		for (Student el: studentList) {
			Random random = new Random();
			int i = random.nextInt(groupsTempList.size());
			Groups group = groupsTempList.get(i);
			List<Student> studentInGroupList = group.getStudent();
			if (studentInGroupList.size() < 30) {
				studentInGroupList.add(el);
				group.setStudent(studentInGroupList);
				groupsTempList.set(i, group);
				el.setGroups(groupsList.get(i)); 
			}
		}
		return studentList;
	}
}
