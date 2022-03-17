package it.be.energy.controllertest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import it.be.energy.repository.ProvinciaRepository;
import it.be.energy.service.ProvinciaService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProvinciaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	ProvinciaRepository provinciarepo;

	@Mock
	ProvinciaService provinciaservice;

	/*
	 * Test Get Provincia
	 */
	@Test
	@WithMockUser
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/mostra")).andDo(print())
				.andExpect(status().isAccepted());
	}

	@Test
	@WithAnonymousUser
	public void testGetAllAnonimo() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/mostra")).andDo(print())
				.andExpect(status().isUnauthorized());
	}

	/*
	 * Test Get Provincia By ID
	 */
	@Test
	@WithMockUser
	public void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/cerca/1")).andDo(print())
				.andExpect(status().isAccepted());
	}

	/*
	 * Test Find Provincia By Name
	 */
	@Test
	@WithMockUser
	public void testFindByNome() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/provincia/cercapernome/Roma")).andDo(print()).andExpect(status().isAccepted());	
	}
	
}
