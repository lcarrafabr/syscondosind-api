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
import com.carrafasoft.syscondosind.api.model.CentroDeCustos;
import com.carrafasoft.syscondosind.api.repository.CentroCustoRepository;
import com.carrafasoft.syscondosind.api.service.CentroCustoService;

@RestController
@RequestMapping("centro-custos")
public class CentroCustoResource {
	
	@Autowired
	private CentroCustoRepository centroCustoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CentroCustoService centroCustoService;
	
	
	@GetMapping
	public List<CentroDeCustos> listarTodos() {
		return centroCustoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<CentroDeCustos> cadastrarCentroCusto(@Valid @RequestBody CentroDeCustos centroCusto, HttpServletResponse response) {
		
		CentroDeCustos centroCustoSalvo = centroCustoRepository.save(centroCusto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, centroCustoSalvo.getCentroCustoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(centroCustoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<CentroDeCustos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<CentroDeCustos> centroCustoSalvo = centroCustoRepository.findById(codigo);
		
		return centroCustoSalvo.isPresent() ? ResponseEntity.ok(centroCustoSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCentroCusto(@PathVariable Long codigo) {
		
		centroCustoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<CentroDeCustos> atualizaCentroCusto(@PathVariable Long codigo, @Valid @RequestBody CentroDeCustos centroCusto) {
		
		CentroDeCustos centroCustoSalvo = centroCustoService.atualizarCentroCusto(codigo, centroCusto);
		
		return ResponseEntity.ok(centroCustoSalvo);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaStatusAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		centroCustoService.atualizaStatusAtivo(codigo, ativo);
	}
	
	@GetMapping("/listar-ativos")
	public List<CentroDeCustos> buscaCentroCustoStatusAtivo() {
		return centroCustoRepository.buscaCentroCustoAtivo();
	}

}
