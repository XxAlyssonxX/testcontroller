package com.yee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yee.entities.Quarto;
import com.yee.services.QuartoService;

@RestController
@RequestMapping("/quarto")
public class QuartoController {

	private final QuartoService quartoService;
	@Autowired
	public QuartoController(QuartoService quartoService) {
		this.quartoService = quartoService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Quarto> getQuartoById(@PathVariable Long id) {
		Quarto quarto = quartoService.getQuartoById(id);
		if (quarto != null) {
			return ResponseEntity.ok(quarto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Quarto>> getAllQuarto() {
		List<Quarto> quarto = quartoService.getAllQuarto();
		return ResponseEntity.ok(quarto);
	}
	
	//@GetMapping("/data/{data}")
	//public ResponseEntity<List<Quarto>> getPorData(@PathVariable String data){
		//List<Quarto> quarto = quartoService.getPorData(data);
		//return ResponseEntity.ok(quarto);
	//}
	
	
	@PostMapping("/")
	public ResponseEntity<Quarto> criarQuarto(@RequestBody Quarto quarto) {
		Quarto criarQuarto = quartoService.salvarQuarto(quarto);
		return ResponseEntity.status(HttpStatus.CREATED).body(criarQuarto);
	}
	


	@PutMapping("/{id}")
	public ResponseEntity<Quarto> updateQuarto(@PathVariable Long id,@RequestBody Quarto quarto) {
		Quarto updatedQuarto = quartoService.updateQuarto(id, quarto);
		if (updatedQuarto != null) {
			return ResponseEntity.ok(updatedQuarto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteQuarto(@PathVariable Long id) {
		boolean deleted = quartoService.deleteQuarto(id);
		if (deleted) {
			return ResponseEntity.ok().body("O quarto foi excluído com sucesso.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}


}








