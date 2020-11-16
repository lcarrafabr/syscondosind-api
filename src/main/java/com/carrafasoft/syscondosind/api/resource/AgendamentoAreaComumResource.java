package com.carrafasoft.syscondosind.api.resource;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.AgendamentoAreaComum;
import com.carrafasoft.syscondosind.api.model.AreasComuns;
import com.carrafasoft.syscondosind.api.repository.AgendamentoAreaComumRepository;
import com.carrafasoft.syscondosind.api.repository.AreasComunsRepository;
import com.carrafasoft.syscondosind.api.service.AgendamentoAreaComumService;

@RestController
@RequestMapping("agendamento-area-comum")
public class AgendamentoAreaComumResource {
	
	@Autowired
	private AgendamentoAreaComumRepository agendamentoAreaComumRepository;
	
	@Autowired 
	private AreasComunsRepository AreasComunsRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private AgendamentoAreaComumService agendamentoService;
	
	@GetMapping
	public List<AgendamentoAreaComum> listarTodos() {
		
		return agendamentoAreaComumRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<AgendamentoAreaComum> cadastrarAreaComum(@Valid @RequestBody AgendamentoAreaComum agendamento, HttpServletResponse response) {
		
		AgendamentoAreaComum agendamentoSalvo= new AgendamentoAreaComum();
		
		AreasComuns areaComumSalva = AreasComunsRepository.findById(agendamento.getAreaComum().getAreaComumId()).orElseThrow(() -> new EmptyResultDataAccessException(1));		
		BigDecimal valorAreaComum = areaComumSalva.getValorLocacao(); 
		
		if( valorAreaComum.compareTo(BigDecimal.ZERO) > 0) {
			
			
			if(areaComumSalva.getContaBancaria().getContaBancariaId() > 0 
					&& areaComumSalva.getCategoriaContas().getCategoriaContaId() > 0
					&& areaComumSalva.getCentroCusto().getCentroCustoId() > 0) {
				
				agendamentoSalvo = agendamentoService.cadastraAgendamentoECriaLancamento(agendamento, valorAreaComum, areaComumSalva);
				
			}else {
				
				return ResponseEntity.badRequest().build();
			}
			
		} else {
			
			agendamentoSalvo = agendamentoAreaComumRepository.save(agendamento);
		}
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, agendamentoSalvo.getAgendamentoAreaComumId()));
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
	}

}
