package com.carrafasoft.syscondosind.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.Moradores;
import com.carrafasoft.syscondosind.api.repository.MoradorRepository;

@RestController
@RequestMapping("/moradores")
public class MoradoresResource {
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	@GetMapping
	public List<Moradores> listarTodos() {
		
		return moradorRepository.findAll();
	}

}
