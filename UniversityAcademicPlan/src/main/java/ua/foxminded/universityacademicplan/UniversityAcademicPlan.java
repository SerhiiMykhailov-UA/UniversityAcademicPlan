package ua.foxminded.universityacademicplan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.GroupSt;
import ua.foxminded.entity.Student;

@Profile("!test")
@Component
public class UniversityAcademicPlan {

	public void universityAcademicPlan() {
		GroupSt g = new GroupSt();
		List<Course> f = new ArrayList<>();
		Student student = new Student("rftg", "aed", g, f);
		student.setNickName("tttt");
		new Student("swhb", "ehjbe");
		
		System.out.println(student);
	}

}
