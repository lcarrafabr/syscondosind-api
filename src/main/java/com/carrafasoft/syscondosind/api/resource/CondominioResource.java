package com.carrafasoft.syscondosind.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.Condominios;
import com.carrafasoft.syscondosind.api.repository.CondominioRepository;

@RestController
@RequestMapping("/condominios")
public class CondominioResource {
	
	@Autowired
	private CondominioRepository condominioRepository;
	
	@GetMapping
	public List<Condominios> listar() {
		
		return condominioRepository.findAll();
	}
	
	@PostMapping
	public void criarCondominio(@RequestBody Condominios condominios) {
		
		Condominios condominioSalvo = condominioRepository.save(condominios);
		
	}

}
