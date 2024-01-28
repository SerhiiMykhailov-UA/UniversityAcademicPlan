package ua.foxminded.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.dto.AdminDto;
import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.service.AdminService;
import ua.foxminded.service.CourseService;
import ua.foxminded.service.LocationService;
import ua.foxminded.service.StudentService;
import ua.foxminded.service.StuffService;
import ua.foxminded.service.TeacherService;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.CourseDtoValidator;
import ua.foxminded.util.UsersDtoValidator;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsersService service;
	@MockBean
	private UsersDtoValidator usersDtoValidator;
	@MockBean
	private CourseDtoValidator courseDtoValidator;
	@MockBean
	private AdminService adminService;
	@MockBean
	private StudentService studentService;
	@MockBean
	private TeacherService teacherService;
	@MockBean
	private CourseService courseService;
	@MockBean
	private LocationService locationService;
	@MockBean
	private StuffService stuffService;
	
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@WithMockUser(value="admin")
	void testUserRegistrationPage_Get() throws Exception {
		mvc.perform(get("/admin/user/registration"))
			.andExpect(status().isOk())
			.andExpect(view().name("registration/user_registration"));
	}

	@Test
	@WithMockUser(value="admin")
	void testPerformUserRegistration_Post() throws Exception {
		UsersDto usersdto = new UsersDto("nickname", "password", UserType.ROLE_ADMIN);
		AdminDto adminDto = new AdminDto("----", "----");
		adminDto.setId(usersdto.getId());
		adminDto.setNickName(usersdto.getNickName());
		adminDto.setPassword(usersdto.getPassword());
		adminDto.setUserType(usersdto.getUserType());
		when(adminService.add(Mockito.any())).thenReturn(adminDto);
		MockHttpServletRequestBuilder request = post("/admin/user/registration").with(csrf())
				.flashAttr("users", usersdto);
		mvc.perform(request.secure(true).with(testSecurityContext()))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/showUserPage"))
			.andExpect(view().name("redirect:/showUserPage"));
	}

	@Test
	@WithMockUser("admin")
	void testUpdateUsers_shouldShowUsewrInfoToUpdate() throws Exception {
		UsersDto usersDto = new UsersDto(1,"nickname", "password", UserType.ROLE_NEWUSER);

		when(service.get(Mockito.anyLong())).thenReturn(usersDto);
		mvc.perform(MockMvcRequestBuilders.get("/admin/user/{id}", 1))
			.andExpect(status().is(200))
			.andExpect(view().name("users"))
			.andExpect(model().attribute("usersDto", usersDto));
	}

	@Test
	@WithMockUser("admin")
	void testUpdateUsers_shouldUpdateUserInDatabase() throws Exception {
		UsersDto dto = new UsersDto("nickname", "password", UserType.ROLE_ADMIN);
		MockHttpServletRequestBuilder request = post("/admin/user/{id}", 1).with(csrf())
				.content(objectMapper.writeValueAsString(dto));
		mvc.perform(request.secure(true).with(testSecurityContext()))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/showUserPage"));
	}

	@Test
	@WithMockUser("admin")
	void testDeletUser() throws Exception {
		UsersDto usersDto = new UsersDto(1,"nickname", "password", UserType.ROLE_ADMIN);
		when(service.delete(Mockito.anyLong())).thenReturn(true);
		MockHttpServletRequestBuilder request = post("/admin/user/delet").with(csrf())
				.content(objectMapper.writeValueAsString(usersDto));
		mvc.perform(request.secure(true).with(testSecurityContext()))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/showUserPage"));
	}

}
