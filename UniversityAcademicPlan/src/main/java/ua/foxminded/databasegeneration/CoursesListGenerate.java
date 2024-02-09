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
		courses.add(new Course("Theory_of_mechanisms"));
		courses.add(new Course("Material_resistance"));
		courses.add(new Course("Robotics"));
		courses.add(new Course("Metrology"));
		courses.add(new Course("Aircraft_engine_design"));
		courses.add(new Course("Theory_of_mechanisms_and_machines"));
		courses.add(new Course("Theoretical mechanics"));
		courses.add(new Course("Electrodynamics"));
		courses.add(new Course("Cutting_tool_development"));
		courses.add(new Course("Aerodynamics"));
		courses.add(new Course("Hydro-gasdynamics"));
		return courses;
	}
}
