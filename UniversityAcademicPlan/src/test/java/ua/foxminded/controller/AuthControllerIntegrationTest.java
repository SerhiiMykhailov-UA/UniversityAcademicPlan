package ua.foxminded.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;


	@Test
	@WithMockUser(value="admin")
	void testLogingPage_returnLoginPage() throws Exception {
		mvc.perform(get("/auth/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("auth/login"));
	}

}
