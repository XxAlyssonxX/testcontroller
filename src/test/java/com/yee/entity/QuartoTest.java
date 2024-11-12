package com.yee.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.yee.entities.Quarto;


class QuartoTest {

private Quarto quarto;
	
	//Arrange
	@BeforeEach
	void setUp() {
		quarto = new Quarto(1L, "2q","15","5");
	}
	
	
	
	@Test
	@DisplayName("Tstando o getter e setter do campo Id")
	void testId() {
		//Action
		quarto.setId(2L);
		
		//Assert
		assertEquals(2L, quarto.getId());
	}
	@Test
	@DisplayName("Tstando o getter e setter do campo Nome")
	void testNome() {
		//Action
		quarto.setTipo("2q");
		quarto.setNum("15");
		quarto.setAndar("5");
		
		//Assert
	}
	
	@Test
	@DisplayName("Testando o construtor com todos os argumentos")
	void testConstructorAll() {
		//act
		Quarto  novoquarto = new Quarto(3L, "3q","20","3");
		//Assert
		assertAll("novoquarto", 
				()-> assertEquals(3L, novoquarto.getId()),
				()-> assertEquals("Matheus", novoquarto.getTipo()),
				()-> assertEquals("Matheus", novoquarto.getNum()),
				()-> assertEquals("Matheus", novoquarto.getAndar())
				
				);
		
	}

};
