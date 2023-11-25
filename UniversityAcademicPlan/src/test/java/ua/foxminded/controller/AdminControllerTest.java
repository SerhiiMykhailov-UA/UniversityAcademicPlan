package ua.foxminded.controller;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
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
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.entity.UserType;
import ua.foxminded.service.UsersService;
import ua.foxminded.util.UsersDtoValidator;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UsersService service;
	@MockBean
	private UsersDtoValidator usersDtoValidator;
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private BindingResult bindingResult;

	@Test
	@WithMockUser(value="admin")
	void testRegistrationPage() throws Exception {
		mvc.perform(get("/adminpanel/registration"))
			.andExpect(status().isOk())
			.andExpect(view().name("adminpanel/registration"));
	}

	@Test
	@WithMockUser(value="admin")
	void testPerformRegistration_shouldCreateNewUser() throws Exception {
		UsersDto dto = new UsersDto("nickname", "password", UserType.ROLE_NEWUSER);

		MockHttpServletRequestBuilder request = post("/adminpanel/registration").with(csrf())
				.content(objectMapper.writeValueAsString(dto));
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
		mvc.perform(MockMvcRequestBuilders.get("/adminpanel/{id}", 1))
			.andExpect(status().is(200))
			.andExpect(view().name("adminpanel/users"))
			.andExpect(model().attribute("usersDto", usersDto));
	}

	@Test
	@WithMockUser("admin")
	void testUpdateUsers_shouldUpdateUserInDatabase() throws Exception {
		UsersDto dto = new UsersDto("nickname", "password", UserType.ROLE_NEWUSER);
		MockHttpServletRequestBuilder request = post("/adminpanel/{id}", 1).with(csrf())
				.content(objectMapper.writeValueAsString(dto));
		mvc.perform(request.secure(true).with(testSecurityContext()))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/showUserPage"));
	}

	@Test
	@WithMockUser("admin")
	void testDeletUser() throws Exception {
		UsersDto usersDto = new UsersDto(1,"nickname", "password", UserType.ROLE_NEWUSER);
		when(service.delete(Mockito.anyLong())).thenReturn(true);
		MockHttpServletRequestBuilder request = post("/adminpanel/delet").with(csrf())
				.content(objectMapper.writeValueAsString(usersDto));
		mvc.perform(request.secure(true).with(testSecurityContext()))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/showUserPage"));
	}

}
