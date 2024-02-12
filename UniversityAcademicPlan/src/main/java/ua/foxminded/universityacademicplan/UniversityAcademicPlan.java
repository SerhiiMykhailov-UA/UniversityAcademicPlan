package ua.foxminded.universityacademicplan;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ua.foxminded.databasegeneration.FillUpDataBase;

@Profile("!test")
@Component
public class UniversityAcademicPlan {

	private static FillUpDataBase fillUpDataBase;

	public UniversityAcademicPlan(FillUpDataBase fillUpDataBase) {
		UniversityAcademicPlan.fillUpDataBase = fillUpDataBase;
	}

	public static void universityAcademicPlan() {
		if (fillUpDataBase.isDataBaseEmpty()) {
			fillUpDataBase.fillUpCourseDataTable();
			fillUpDataBase.fillUpLectureToCourse();
			fillUpDataBase.fillUpGroupDataTable();
			fillUpDataBase.fillUpTeacherDataTable();
			fillUpDataBase.fillUpStudentDataTable();
			fillUpDataBase.assignStudentsToGroup();
			fillUpDataBase.assignCoursesToGroup();
			fillUpDataBase.assignTeachersToCourse();
			fillUpDataBase.assignScheduleListToCourse();
		}
	}

}
