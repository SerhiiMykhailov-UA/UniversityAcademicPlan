package ua.foxminded;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ua.foxminded.entity.Student;

@SpringBootApplication
public class UniversityAcademicPlanApplication implements ApplicationRunner{

	public static void main(String[] args){
		SpringApplication.run(UniversityAcademicPlanApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Student student = new Student();
		
	}

}
