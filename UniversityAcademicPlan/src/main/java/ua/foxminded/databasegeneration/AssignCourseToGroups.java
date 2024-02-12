package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;

public final class AssignCourseToGroups {

	public static List<Groups> assign(List<Groups> groupsList, List<Course> coursesList) {
		groupsList.forEach(el->{
			List<Course> coursesInGroup = new ArrayList<>();
			while (coursesInGroup.size() < 4) {
				Random random = new Random();
				int indexCourse = random.nextInt(coursesList.size());
				Course course = coursesList.get(indexCourse);
				if (!coursesInGroup.contains(course))
					coursesInGroup.add(course);
			}
			el.setCourse(coursesInGroup);
		});
		return groupsList;
	}

}
