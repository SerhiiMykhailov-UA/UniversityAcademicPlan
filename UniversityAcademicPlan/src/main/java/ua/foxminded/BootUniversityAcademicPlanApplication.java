package ua.foxminded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.foxminded.universityacademicplan.UniversityAcademicPlan;

@SpringBootApplication
public class BootUniversityAcademicPlanApplication {

	public static void main(String[] args){
		SpringApplication.run(BootUniversityAcademicPlanApplication.class, args);
		UniversityAcademicPlan.universityAcademicPlan();
	}

}
