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
import com.carrafasoft.syscondosind.api.model.GaragemMorador;
import com.carrafasoft.syscondosind.api.repository.GaragemMoradorRepository;
import com.carrafasoft.syscondosind.api.service.GaragemMoradorService;

@RestController
@RequestMapping("/garagem-morador")
public class GaragemMoradorResource {
	
	@Autowired
	private GaragemMoradorRepository garagemMoradorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private GaragemMoradorService garagemMoradorService;
	
	@GetMapping
	public List<GaragemMorador> listarTodos() {
		
		return garagemMoradorRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<GaragemMorador> cadastrarVagaMorador(@Valid @RequestBody GaragemMorador garagemMorador, HttpServletResponse response) {
		
		GaragemMorador vagaMorador = garagemMoradorRepository.save(garagemMorador);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vagaMorador.getGaragemMoradorId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vagaMorador);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<GaragemMorador> buscaPorId(@PathVariable Long codigo) {
		
		Optional<GaragemMorador> vagaMorador = garagemMoradorRepository.findById(codigo);
		return vagaMorador.isPresent() ? ResponseEntity.ok(vagaMorador.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerVagaMorador(@PathVariable Long codigo) {
		
		garagemMoradorRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<GaragemMorador> atualizaMorador(@PathVariable Long codigo, @Valid @RequestBody GaragemMorador vagaMorador) {
		
		GaragemMorador vagaMoradorSalvo = garagemMoradorService.atualizarGaragemMorador(codigo, vagaMorador);
		return ResponseEntity.ok(vagaMoradorSalvo);
	}
	
	/********************************************************************************************************************************************/
	
	
	@GetMapping("/qtd-vagas-ocupadas")
	public Long quantidadeVagasOcupadas() {
		
		return garagemMoradorRepository.qtdVagaOcupada();
	}
	
	@GetMapping("/qtd-vagas-disponiveis")
	public Long quantidadeVagasDisponiveis() {
		
		return garagemMoradorRepository.qtdVagasDisponiveis();
	}
	
	@GetMapping("/qtd-morador-sem-vaga")
	public Long quantidadeMoradoresSemVagas() {
		
		return garagemMoradorRepository.qtdMoradoresSemVagas();
	}

}
