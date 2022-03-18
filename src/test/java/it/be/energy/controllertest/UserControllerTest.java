package it.be.energy.controllertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	/*
	 * Test Find User By Id
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testfindById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/user/4")).andDo(print()).andExpect(status().isOk());	
	}
	
	
}
