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

import com.yee.entities.Reserva;
import com.yee.services.ReservaService;	

@RestController
@RequestMapping("/reserva")
public class ReservaController {

	
private final ReservaService reservaService;
@Autowired
public ReservaController(ReservaService reservaService) {
	this.reservaService = reservaService;
}

@GetMapping("/{id}")
public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
	Reserva reserva = reservaService.getReservaById(id);
	if (reserva != null) {
		return ResponseEntity.ok(reserva);
	} else {
		return ResponseEntity.notFound().build();
	}
}

@GetMapping("/")
public ResponseEntity<List<Reserva>> getAllReserva() {
	List<Reserva> reserva = reservaService.getAllReserva();
	return ResponseEntity.ok(reserva);
}

//@GetMapping("/data/{data}")
//public ResponseEntity<List<Reserva>> getPorData(@PathVariable String data){
	//List<Reserva> reserva = reservaService.getPorData(data);
	//return ResponseEntity.ok(reserva);
//}


@PostMapping("/")
public ResponseEntity<Reserva> criarReserva(@RequestBody Reserva reserva) {
	Reserva criarReserva = reservaService.salvarReserva(reserva);
	return ResponseEntity.status(HttpStatus.CREATED).body(criarReserva);
}



@PutMapping("/{id}")
public ResponseEntity<Reserva> updateReserva(@PathVariable Long id,@RequestBody Reserva reserva) {
	Reserva updatedReserva = reservaService.updateReserva(id, reserva);
	if (updatedReserva != null) {
		return ResponseEntity.ok(updatedReserva);
	} else {
		return ResponseEntity.notFound().build();
	}
}

@DeleteMapping("/{id}")
public ResponseEntity<String> deleteReserva(@PathVariable Long id) {
	boolean deleted = reservaService.deleteReserva(id);
	if (deleted) {
		return ResponseEntity.ok().body("O reserva foi excluído com sucesso.");
	} else {
		return ResponseEntity.notFound().build();
	}
}


}
















