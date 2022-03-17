package it.be.energy.controllertest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

	
	@Autowired
	private MockMvc mockMvc;


	
	/*
	 * Test Registrazione User
	 */
	@Test
	@WithMockUser
	final void testSignUp() throws Exception {
		String body = "{\r\n"
				+ "  \"userName\": \"string\",\r\n"
				+ "  \"password\": \"string\",\r\n"
				+ "  \"nome\": \"string\",\r\n"
				+ "  \"cognome\": \"string\",\r\n"
				+ "  \"mail\": \"string\",\r\n"
				+ "  \"roles\": [\r\n"
				+ "    \"USER\"\r\n"
				+ "  ]\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isCreated()).andReturn();
	}
	
	/*
	 * Test Login User
	 */
	@Test
	@WithMockUser
	final void testLogin() throws Exception {
		String body = "{\r\n"
				+ "  \"userName\": \"user\",\r\n"
				+ "  \"password\": \"user\"\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	@WithMockUser
	final void testLoginCredenzialiSbagliate() throws Exception {
		String body = "{\r\n"
				+ "  \"userName\": \"string1\",\r\n"
				+ "  \"password\": \"prova\"\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isUnauthorized()).andReturn();
	}
	
	
}
