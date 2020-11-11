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
import com.carrafasoft.syscondosind.api.model.SetoresGaragem;
import com.carrafasoft.syscondosind.api.repository.SetorGaragemRepository;
import com.carrafasoft.syscondosind.api.service.SetorGaragemService;

@RestController
@RequestMapping("/setor-garagem")
public class SetorGaragemResource {
	
	@Autowired
	private SetorGaragemRepository setorGaragemRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private SetorGaragemService setorGaragemService;
	
	@GetMapping
	public List<SetoresGaragem> listarTodos() {
		
		return setorGaragemRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<SetoresGaragem> cadastrarSetor(@Valid @RequestBody SetoresGaragem setor, HttpServletResponse response) {
		
		setor.setNomeSetor(setor.getNomeSetor().toUpperCase());
		
		SetoresGaragem setorSalvo = setorGaragemRepository.save(setor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, setorSalvo.getSetorGaragemId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(setorSalvo);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<SetoresGaragem> buscarSetorPorId(@PathVariable Long codigo) {
		
		Optional<SetoresGaragem> setoresGaragem = setorGaragemRepository.findById(codigo);
		return setoresGaragem.isPresent() ? ResponseEntity.ok(setoresGaragem.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerSetor(@PathVariable Long codigo) {
		
		setorGaragemRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<SetoresGaragem> atualizaSetor(@PathVariable Long codigo, @Valid @RequestBody SetoresGaragem setor) {
		
		SetoresGaragem setorSalvo = setorGaragemService.atualizaSetorGaragem(codigo, setor);
		return ResponseEntity.ok(setorSalvo);
	}
	
	/*************************************************************************************************************************************************/
	
	@GetMapping("/total-setores")
	public Long quantidadeSetores() {
		return setorGaragemRepository.count();
	}

}
