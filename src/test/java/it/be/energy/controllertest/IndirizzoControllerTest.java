package it.be.energy.controllertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.be.energy.repository.IndirizzoRepository;
import it.be.energy.service.IndirizzoService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IndirizzoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	IndirizzoRepository indirizzorepo;

	@Mock
	IndirizzoService indirizzoservice;
	
	
	/*
	 * Test Get Indirizzo
	 */
	@Test
	@WithMockUser
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/indirizzo/mostra")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Get Indirizzo by Id
	 */
	@Test
	@WithMockUser
	public void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/indirizzo/cerca/1")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Delete by Id
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/indirizzo/cancella/4")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Delete by Id
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testDeleteByIdAsUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/indirizzo/cancella/4")).andDo(print()).andExpect(status().isForbidden());	
	}
	
	/*
	 * Test Inserimento Indirizzo
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPostIndirizzo() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Via del Melograno\",\r\n"
				+ "  \"civico\": \"2\",\r\n"
				+ "  \"localita\": \"Bussoleno\",\r\n"
				+ "  \"cap\": \"01344\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 43\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/indirizzo/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostFatturaUser() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Via del Melograno\",\r\n"
				+ "  \"civico\": \"2\",\r\n"
				+ "  \"localita\": \"Bussoleno\",\r\n"
				+ "  \"cap\": \"01344\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 43\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/indirizzo/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	
	/*
	 * Test Aggiornamento Indirizzo
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutFattura() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Via del Melograno\",\r\n"
				+ "  \"civico\": \"2\",\r\n"
				+ "  \"localita\": \"Bussoleno\",\r\n"
				+ "  \"cap\": \"01344\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 43\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/indirizzo/modifica/2")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}

	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutFatturaUser() throws Exception {
		String body = "{\r\n"
				+ "  \"via\": \"Via del Melograno\",\r\n"
				+ "  \"civico\": \"2\",\r\n"
				+ "  \"localita\": \"Bussoleno\",\r\n"
				+ "  \"cap\": \"01344\",\r\n"
				+ "  \"comune\": {\r\n"
				+ "    \"id\": 43\r\n"
				+ "  }\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/indirizzo/modifica/2")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	
}
