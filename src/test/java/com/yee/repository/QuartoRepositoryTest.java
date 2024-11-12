package com.yee.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.yee.entities.Hospede;
import com.yee.entities.Quarto;

@DataJpaTest
class QuartoRepositoryTest {

	@Autowired
	private QuartoRepository quartoRepository;
	
	@DisplayName("Tenstando Save")
	@Test
	void testSalvarRepositry() {
		
		//given / Arrange
		Quarto quarto1 = new Quarto(null, "2d","15","2");
		
		//when / act
		Quarto saveQuarto = quartoRepository.save(quarto1);
		
		//Then / Assert
		assertNotNull(saveQuarto);
		assertTrue(saveQuarto.getId() > 0);
		
	}
	
	@DisplayName("Tenstando Save")
	@Test
	void testGetAllRepository() {
		
		//given / Arrange
		Quarto quarto1 = new Quarto(null, "2d","15","2");
		Quarto quarto2 = new Quarto(null, "2d","15","2");
		
		//when / act
		quartoRepository.save(quarto1);
		quartoRepository.save(quarto2);
		
		List<Quarto> quartoList = quartoRepository.findAll();
		
		//Then / Assert
		assertNotNull(quartoList);
		assertEquals(2, quartoList.size());
		
	}
	@DisplayName("Tenstando By ID")
	@Test
	void testGetById() {
		
		//given / Arrange
		Quarto quarto1 = new Quarto(null, "2d","15","2");
				
		//when / act
		quartoRepository.save(quarto1);
		
		Quarto saveQuarto = quartoRepository.findById(quarto1.getId()).get();
		
		//Then / Assert
		assertNotNull(saveQuarto);
		assertEquals(quarto1.getId(),saveQuarto.getId());
		
	}
	@DisplayName("Tenstando By ID")
	@Test
	void testUpdateQuarto() {
		
		//given / Arrange
		Quarto quarto1 = new Quarto(null, "2d","15","2");
				
		//when / act
		quartoRepository.save(quarto1);
		
		Quarto saveQuarto = quartoRepository.findById(quarto1.getId()).get();
		quarto1.setTipo("2D");
		quarto1.setNum("15");
		quarto1.setAndar("5");
		
		Quarto updateQuarto = quartoRepository.save(saveQuarto);
		//Then / Assert
		assertNotNull(updateQuarto);
		assertEquals("2D", updateQuarto.getTipo());
		assertEquals("15", updateQuarto.getNum());
		assertEquals("5", updateQuarto.getAndar());
		
	}
	@DisplayName("Tenstando By ID")
	@Test
	void testDeleteQuarto() {
		
		//given / Arrange
		Quarto quarto1 = new Quarto(null, "2d","15","2");
				
		//when / act
		quartoRepository.save(quarto1);
		
		quartoRepository.deleteById(quarto1.getId());
		
		Optional<Quarto> quartoOptional = quartoRepository.findById(quarto1.getId());
		//Then / Assert
		assertNotNull(quartoOptional);

	}

}
