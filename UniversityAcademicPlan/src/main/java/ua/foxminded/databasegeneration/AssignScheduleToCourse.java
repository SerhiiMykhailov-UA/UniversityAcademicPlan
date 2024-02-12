package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Schedule;

public class AssignScheduleToCourse {
	
	public static List<Schedule> assign(List<Schedule> scheduleList, List<Course> coursesList) {
		coursesList.forEach(el -> {
			List<Schedule> schedulesInCourse = new ArrayList<>();
			while (schedulesInCourse.size() < 3) {
				Random random = new Random();
				int indexCourse = random.nextInt(scheduleList.size());
				Schedule schedule = scheduleList.get(indexCourse);
				if (!schedulesInCourse.contains(schedule)) {
					schedulesInCourse.add(schedule);
					List<Course> coursesInSchedule = schedule.getCourse();
					coursesInSchedule.add(el);
					schedule.setCourse(coursesInSchedule);
				}
			}
			el.setSchedule(schedulesInCourse);
		});
		return scheduleList;
	}
}
