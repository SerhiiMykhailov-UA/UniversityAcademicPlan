package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;

import ua.foxminded.entity.Course;

public class CoursesListGenerate {
	
	public static List<Course> coursesList (){
		List<Course> courses = new ArrayList<>();
		courses.add(new Course("Mathematics"));
		courses.add(new Course("Physics"));
		courses.add(new Course("Philosophy"));
		courses.add(new Course("Literature"));
		courses.add(new Course("Biology"));
		courses.add(new Course("Chemistry"));
		courses.add(new Course("Hydrodynamics"));
		courses.add(new Course("Theory of mechanisms"));
		courses.add(new Course("Material resistance"));
		courses.add(new Course("Robotics"));
		courses.add(new Course("Metrology"));
		courses.add(new Course("Aircraft engine design"));
		courses.add(new Course("Theory of_mechanisms and machines"));
		courses.add(new Course("Theoretical mechanics"));
		courses.add(new Course("Electrodynamics"));
		courses.add(new Course("Cutting tool development"));
		courses.add(new Course("Aerodynamics"));
		courses.add(new Course("Hydro-gasdynamics"));
		return courses;
	}
}
