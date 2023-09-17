package ua.foxminded.databaseconfiguration;

import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;

public final class AssignCourseToGroups {

	private List<Groups> groupsList;
	private List<Course> coursesList;

	public AssignCourseToGroups(List<Groups> groupsList, List<Course> coursesList) {
		this.groupsList = groupsList;
		this.coursesList = coursesList;
	}

	public List<Groups> assignCourseToGroups() {
		for (int i =0; i<groupsList.size(); i++) {
			Groups group = groupsList.get(i);
			List<Course> coursesInGroup = group.getCourse();
			while (coursesInGroup.size() < 3) {
				Random random = new Random();
				int indexCourse = random.nextInt(9);
				Course course = coursesList.get(indexCourse);
				if (!coursesInGroup.contains(course))
					coursesInGroup.add(course);
			}
			group.setCourse(coursesInGroup);
			groupsList.set(i, group);

		}
		return groupsList;
	}

}
