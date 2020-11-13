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
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

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
		
		String chavePesquisa = FuncoesUtils.gerarHash();
		
		List<Lancamentos> verificaLanc = lancamentosRepository.buscarPorchave(chavePesquisa);
		
		 while(!verificaLanc.isEmpty()) {
			 
			 chavePesquisa = FuncoesUtils.gerarHash();
			 verificaLanc = lancamentosRepository.buscarPorchave(chavePesquisa);
		 }
		
		
		
		FuncoesUtils.gerarHash();
		
		if(lancamento.getParcelado().equals(true)) {
			
			//lancamentoSalvo = lancamentoService.cadastrarLancamentosParcelado(lancamento, response);
			
			lancamentoSalvo = lancamentoService.cadastrarLancamentosParcelado(lancamento, response, chavePesquisa);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getLancamentoId()));
			
			//lancamentosRepository.saveAll(lista);
		
		}else {
			
			lancamento.setChavePesquisa(chavePesquisa);
			lancamentoSalvo = lancamentosRepository.save(lancamento);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getLancamentoId()));
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
		//return null;
	}

}
