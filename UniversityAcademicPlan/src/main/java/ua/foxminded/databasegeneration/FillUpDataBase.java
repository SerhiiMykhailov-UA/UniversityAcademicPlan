package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.dto.CourseDto;
import ua.foxminded.dto.GroupsDto;
import ua.foxminded.dto.LectureDto;
import ua.foxminded.dto.LocationDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.entity.Course;
import ua.foxminded.entity.Groups;
import ua.foxminded.entity.Lecture;
import ua.foxminded.entity.Student;
import ua.foxminded.entity.Teacher;
import ua.foxminded.entity.UserType;
import ua.foxminded.entity.Location;
import ua.foxminded.exceptions.CourseException;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.exceptions.LectureException;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.exceptions.StudentException;
import ua.foxminded.repository.CourseJPARepository;
import ua.foxminded.repository.GroupsJPARepository;
import ua.foxminded.repository.LectureJPARepository;
import ua.foxminded.repository.LocationJPARepository;
import ua.foxminded.repository.StudentJPARepository;
import ua.foxminded.repository.TeacherJPARepository;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.GroupsService;
import ua.foxminded.service.LectureService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;

//@Profile("!test")
@Component
public class FillUpDataBase {

//	private final CourseService courseService;
//	private final StudentService studentService;
//	private final GroupsService groupService;
//	private final TeacherService teacherService;
//	private final LocationService locationService;
//	private final LectureService lectureService;
	
	private final CourseJPARepository courseJPARepository;
	private final StudentJPARepository studentJPARepository;
	private final GroupsJPARepository groupsJPARepository;
	private final TeacherJPARepository teacherJPARepository;
	private final LocationJPARepository locationJPARepository;
	private final LectureJPARepository lectureJPARepository;
	private final PasswordEncoder encoder;
	
	
	private final Logger logger = LogManager.getLogger();

	private static final List<String> groupsListStart = GroupGenerate.groupGenerate();
	private static final List<Course> coursesListStart = CoursesListGenerate.coursesList();
	private static final List<Teacher> teachersStartList = TeacherGenerate.teacherGenerate();
	private static final List<Student> studentsListStart = StudentsRandomNameGenerate.studentsRandomNameGenerate();
	
	public FillUpDataBase( TeacherJPARepository teacherJPARepository,
			StudentJPARepository studentJPARepository,
			LocationJPARepository locationJPARepository, 
			LectureJPARepository lectureJPARepository,
			GroupsJPARepository groupsJPARepository,
			CourseJPARepository courseJPARepository,
			PasswordEncoder encoder) {

		this.courseJPARepository = courseJPARepository;
		this.studentJPARepository = studentJPARepository;
		this.groupsJPARepository = groupsJPARepository;
		this.teacherJPARepository = teacherJPARepository;
		this.locationJPARepository = locationJPARepository;
		this.lectureJPARepository = lectureJPARepository;
		this.encoder = encoder;
	}

	
	public void dataBaseConfiguration(){
		List<Location> locations = locationJPARepository.findAll();

		// Fill-up courses Data table
		long locationIndex = 1;
		for (Course c:coursesListStart) {
			if (locationIndex > locations.size()) {
				locationIndex = 1;
			};
			Location location = new Location();
			try {
				location = locationJPARepository.findById(locationIndex).orElseThrow(()->new LocationException(""));
				
			} catch (LocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setLocation(location);
			courseJPARepository.saveAndFlush(c);
			locationIndex++;
		}
		
		// Add lectures for each course
		List<Course> courses = courseJPARepository.findAll();
		for(Course c : courses) {
			for(int i=0; i<10; i++) {
				Lecture lecture = lectureJPARepository.saveAndFlush(new Lecture("Lecture-" + (i+1) + ":_" + c.getName()));
				lecture.setCourse(c);
				lectureJPARepository.saveAndFlush(lecture);

			}
		}
		
		// Fill-up group Data table
		for(String s:groupsListStart) {
			groupsJPARepository.saveAndFlush(new Groups(s));
		}
		 
		
		// Fill-up teacher Data table
		int i = 1;
		for (Teacher teacher:teachersStartList) {

			teacher.setNickName("teacher" + i);
			i++;
			teacher.setPassword(encoder.encode("111"));
			teacher.setUserType(UserType.ROLE_TEACHER);
			teacherJPARepository.saveAndFlush(teacher);
		}
		
		
		// Fill-up students Data table
		i = 1;
		for (Student student:studentsListStart) {

			student.setNickName("student" + i);
			i++;
			student.setPassword(encoder.encode("111"));
			student.setUserType(UserType.ROLE_STUDENT);
			studentJPARepository.saveAndFlush(student);
		}
		
		// Assign students to group
		List<Student> studentList = studentJPARepository.findAll();
		List<Groups> groups = groupsJPARepository.findAll();
		List<Student> studentAssignToGroup = assignStudentsToGroups(groups, studentList);
		
		for(Student s: studentAssignToGroup) {
			try {
				Groups studentIngroup = groupsJPARepository.findById(s.getGroups().getId()).orElseThrow(()-> new GroupsException(""));
				s.setGroups(studentIngroup);
			} catch (GroupsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			studentJPARepository.saveAndFlush(s);
		}
		
		// Assign courses to groups
		List<Groups> groupsWithAssignCourse = assignCourseToGroups(groups, courses);
		
		for(Groups g : groupsWithAssignCourse) {
			Groups group = new Groups();
			try {
				group = groupsJPARepository.findById(g.getId()).orElseThrow(()-> new GroupsException(""));
				List<Course> coursesInGroup = group.getCourse();
				for (Course c : g.getCourse()) {
					Course course = courseJPARepository.findById(c.getId()).orElseThrow(()-> new CourseException(""));
					coursesInGroup.add(course);
				}
				group.setCourse(coursesInGroup);
			} catch (GroupsException | CourseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			groupsJPARepository.saveAndFlush(group);
		}
		
		// Assign teachers to courses
		i = 0;
		List<Course> courses22 = courseJPARepository.findAll();
		List<Teacher> teachers = teacherJPARepository.findAll();
		for(Course c : courses22) {
			if (i == 9) {
				i = 0;
			}
			c.setTeacher(new ArrayList<>(Arrays.asList(teachers.get(i))));
			logger.info("teacher list = {}", c.getTeacher());
			courseJPARepository.saveAndFlush(c);
			i++;
		}
	}
	
	public static List<Student> assignStudentsToGroups(List<Groups> groupsList, List<Student> studentList) {
//	    List<Student> studentsList = new ArrayList<>();
		List<Student> studentInGroupList = new ArrayList<>();
		for (Student el: studentList) {
			Random random = new Random();
			int i = random.nextInt(groupsList.size());
			Groups group = groupsList.get(i);
			if( group.getStudent() != null) {
				studentInGroupList = group.getStudent();
			}
			if (studentInGroupList.size() < 30) {
				studentInGroupList.add(el);
				group.setStudent(studentInGroupList);
				groupsList.set(i, group);
				el.setGroups(group); 
			}
		}
		return studentList;
	}
	
	public List<Groups> assignCourseToGroups(List<Groups> groupsList, List<Course> coursesList) {
		for (int i =0; i<groupsList.size(); i++) {
			Groups group = groupsList.get(i);
			List<Course> coursesInGroup = new ArrayList<>();
			while (coursesInGroup.size() < 3) {
				Random random = new Random();
				int indexCourse = random.nextInt(coursesList.size());
				Course course = coursesList.get(indexCourse);
				if (!coursesInGroup.contains(course))
					coursesInGroup.add(course);
			}
			group.setCourse(coursesInGroup);
			groupsList.set(i, group);

		}
		return groupsList;
	}
}
