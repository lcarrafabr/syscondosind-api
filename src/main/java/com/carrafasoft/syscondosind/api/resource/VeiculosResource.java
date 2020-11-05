package com.carrafasoft.syscondosind.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Veiculos;
import com.carrafasoft.syscondosind.api.repository.VeiculosRepository;
import com.carrafasoft.syscondosind.api.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculosResource {
	
	@Autowired
	private VeiculosRepository veiculosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@GetMapping
	public List<Veiculos> listarTodos() {
		
		return veiculosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Veiculos> cadastrarVeiculo(@Valid @RequestBody Veiculos veiculo, HttpServletResponse response) {
		
		Veiculos veiculoSalvo = veiculosRepository.save(veiculo);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, veiculoSalvo.getVeiculoId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Veiculos> buscaPorId(@PathVariable Long codigo) {
		
		return veiculosRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerVeiculo(@PathVariable Long codigo) {
		
		veiculosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Veiculos> atualizarVeiculo(@PathVariable Long codigo, @Valid @RequestBody Veiculos veiculo) {
		
		Veiculos veiculoSalvo = veiculoService.atualizarVeiculo(codigo, veiculo);
		return ResponseEntity.ok(veiculoSalvo);
	}

}
