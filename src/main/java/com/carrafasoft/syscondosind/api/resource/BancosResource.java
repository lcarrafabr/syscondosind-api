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
import com.carrafasoft.syscondosind.api.model.Bancos;
import com.carrafasoft.syscondosind.api.repository.BancosRepository;
import com.carrafasoft.syscondosind.api.service.BancoService;

@RestController
@RequestMapping("/bancos")
public class BancosResource {
	
	@Autowired
	private BancosRepository bancosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private BancoService bancoService;
	
	@GetMapping
	public List<Bancos> listarTodos() {
		
		return bancosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Bancos> cadastrarBanco(@Valid @RequestBody Bancos banco, HttpServletResponse response) {
		
		Bancos bancoSalvo = bancosRepository.save(banco);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, bancoSalvo.getBancoId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bancoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Bancos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Bancos> bancoSalvo = bancosRepository.findById(codigo);
		return bancoSalvo.isPresent() ? ResponseEntity.ok(bancoSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerBanco(@PathVariable Long codigo) {
		bancosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Bancos> atualizabanco(@PathVariable Long codigo, @Valid @RequestBody Bancos banco) {
		
		Bancos bancoSalvo = bancoService.atualizaBanco(codigo, banco);
		
		return ResponseEntity.ok(bancoSalvo);
	}
	
	@PutMapping("{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatusBancoAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		bancoService.atualizaStatusAtivo(codigo, ativo);
	}
	
	/***********************************************************************************************************************************/
	
	
	@GetMapping("/ativos")
	public List<Bancos> buscarBancosAtivos() {
		return bancosRepository.buscarBancosAtivos();
	}

}
