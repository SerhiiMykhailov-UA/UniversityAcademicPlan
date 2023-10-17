package ua.foxminded.databasegeneration;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;

public final class AssignStudentsToGroups {
	
	private static List<Student> studentList = StudentsRandomNameGenerate.studentsRandomNameGenerate();

	public static List<Student> assign(List<Groups> groupList) throws SQLException {
	    List<Groups> groupsList = groupList;
	    List<Student> studentsList = studentList;
		for (Student el: studentsList) {
			Random random = new Random();
			int i = random.nextInt(9)+1;
			Groups group = groupsList.get(i);
			List<Student> studentInGroupList = group.getStudent();
//			long idGroup = group.getId();
//			long numberOfMembers = ;
			if (studentInGroupList.size() < 30) {
				studentInGroupList.add(el);
				group.setStudent(studentInGroupList);
				groupsList.set(i, group);
//				group.setNumberOfMembers(numberOfMembers+1);
				el.setGroups(group); 
			}
		}
		return studentsList;
	}
}
