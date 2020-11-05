package com.carrafasoft.syscondosind.api.resource;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
import com.carrafasoft.syscondosind.api.model.UsuarioSistema;
import com.carrafasoft.syscondosind.api.repository.UsuariosRepository;
import com.carrafasoft.syscondosind.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<UsuarioSistema> listarTodos() {
		
		return usuariosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<UsuarioSistema> cadastrarUsuario(@Valid @RequestBody UsuarioSistema usuario, HttpServletResponse response) {
		
		/**O sistema ainda não está com o spring security (quando fizer a iclusão voltar e mudar o metodo para criptografar senha*/
		//TODO criptografar a senha
		
		UsuarioSistema usuarioSalvo = usuariosRepository.save(usuario);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getUsuarioSistemaId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@GetMapping("/{codigo}")
	public Optional<UsuarioSistema> buscaPorId(@PathVariable Long codigo) {
		
		return usuariosRepository.findById(codigo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUsuario(@PathVariable Long codigo) {
		
		usuariosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<UsuarioSistema> atualizaUsuario(@PathVariable Long codigo, @Valid @RequestBody UsuarioSistema usuario) {
		
		UsuarioSistema usuarioSalvo = usuarioService.atualizaUsuario(codigo, usuario);
		return ResponseEntity.ok(usuarioSalvo);
	}

}
