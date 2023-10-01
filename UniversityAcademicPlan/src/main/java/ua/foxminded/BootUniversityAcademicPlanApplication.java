package ua.foxminded;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.foxminded.universityacademicplan.UniversityAcademicPlan;


@SpringBootApplication
@EnableAutoConfiguration
public class BootUniversityAcademicPlanApplication implements ApplicationRunner{
	@Autowired
	UniversityAcademicPlan universityAcademicPlan;

	public static void main(String[] args){
		SpringApplication.run(BootUniversityAcademicPlanApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		universityAcademicPlan.universityAcademicPlan();

	}

}
