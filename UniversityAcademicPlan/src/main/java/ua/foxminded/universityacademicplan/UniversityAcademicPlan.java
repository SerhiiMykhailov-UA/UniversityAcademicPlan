package ua.foxminded.universityacademicplan;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class UniversityAcademicPlan {

	public static void universityAcademicPlan() {
;
	}

}
