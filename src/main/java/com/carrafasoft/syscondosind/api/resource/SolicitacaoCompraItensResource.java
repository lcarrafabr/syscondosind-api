package com.carrafasoft.syscondosind.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.model.SolicitacaoCompraItens;
import com.carrafasoft.syscondosind.api.repository.SolicitacaoCompraItemRepository;

@RestController
@RequestMapping("/solicitacao-compra-itens")
public class SolicitacaoCompraItensResource {
	
	@Autowired
	private SolicitacaoCompraItemRepository repository;
	
	@GetMapping
	public List<SolicitacaoCompraItens> listarTodos() {
		
		return repository.findAll();
	}

}
