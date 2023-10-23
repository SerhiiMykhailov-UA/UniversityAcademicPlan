package ua.foxminded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.foxminded.universityacademicplan.UniversityAcademicPlan;

@SpringBootApplication
public class BootUniversityAcademicPlanApplication {
	@Autowired
	UniversityAcademicPlan universityAcademicPlan;

	public static void main(String[] args){
		SpringApplication.run(BootUniversityAcademicPlanApplication.class, args);
	}

}
