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
import com.carrafasoft.syscondosind.api.model.VagasGaragem;
import com.carrafasoft.syscondosind.api.repository.VagaGaragemRepository;
import com.carrafasoft.syscondosind.api.service.VagaGaragemService;

@RestController
@RequestMapping("/vaga-garagem")
public class VagaGaragemResource {
	
	@Autowired
	private VagaGaragemRepository vagaGaragemRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private VagaGaragemService vagaGaragemService;
	
	@GetMapping
	public List<VagasGaragem> listarTodos() {
		return vagaGaragemRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<VagasGaragem> cadastrarVagaGaragem(@Valid @RequestBody VagasGaragem vaga, HttpServletResponse response) {
		
		vaga.setNomeVaga(vaga.getNomeVaga().toUpperCase());
		
		VagasGaragem vagaSalva = vagaGaragemRepository.save(vaga);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vagaSalva.getVagaGaragemId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vagaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<VagasGaragem> buscaVagaPorId(@PathVariable Long codigo) {
		
		Optional<VagasGaragem> vaga = vagaGaragemRepository.findById(codigo);
		
		return vaga.isPresent() ? ResponseEntity.ok(vaga.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerVaga(@PathVariable Long codigo) {
		
		vagaGaragemRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<VagasGaragem> atualizaVaga(@PathVariable Long codigo, @Valid @RequestBody VagasGaragem vaga) {
		
		VagasGaragem vagaSalva = vagaGaragemService.atualizaVaga(codigo, vaga);
		return ResponseEntity.ok(vagaSalva);
	}
	
	/****************************************************************************************************************************/
	
	@GetMapping("/qtd-vaga-total")
	public Long quantidadeVagasTotal() {
		return vagaGaragemRepository.count();
	}
	
	
	/**Pensar em como retornar esses dados de uma forma melhor =>> retorno [["A1",3],["A2",2]]**/
	@GetMapping("/qtd-vagas-por-setor")
	public List<Object> totalVagasPorSetor() {
		return vagaGaragemRepository.totalVagasPorSetor();
	}

}
