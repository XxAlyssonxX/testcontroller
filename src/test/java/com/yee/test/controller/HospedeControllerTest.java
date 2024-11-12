package com.yee.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yee.entities.Hospede;
import com.yee.repository.HospedeRepository;
import com.yee.services.HospedeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class HospedeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private HospedeRepository hospedeRepository;
    
    @Autowired
    private HospedeService hospedeService;

    @BeforeEach
    void setUp() {
        hospedeRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste de criação de Hóspede")
    void testCriarHospede() {
        Hospede hospede = new Hospede(null, "Julia Maria", "julia@gmail.com",
        														"(00)0000-0000");

        ResponseEntity<Hospede> response = restTemplate.postForEntity("/api/hospedes", 
        													  hospede, Hospede.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Julia Maria", response.getBody().getName());
    }


    @Test
    @DisplayName("Teste de listagem de todos os Hóspedes")
    void testListarTodosHospedes() {
        // Arrange
        Hospede hospede1 = new Hospede(null, "Julia Maria", "julia@gmail.com", "(00)0000-0000");
        Hospede hospede2 = new Hospede(null, "Julio Fernando", "julio@gmail.com", "(00)0000-0000");

        hospedeService.salvarHospede(hospede1);
        hospedeService.salvarHospede(hospede2);

        // Act
        ResponseEntity<Hospede[]> response = restTemplate.getForEntity("/api/hospedes", Hospede[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "A resposta deveria ser 200 OK.");
        assertNotNull(response.getBody(), "O corpo da resposta não deveria ser nulo.");
        assertEquals(2, response.getBody().length, "A quantidade de hóspedes retornada deveria ser 2.");
    }


    @Test
    @DisplayName("Teste de buscar Hóspede por ID")
    void testBuscarHospedePorId() {
    	Hospede hospede = new Hospede(null, "Julia Maria", "julia@gmail.com", "(00)0000-0000");

    	
        Hospede hospedeSalvo = hospedeRepository.save(hospede);

        ResponseEntity<Hospede> response = restTemplate.getForEntity("/api/hospedes/" + 
        																	hospedeSalvo.getId()
        																	, Hospede.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Julia Maria", response.getBody().getName());
    }

    @Test
    @DisplayName("Teste de atualização de Hóspede")
    void testAtualizarHospede() {
        Hospede hospedeSalvo = hospedeRepository.save(
        										new Hospede(null, "Julia Maria", "julia@gmail.com",
        																			"(00)0000-0000"));
        Hospede hospedeAtualizado = new Hospede(hospedeSalvo.getId(), "Julia Silva", 
        															"julia.silva@gmail.com",
        															"(11)1111-1111");

        HttpEntity<Hospede> requestUpdate = new HttpEntity<>(hospedeAtualizado);
        ResponseEntity<Hospede> response = restTemplate.exchange("/api/hospedes/"
        									+ hospedeSalvo.getId(), HttpMethod.PUT, 
        												requestUpdate, Hospede.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Julia Silva", response.getBody().getName());
        assertEquals("julia.silva@gmail.com", response.getBody().getEmail());
    }
    
    @Test
    @DisplayName("Teste DELETAR Hóspede")
    void testDeletarHospede() {
        // Arrange: Cria e salva um hóspede
        Hospede hospede = new Hospede(null, "Julia Maria", "julia@gmail.com", "(00)0000-0000");
        Hospede hospedeSalvo = hospedeService.salvarHospede(hospede);
        
        // Act: Tenta deletar o hóspede
        ResponseEntity<Void> response = restTemplate.exchange("/api/hospedes/" + hospedeSalvo.getId(), 
        															HttpMethod.DELETE, null, Void.class);

        // Assert: Verifica se o status da resposta é 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), 
        											"A resposta deveria ser 204 No Content.");

        // Verifica se o hóspede realmente foi deletado
        ResponseEntity<Hospede> checkDeleted = restTemplate.getForEntity("/api/hospedes/" +
        															hospedeSalvo.getId(), Hospede.class);
        assertEquals(HttpStatus.NOT_FOUND, checkDeleted.getStatusCode(), 
        									"Após O DELETE o hóspede não deveria ser encontrado.");
     }

}
