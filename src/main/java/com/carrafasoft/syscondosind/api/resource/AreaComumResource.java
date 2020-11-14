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
import com.carrafasoft.syscondosind.api.model.AreasComuns;
import com.carrafasoft.syscondosind.api.repository.AreasComunsRepository;
import com.carrafasoft.syscondosind.api.service.AreaComumService;

@RestController
@RequestMapping("area-comum")
public class AreaComumResource {
	
	@Autowired
	private AreasComunsRepository areasComunsRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private AreaComumService areaComumService;
	
	
	@GetMapping
	public List<AreasComuns> listarTodos() {
		return areasComunsRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<AreasComuns> cadastrarAreaComum(@Valid @RequestBody AreasComuns areaComum, HttpServletResponse response) {
		
		areaComum.setNomeArea(areaComum.getNomeArea().toUpperCase());
		
		AreasComuns areaSalva = areasComunsRepository.save(areaComum);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, areaSalva.getAreaComumId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(areaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<AreasComuns> buscaPorId(@PathVariable Long codigo) {
		
		Optional<AreasComuns> areaSalva = areasComunsRepository.findById(codigo);
		
		return areaSalva.isPresent() ? ResponseEntity.ok(areaSalva.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerAreaComum(@PathVariable Long codigo) {
		areasComunsRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<AreasComuns> atualizaAreaComum(@PathVariable Long codigo, @Valid @RequestBody AreasComuns areaComum) {
		
		AreasComuns areaSalva = areaComumService.atualizaAreaComum(codigo, areaComum);
		return ResponseEntity.ok(areaSalva);
	}
	
	@PutMapping("/{codigo}/liberar-agendamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaLiberarParaAgendamento(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		
		areaComumService.atualizaLiberarParaAgendar(codigo, ativo);
	}

}
