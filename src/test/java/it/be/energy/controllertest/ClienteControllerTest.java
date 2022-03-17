package it.be.energy.controllertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import it.be.energy.repository.ClienteRepository;
import it.be.energy.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	ClienteRepository clienteRepo;

	@Mock
	ClienteService clienteService;
	
	/*
	 * Test Get Client
	 */
	@Test
	@WithMockUser
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/mostra")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Inserimento Cliente
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {
		String body = "{\r\n"
				+ "   \r\n"
				+ "  \"ragioneSociale\": \"23 SAS\",\r\n"
				+ "  \"partitaIva\": \"D32334343\",\r\n"
				+ "  \"tipoCliente\": \"SAS\",\r\n"
				+ "  \"email\": \"strin23g@string.it\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-16\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-16\",\r\n"
				+ "  \"fatturatoAnnuale\": 200000,\r\n"
				+ "  \"pec\": \"string@pec.it\",\r\n"
				+ "  \"telefono\": \"323233434\",\r\n"
				+ "  \"emailContatto\": \"string@string.it\",\r\n"
				+ "  \"nomeContatto\": \"Holly\",\r\n"
				+ "  \"cognomeContatto\": \"String\",\r\n"
				+ "  \"telefonoContatto\": \"33434534343\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    }\r\n"
				+ "  ,\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "    }\r\n"
				+ "  \r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}
	
	
	/*
	 * Test Update Cliente
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		String body = "{\r\n"
				+ "   \r\n"
				+ "  \"ragioneSociale\": \"23 SAS\",\r\n"
				+ "  \"partitaIva\": \"D32334343\",\r\n"
				+ "  \"tipoCliente\": \"SAS\",\r\n"
				+ "  \"email\": \"strin23g@string.it\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-16\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-16\",\r\n"
				+ "  \"fatturatoAnnuale\": 200000,\r\n"
				+ "  \"pec\": \"string@pec.it\",\r\n"
				+ "  \"telefono\": \"323233434\",\r\n"
				+ "  \"emailContatto\": \"string@string.it\",\r\n"
				+ "  \"nomeContatto\": \"Holly\",\r\n"
				+ "  \"cognomeContatto\": \"String\",\r\n"
				+ "  \"telefonoContatto\": \"33434534343\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    }\r\n"
				+ "  ,\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "    },\r\n"
				+ "  \"fatture\":[]\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/cliente/modifica/4")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isAccepted()).andReturn();
	}
	
	/*
	 * Test Cancellazione Cliente come Admin
	 */
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/cliente/cancella/4")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	
	/*
	 * Test Cancellazione Cliente come User
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testDeleteByIdAsUser() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/cliente/cancella/4")).andDo(print()).andExpect(status().isForbidden());	
	}
	
	
	/*
	 * Test Find By Id
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testfindById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/cerca/1")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Find By Ragione Sociale
	 */
	@Test
	@WithMockUser
	public void testMostraPerRagioneSociale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080//cliente/mostrapernome")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Order By Fatturato
	 */
	@Test
	@WithMockUser
	public void findAllByOrderByFatturatoAnnuale() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/cliente/mostraperfatturato")).andDo(print()).andExpect(status().isAccepted());	
	}
	
	/*
	 * Test Cerca By Ragione Sociale
	 */
	@Test
	@WithMockUser
	public void findByRagioneSociale() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperragionesociale/Tab")).andDo(print()).andExpect(status().isAccepted());
	}
	
	@Test
	@WithMockUser
	public void findByRagioneSocialeNotPresent() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperragionesociale/Rag")).andDo(print()).andExpect(status().isNoContent());
	}
	
	/*
	 * Test Modifica Sede Legale
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testModificaSedeLegale() throws Exception{
		this.mockMvc.perform(put("http://localhost:8080/cliente/cambiasedelegale/1/2")).andDo(print()).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testModificaSedeLegaleAsAdmin() throws Exception{
		this.mockMvc.perform(put("http://localhost:8080/cliente/cambiasedelegale/1/2")).andDo(print()).andExpect(status().isAccepted());
	}
	
	/*
	 * Test Modifica Sede Legale
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	public void testModificaSedeOperativa() throws Exception{
		this.mockMvc.perform(put("http://localhost:8080/cliente/cambiasedeoperativa/3/4")).andDo(print()).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	public void testModificaSedeOperativaAsAdmin() throws Exception{
		this.mockMvc.perform(put("http://localhost:8080/cliente/cambiasedeoperativa/3/4")).andDo(print()).andExpect(status().isAccepted());
	}
	
	/*
	 * Test Get Data
	 */
//	@Test
//	@WithMockUser
//	public void findByDataUltimoContattoDopo() throws Exception{
//		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperdatacontattodopo/10-02-02")).andDo(print()).andExpect(status().isNoContent());
//	}
	
	@Test
	@WithMockUser
	public void findByFatturatoMaggiore() throws Exception{
		this.mockMvc.perform(get("http://localhost:8080/cliente/trovaperfatturatomaggiore/100000")).andDo(print()).andExpect(status().isAccepted());
	}
	
	/*
	 * Test Inserimento Cliente come USer
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPostNotAuthorized() throws Exception {
		String body = "{\r\n"
				+ "   \r\n"
				+ "  \"ragioneSociale\": \"23 SAS\",\r\n"
				+ "  \"partitaIva\": \"D32334343\",\r\n"
				+ "  \"tipoCliente\": \"SAS\",\r\n"
				+ "  \"email\": \"strin23g@string.it\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-16\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-16\",\r\n"
				+ "  \"fatturatoAnnuale\": 200000,\r\n"
				+ "  \"pec\": \"string@pec.it\",\r\n"
				+ "  \"telefono\": \"323233434\",\r\n"
				+ "  \"emailContatto\": \"string@string.it\",\r\n"
				+ "  \"nomeContatto\": \"Holly\",\r\n"
				+ "  \"cognomeContatto\": \"String\",\r\n"
				+ "  \"telefonoContatto\": \"33434534343\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    }\r\n"
				+ "  ,\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "    }\r\n"
				+ "  \r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/inserisci")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	
	
	/*
	 * Test Update Cliente come User
	 */
	@Test
	@WithMockUser(username = "user", password = "user", roles = "USER")
	final void testPutNotAuthorized() throws Exception {
		String body = "{\r\n"
				+ "   \r\n"
				+ "  \"ragioneSociale\": \"23 SAS\",\r\n"
				+ "  \"partitaIva\": \"D32334343\",\r\n"
				+ "  \"tipoCliente\": \"SAS\",\r\n"
				+ "  \"email\": \"strin23g@string.it\",\r\n"
				+ "  \"dataInserimento\": \"2022-03-16\",\r\n"
				+ "  \"dataUltimoContatto\": \"2022-03-16\",\r\n"
				+ "  \"fatturatoAnnuale\": 200000,\r\n"
				+ "  \"pec\": \"string@pec.it\",\r\n"
				+ "  \"telefono\": \"323233434\",\r\n"
				+ "  \"emailContatto\": \"string@string.it\",\r\n"
				+ "  \"nomeContatto\": \"Holly\",\r\n"
				+ "  \"cognomeContatto\": \"String\",\r\n"
				+ "  \"telefonoContatto\": \"33434534343\",\r\n"
				+ "  \"sedeLegale\": {\r\n"
				+ "    \"id\": 1\r\n"
				+ "    }\r\n"
				+ "  ,\r\n"
				+ "  \"sedeOperativa\": {\r\n"
				+ "    \"id\": 4\r\n"
				+ "    },\r\n"
				+ "  \"fatture\":[]\r\n"
				+ "}";
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/cliente/modifica/4")
	    			.contentType(MediaType.APPLICATION_JSON)
	    			.content(body))
	    			.andExpect(status().isForbidden()).andReturn();
	}
	
}
