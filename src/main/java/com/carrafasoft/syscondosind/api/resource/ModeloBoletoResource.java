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
import com.carrafasoft.syscondosind.api.model.ModeloBoletos;
import com.carrafasoft.syscondosind.api.repository.ModelosBoletoRepository;
import com.carrafasoft.syscondosind.api.service.ModeloBoletoService;

@RestController
@RequestMapping("/modelo-boleto")
public class ModeloBoletoResource {
	
	@Autowired
	private ModelosBoletoRepository modelosBoletoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ModeloBoletoService modeloService;
	
	@GetMapping
	public List<ModeloBoletos> listarTodos() {
		
		return modelosBoletoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ModeloBoletos> cadastrarModeloBoleto(@Valid @RequestBody ModeloBoletos modelo, HttpServletResponse response) {
		
		ModeloBoletos modeloRetorno =  modeloService.cadastrarModeloBoleto(modelo, response);
		ModeloBoletos modeloSalvo = modelosBoletoRepository.save(modeloRetorno);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, modeloSalvo.getModeloBoletoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(modeloSalvo);
	}
	
	@GetMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<ModeloBoletos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<ModeloBoletos> modeloSalvo = modelosBoletoRepository.findById(codigo);
		
		return modeloSalvo.isPresent() ? ResponseEntity.ok(modeloSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void removerModelo(@PathVariable Long codigo) {
		
		modelosBoletoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ModeloBoletos> atualizaModeloBoleto(@PathVariable Long codigo, @Valid @RequestBody ModeloBoletos modelo) {
		
		ModeloBoletos modeloSalvo = modeloService.atualizarModeloBoleto(modelo, codigo);
		
		return ResponseEntity.ok(modeloSalvo);
	}

}
