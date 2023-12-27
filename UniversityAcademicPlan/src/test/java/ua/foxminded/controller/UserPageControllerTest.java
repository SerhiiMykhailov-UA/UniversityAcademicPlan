package ua.foxminded.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.StudentDto;
import ua.foxminded.dto.TeacherDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;

@WebMvcTest(value = UserPageController.class)
class UserPageControllerTest {
	
	@Autowired
	private MockMvc mvc;
	@MockBean
	private UsersService usersService;
	@MockBean
	private AdminService adminService;
	@MockBean
	private StudentService studentService;
	@MockBean
	private CourseService courseService;
	@MockBean
	private TeacherService teacherService;
	
	@Test
	@WithMockUser(value="admin")
	void testStart() throws Exception {
		mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}

	@Test
	@WithMockUser(username = "admin")
	void testShowUserInfo() throws Exception {
		UsersDto dto = new UsersDto("admin", "password", UserType.ROLE_ADMIN);
		AdminDto adminDto = new AdminDto("fn1", "ln1");
		List<UsersDto> dtoList = new ArrayList<>(Arrays.asList(new UsersDto("nickname", "password", UserType.ROLE_ADMIN), new UsersDto("nickname2", "password", UserType.ROLE_STUDENT)));

		when(usersService.getByNickName(Mockito.anyString())).thenReturn(dto);
		when(adminService.get(Mockito.anyLong())).thenReturn(adminDto);
		when(usersService.getAll()).thenReturn(dtoList);
		mvc.perform(get("/showUserPage"))
			.andExpect(status().isOk())
			.andExpect(view().name("showuserpage/admin"))
			.andExpect(model().attribute("usersInfo", adminDto));
		
		dto = new UsersDto("teacher", "password", UserType.ROLE_TEACHER);
		TeacherDto teacherDto = new TeacherDto("teacher_fn1", "teacher_ln1");
		when(usersService.getByNickName(Mockito.anyString())).thenReturn(dto);
		when(teacherService.get(Mockito.anyLong())).thenReturn(teacherDto);
		mvc.perform(get("/showUserPage"))
			.andExpect(status().isOk())
			.andExpect(view().name("showuserpage/teacher"))
			.andExpect(model().attribute("usersInfo", teacherDto));
		
		dto = new UsersDto("student", "password", UserType.ROLE_STUDENT);
		StudentDto studentDto = new StudentDto("student_fn1", "student_ln1");
		when(usersService.getByNickName(Mockito.anyString())).thenReturn(dto);
		when(studentService.get(Mockito.anyLong())).thenReturn(studentDto);
		mvc.perform(get("/showUserPage"))
			.andExpect(status().isOk())
			.andExpect(view().name("showuserpage/student"))
			.andExpect(model().attribute("usersInfo", studentDto));
	}

}
