package com.carrafasoft.syscondosind.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.ModeloBoletos;
import com.carrafasoft.syscondosind.api.repository.ModelosBoletoRepository;

@RestController
@RequestMapping("/modelo-boleto")
public class ModeloBoletoResource {
	
	@Autowired
	private ModelosBoletoRepository modelosBoletoRepository;
	
	@GetMapping
	private List<ModeloBoletos> listarTodos() {
		
		return modelosBoletoRepository.findAll();
	}

}
