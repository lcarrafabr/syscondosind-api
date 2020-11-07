package com.carrafasoft.syscondosind.api.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Portaria;
import com.carrafasoft.syscondosind.api.repository.PortariasRepository;
import com.carrafasoft.syscondosind.api.repository.filter.PortariaFilter;
import com.carrafasoft.syscondosind.api.service.PortariaService;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

@RestController
@RequestMapping("/portaria")
public class PortariaResource {
	
	@Autowired
	private PortariasRepository portariasRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PortariaService portariaService;
	
	@GetMapping
	public List<Portaria> pesquisar(PortariaFilter portariaFilter) {
		
		return portariasRepository.findAll();
		//return portariasRepository.filtrar(portariaFilter);
	}

	
	@PostMapping
	public ResponseEntity<Portaria> cadastrarVisitante(@Valid @RequestBody Portaria portaria, HttpServletResponse response) {
		
		Portaria portariaSalva = portariasRepository.save(portaria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, portariaSalva.getControlePortariaId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(portariaSalva);
	}
	
	@GetMapping("/{codigo}")
	public Optional<Portaria> buscaPorId(@PathVariable Long codigo) {
		
		return portariasRepository.findById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Portaria> atualizaVisita(@PathVariable Long codigo, @Valid @RequestBody Portaria portaria) {
		
		Portaria portariaSalva = portariaService.atualizarPortaria(codigo, portaria);
		return ResponseEntity.ok(portariaSalva);
	}
	
	/*****************************************************************************************************************************************************/
	
	@GetMapping("/busca-por-nome")
	public List<Portaria> buscaPorNome(@RequestParam("nome") String nome) {
		
		return portariasRepository.buscaPorNome(nome);
	}
	
	@GetMapping("/busca-por-data-entrada")
	public List<Portaria> buscaPorDataEntrada(@RequestParam("dataEntradaDe") String dataEntradaDe,@RequestParam("dataEntradaAte") String dataEntradaAte) {
		
		return portariasRepository.buscaEntreDatas(
				FuncoesUtils.converterStringParaLocalDateTime(dataEntradaDe), 
				FuncoesUtils.converterStringParaLocalDateTime(dataEntradaAte));
	}
}
