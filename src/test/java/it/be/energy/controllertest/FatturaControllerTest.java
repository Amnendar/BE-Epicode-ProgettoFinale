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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.be.energy.repository.FatturaRepository;
import it.be.energy.service.FatturaService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FatturaControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@Mock
	FatturaRepository fatturarepo;

	@Mock
	FatturaService fatturaservice;
	
	
	/*
	 * Test Get Fattura
	 */
	@Test
	@WithMockUser
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/mostra")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	@Test
	@WithAnonymousUser
	public void testGetAllAnonimo() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/mostra")).andDo(print()).andExpect(status().isUnauthorized());	
	}

	
	/*
	 * Test Cancellazione Fattura come Admin
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/fattura/cancella/4")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	
	/*
	 * Test Cancellazione Fattura come User
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testDeleteByIdAsUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/fattura/cancella/2")).andDo(print()).andExpect(status().isForbidden());	
	}
	
	/*
	 * Test Find By Id
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testfindById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/cerca/2")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 *Test Cerca per Ragione Sociale
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testfindByRagioneSociale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/cercaragionesociale/Tabolacci")).andDo(print()).andExpect(status().isAccepted());	
	}

	/*
	 * Test Cerca per stato
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testfindByStato() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/fattura/cercaperstato/5")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Cerca per anno
	 */
	@Test
	@WithMockUser
	public void testFindByAnno() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/fattura/cercaperanno/2021")).andDo(print()).andExpect(status().isAccepted());
	}
	
	/*
	 * Test Per range di importi
	 */
	@Test
	@WithMockUser
	public void testFindByImporti() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/fattura/cercaperimporti/100/20000")).andDo(print()).andExpect(status().isAccepted());
	}
	
	/*
	 * Test Inserimento Fattura
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPostFattura() throws Exception {
		String body = "{\r\n"
				+ "  \"anno\": 2022,\r\n"
				+ "  \"data\": \"2022-03-15\",\r\n"
				+ "  \"importo\": 3000,\r\n"
				+ "  \"stato\": {\r\n"
				+ "    \"id\": 5\r\n"
				+ "  },\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "  },\r\n"
				+ "    \"nfattura\": 2211\r\n"
				+ "  }";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/fattura/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}
	
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostFatturaUser() throws Exception {
		String body = "{\r\n"
				+ "  \"anno\": 2022,\r\n"
				+ "  \"data\": \"2022-03-15\",\r\n"
				+ "  \"importo\": 3000,\r\n"
				+ "  \"stato\": {\r\n"
				+ "    \"id\": 5\r\n"
				+ "  },\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "  },\r\n"
				+ "    \"nfattura\": 2211\r\n"
				+ "  }";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/fattura/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	
	/*
	 * Test Inserimento Fattura
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPutFattura() throws Exception {
		String body = "{\r\n"
				+ "  \"anno\": 2022,\r\n"
				+ "  \"data\": \"2022-03-15\",\r\n"
				+ "  \"importo\": 3000,\r\n"
				+ "  \"stato\": {\r\n"
				+ "    \"id\": 5\r\n"
				+ "  },\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "  },\r\n"
				+ "    \"nfattura\": 2211\r\n"
				+ "  }";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/fattura/modifica/2")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}
	/*
	 * Test Update Fattura
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutFatturaUser() throws Exception {
		String body = "{\r\n"
				+ "  \"anno\": 2022,\r\n"
				+ "  \"data\": \"2022-03-15\",\r\n"
				+ "  \"importo\": 3000,\r\n"
				+ "  \"stato\": {\r\n"
				+ "    \"id\": 5\r\n"
				+ "  },\r\n"
				+ "  \"cliente\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "  },\r\n"
				+ "    \"nfattura\": 2211\r\n"
				+ "  }";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/fattura/modifica/2")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	

}
