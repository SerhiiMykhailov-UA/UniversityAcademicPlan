package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;

public final class AssignCourseToGroups {

	public static List<Groups> assignCourseToGroups(List<Groups> groupsList, List<Course> coursesList) {
		for (int i =0; i<groupsList.size(); i++) {
			Groups group = groupsList.get(i);
			List<Course> coursesInGroup = new ArrayList<>();
			while (coursesInGroup.size() < 3) {
				Random random = new Random();
				int indexCourse = random.nextInt(coursesList.size());
				Course courseDto = coursesList.get(indexCourse);
				if (!coursesInGroup.contains(courseDto))
					coursesInGroup.add(courseDto);
			}
			group.setCourse(coursesInGroup);
			groupsList.set(i, group);

		}
		return groupsList;
	}

}
