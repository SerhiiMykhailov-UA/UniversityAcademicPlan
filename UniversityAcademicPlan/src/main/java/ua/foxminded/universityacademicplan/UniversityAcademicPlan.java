package ua.foxminded.universityacademicplan;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.UserType;
import ua.foxminded.entity.Users;

@Profile("!test")
@Component
public class UniversityAcademicPlan {
	private static final Logger logger = LogManager.getLogger();
	public static void universityAcademicPlan() {
		Groups g = new Groups();
		List<Course> f = new ArrayList<>();
		Student student = new Student("rftg", "aed", g, f);
		student.setNickName("tttt");
		new Student("swhb", "ehjbe");
		System.out.println(student);
		Users users = new Users("wed", "swedf");
		users.equals(users);
		logger.info("srferfsaf");
	}

}
