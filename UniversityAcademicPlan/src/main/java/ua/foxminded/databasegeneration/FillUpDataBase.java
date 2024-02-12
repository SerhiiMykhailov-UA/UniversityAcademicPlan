package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Lecture;
import ua.foxminded.entity.Location;
import ua.foxminded.entity.Schedule;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.entity.UserType;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.repository.CourseJPARepository;
import ua.foxminded.repository.GroupsJPARepository;
import ua.foxminded.repository.LectureJPARepository;
import ua.foxminded.repository.LocationJPARepository;
import ua.foxminded.repository.ScheduleJPARepository;
import ua.foxminded.repository.StudentJPARepository;
import ua.foxminded.repository.TeacherJPARepository;

@Component
@Transactional(readOnly = false)
public class FillUpDataBase {

	private final CourseJPARepository courseJPARepository;
	private final StudentJPARepository studentJPARepository;
	private final GroupsJPARepository groupsJPARepository;
	private final TeacherJPARepository teacherJPARepository;
	private final LocationJPARepository locationJPARepository;
	private final LectureJPARepository lectureJPARepository;
	private final PasswordEncoder encoder;
	private final ScheduleJPARepository scheduleJPARepository;

	private final Logger logger = LogManager.getLogger();

	private static final List<String> groupsListStart = GroupGenerate.groupGenerate();
	private static final List<Course> coursesListStart = CoursesListGenerate.coursesList();
	private static final List<Teacher> teachersStartList = TeacherGenerate.teacherGenerate();
	private static final List<Student> studentsListStart = StudentsRandomNameGenerate.studentsRandomNameGenerate();

	public FillUpDataBase(TeacherJPARepository teacherJPARepository, StudentJPARepository studentJPARepository,
			LocationJPARepository locationJPARepository, LectureJPARepository lectureJPARepository,
			GroupsJPARepository groupsJPARepository, CourseJPARepository courseJPARepository, PasswordEncoder encoder,
			ScheduleJPARepository scheduleJPARepository) {

		this.courseJPARepository = courseJPARepository;
		this.studentJPARepository = studentJPARepository;
		this.groupsJPARepository = groupsJPARepository;
		this.teacherJPARepository = teacherJPARepository;
		this.locationJPARepository = locationJPARepository;
		this.lectureJPARepository = lectureJPARepository;
		this.encoder = encoder;
		this.scheduleJPARepository = scheduleJPARepository;
	}

	public boolean isDataBaseEmpty () {
		if (courseJPARepository.findAll().isEmpty() && studentJPARepository.findAll().isEmpty()
				&& teacherJPARepository.findAll().isEmpty() && groupsJPARepository.findAll().isEmpty()) {
			return true;
		}else {
		return false;
		}
	}
	
	public void fillUpCourseDataTable() {
			List<Location> locations = locationJPARepository.findAll();
			long locationIndex = 1;
			for (Course c : coursesListStart) {
				if (locationIndex > locations.size()) {
					locationIndex = 1;
				}
				;
				Location location = new Location();
				try {
					location = locationJPARepository.findById(locationIndex)
							.orElseThrow(() -> new LocationException(""));

				} catch (LocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.setLocation(location);
				courseJPARepository.saveAndFlush(c);
				locationIndex++;
			}
	}

	public void fillUpLectureToCourse() {
		List<Course> courses = courseJPARepository.findAll();
		for (Course c : courses) {
			for (int i = 0; i < 10; i++) {
				Lecture lecture = lectureJPARepository
						.saveAndFlush(new Lecture("Lecture-" + (i + 1) + ":_" + c.getName()));
				lecture.setCourse(c);
				lectureJPARepository.saveAndFlush(lecture);

			}
		}
	}

	public void fillUpGroupDataTable() {
		for (String s : groupsListStart) {
			groupsJPARepository.saveAndFlush(new Groups(s));
		}
	}

	public void fillUpTeacherDataTable() {
		int i = 1;
		for (Teacher teacher : teachersStartList) {

			teacher.setNickName("teacher" + i);
			i++;
			teacher.setPassword(encoder.encode("111"));
			teacher.setUserType(UserType.ROLE_TEACHER);
			teacherJPARepository.saveAndFlush(teacher);
		}
	}

	public void fillUpStudentDataTable() {
		int i = 1;
		for (Student student : studentsListStart) {

			student.setNickName("student" + i);
			i++;
			student.setPassword(encoder.encode("111"));
			student.setUserType(UserType.ROLE_STUDENT);
			studentJPARepository.saveAndFlush(student);
		}
	}

	public void assignStudentsToGroup() {
		List<Student> studentList = studentJPARepository.findAll();
		List<Groups> groups = groupsJPARepository.findAll();
		List<Student> studentAssignToGroup = AssignStudentsToGroups.assign(groups, studentList);

		for (Student s : studentAssignToGroup) {
			studentJPARepository.saveAndFlush(s);
		}
	}

	public void assignCoursesToGroup() {
		List<Course> courses = courseJPARepository.findAll();
		List<Groups> groups = groupsJPARepository.findAll();
		List<Groups> groupsWithAssignCourse = AssignCourseToGroups.assign(groups, courses);

		for (Groups g : groupsWithAssignCourse) {
			groupsJPARepository.saveAndFlush(g);
		}
	}

	public void assignTeachersToCourse() {
		List<Course> courses = courseJPARepository.findAll();
		List<Teacher> teachers = teacherJPARepository.findAll();
		int i = 0;
		for (Course c : courses) {
			if (i == teachers.size() - 1) {
				i = 0;
			}
			c.setTeacher(new ArrayList<>(Arrays.asList(teachers.get(i))));
			logger.info("teacher list = {}", c.getTeacher());
			courseJPARepository.saveAndFlush(c);
			i++;
		}
	}

	public void assignScheduleListToCourse() {
		List<Course> courses = courseJPARepository.findAll();
		List<Schedule> schedules = scheduleJPARepository.findAll();
		List<Schedule> scheduleWithCourses = AssignScheduleToCourse.assign(schedules, courses);
		scheduleWithCourses.forEach(el -> {
			scheduleJPARepository.saveAndFlush(el);
//			System.out.println(el.getCourse());

		});

	}
}
