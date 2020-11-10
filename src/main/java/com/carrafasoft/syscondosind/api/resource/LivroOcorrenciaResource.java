package com.carrafasoft.syscondosind.api.resource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.LivroOcorrencia;
import com.carrafasoft.syscondosind.api.repository.LivroOcorrenciaRepository;
import com.carrafasoft.syscondosind.api.service.LivroOcorrenciaService;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

@RestController
@RequestMapping("livro-ocorrencia")
public class LivroOcorrenciaResource {
	
	@Autowired
	private LivroOcorrenciaRepository livroOcorrenciaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LivroOcorrenciaService livroOcorrenciaService;
	
	@GetMapping
	public List<LivroOcorrencia> listarTodos() {
		
		return livroOcorrenciaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<LivroOcorrencia> criarOcorrencia(@Valid @RequestBody LivroOcorrencia ocorrencia, HttpServletResponse response) {
		
		LivroOcorrencia ocorrenciaSalva = livroOcorrenciaRepository.save(ocorrencia);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, ocorrenciaSalva.getOcorrenciaId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciaSalva);
	}
	
	@GetMapping("/{codigo}")
	public Optional<LivroOcorrencia> buscaPorId(@PathVariable Long codigo) {
		
		return livroOcorrenciaRepository.findById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<LivroOcorrencia> atualizarOcorrencia(@PathVariable Long codigo, @Valid @RequestBody LivroOcorrencia ocorrencia) {
		
		LivroOcorrencia ocorrenciaSalva = livroOcorrenciaService.atualizaOcorrencia(codigo, ocorrencia);
		
		return ResponseEntity.ok(ocorrenciaSalva);
	}
	
	/**********************************************************************************************************************************************************/
	
	@GetMapping("/pesquisar")
	public List<LivroOcorrencia> buscaEntreDataOcorrencia(@RequestParam("dataOcorrenciaDe") String dataOcorrenciaDe, 
			@RequestParam("dataOcorrenciaAte") String dataOcorrenciaAte) {
		
		return livroOcorrenciaRepository.buscarEntreDataAbertura(
				FuncoesUtils.converterStringParaLocalDateTime(dataOcorrenciaDe),
				FuncoesUtils.converterStringParaLocalDateTime(dataOcorrenciaAte));
	}
	
	@GetMapping("pesquisar/{status}")
	public List<LivroOcorrencia> buscaPorStatusOcorrencia(@PathVariable String status) {
		
		return livroOcorrenciaRepository.buscaPorStatusOcorrencia(status);
	}
	
	@GetMapping("/qtd-ocorrencias")
	public Long buscaQuantidadeOcorrenciaTotal() {
		
		return livroOcorrenciaRepository.count();
	}
	
	@GetMapping("/qtd-ocorrencias/{status}")
	public Long buscaQuantidadePorStatus(@PathVariable String status) {
		
		return livroOcorrenciaRepository.buscaQtdPorStatus(status);
	}
	

}
