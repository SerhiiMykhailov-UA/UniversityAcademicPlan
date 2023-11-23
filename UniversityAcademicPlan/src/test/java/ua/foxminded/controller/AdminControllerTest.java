package ua.foxminded.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

	@WithMockUser(value="admin")
	@Test
	void testRegistrationPage() throws Exception {
		mvc.perform(get("/adminpanel/registration"))
		.andExpect(status().isOk())
		.andExpect(view().name("adminpanel/registration"));
	}

	@WithMockUser(value="admin")
	@Test
	void testPerformRegistration() throws Exception {
		
		mvc.perform(post("/adminpanel/registration")).andExpect(status().isOk());
	}

	@Test
	void testUpdateUsersLongModel() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateUsersLongUsersDto() {
		fail("Not yet implemented");
	}

	@Test
	void testDeletUser() {
		fail("Not yet implemented");
	}

}
