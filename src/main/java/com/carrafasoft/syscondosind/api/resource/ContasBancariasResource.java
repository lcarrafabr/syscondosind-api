package com.carrafasoft.syscondosind.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.ContasBancarias;
import com.carrafasoft.syscondosind.api.repository.ContaBancariaRepository;

@RestController
@RequestMapping("/contas-bancarias")
public class ContasBancariasResource {
	
	@Autowired
	private ContaBancariaRepository contaBancariaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	private List<ContasBancarias> listarTodos() {
		
		return contaBancariaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ContasBancarias> cadastrarContabancaria(@Valid @RequestBody ContasBancarias conta, HttpServletResponse response) {
		
		ContasBancarias contaSalva = contaBancariaRepository.save(conta);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getContaBancariaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}

}
