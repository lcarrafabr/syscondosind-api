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
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.LancamentosRepository;
import com.carrafasoft.syscondosind.api.service.LancamentosService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentosResource {
	
	@Autowired
	private LancamentosRepository lancamentosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentosService lancamentoService;
	
	@GetMapping
	public List<Lancamentos> listarTodos() {
		return lancamentosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Lancamentos> cadastrarLancamento(@Valid @RequestBody Lancamentos lancamento, HttpServletResponse response) {
		
		Lancamentos lancamentoSalvo = new Lancamentos();
		
		if(lancamento.getParcelado().equals(true)) {
			
			//lancamentoSalvo = lancamentoService.cadastrarLancamentosParcelado(lancamento, response);
			
			List<Lancamentos> lista = lancamentoService.cadastrarLancamentosParcelado(lancamento, response);
			
			//lancamentosRepository.saveAll(lista);
		
		}else {
			
			lancamentoSalvo = lancamentosRepository.save(lancamento);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getLancamentoId()));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

}
