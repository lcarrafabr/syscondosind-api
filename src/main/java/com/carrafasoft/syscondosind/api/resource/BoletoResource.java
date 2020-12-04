package com.carrafasoft.syscondosind.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.Boletos;
import com.carrafasoft.syscondosind.api.repository.BoletosRepository;
import com.carrafasoft.syscondosind.api.service.BoletosService;

import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;

@RestController
@RequestMapping("/boletos")
public class BoletoResource {
	
	@Autowired
	private BoletosRepository boletosRepository;
	
	@Autowired
	private BoletosService boletoService;
	
	@GetMapping
	public List<Boletos> listarTodos() {
		 
		return boletosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Boletos> cadastrarBoleto(@Valid @RequestBody Boletos boleto, HttpServletResponse response) {
		
		return boletoService.cadastrarBoleto(boleto, response);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Boletos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Boletos> boletoSalvo = boletosRepository.findById(codigo);
		
		return boletoSalvo.isPresent() ? ResponseEntity.ok(boletoSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void removerBoleto(@PathVariable Long codigo) {
		
		boletosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Boletos> atualizarBoleto(@PathVariable Long codigo, @Valid @RequestBody Boletos boleto) {
		
		Boletos boletoAtualizado = boletoService.atualizarBoleto(codigo, boleto);
		
		return ResponseEntity.ok(boletoAtualizado);
	}
	
	@GetMapping("/imprimir-boleto/{codigo}")
	public GeradorDeBoletoHTML imprimirBoleto(@PathVariable Long codigo, HttpServletResponse response) {
		
		GeradorDeBoletoHTML boleto = boletoService.imprimirBoleto(codigo, response);
		
		return null;
	}

}
