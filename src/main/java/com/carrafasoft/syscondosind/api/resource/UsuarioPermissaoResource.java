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
import com.carrafasoft.syscondosind.api.model.UsuarioPermissao;
import com.carrafasoft.syscondosind.api.repository.UsuarioPermissaoRepository;
import com.carrafasoft.syscondosind.api.service.UsuarioPermissaoService;

@RestController
@RequestMapping("/permissoes-usuarios")
public class UsuarioPermissaoResource {
	
	@Autowired
	private UsuarioPermissaoRepository usuarioPermissaoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioPermissaoService usuarioPermissaoService;
	
	@GetMapping
	public List<UsuarioPermissao> listarTodos() {
		
		return usuarioPermissaoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<UsuarioPermissao> cadastrarUsuarioPermissao(@Valid @RequestBody UsuarioPermissao userPermissao, HttpServletResponse response) {
		
		UsuarioPermissao userPermissaoSalvo = usuarioPermissaoRepository.save(userPermissao);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, userPermissaoSalvo.getUsuarioPermissao()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userPermissaoSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<UsuarioPermissao> buscaPorId(@PathVariable Long codigo) {
		
		return usuarioPermissaoRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUsuarioPermissao(@PathVariable Long codigo) {
		
		usuarioPermissaoRepository.deleteById(codigo);
	}
	
	/***NÃO FUNCIOANDO AINDA. DESCOBRIR O ERRO**/
	
//	@PutMapping("/{codigo}")
//	public ResponseEntity<UsuarioPermissao> atualizaPermissao(@PathVariable Long codigo, @RequestBody UsuarioPermissao userPermit) {
//		
//		UsuarioPermissao userPermitSalvo = usuarioPermissaoService.atualizausuarioPermissao(codigo, userPermit);
//		
//		return ResponseEntity.ok(userPermitSalvo);
//	}
	
	/*******************************************************************************************************************************************************************/
	
	@DeleteMapping("/{codigo}/bloquear-acessos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bloquearTodosAcessos(@PathVariable Long codigo) {
		
		usuarioPermissaoRepository.bloquearTodosOsAcessos(codigo);
	}

}
