package com.carrafasoft.syscondosind.api.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Enderecos;
import com.carrafasoft.syscondosind.api.model.Fornecedores;
import com.carrafasoft.syscondosind.api.repository.EnderecoRepository;

@Service
public class EnderecosService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	public Enderecos atualizaEndereco(Enderecos endereco, Long codigo) {
		
		Enderecos enderecoSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(endereco, enderecoSalvo, "enderecoId");
		return enderecoRepository.save(enderecoSalvo);
	}
	
	/*****************************************************************************************************/
	
	public Enderecos cadastraEndereco(Fornecedores fornecedor, HttpServletResponse response) {
		
		Enderecos endereco = new Enderecos();
		
		endereco.setCondominios(fornecedor.getCondominio());
		endereco.setCep(fornecedor.getEndereco().getCep());
		endereco.setLogradouro(fornecedor.getEndereco().getLogradouro());
		endereco.setNumero(fornecedor.getEndereco().getNumero());
		endereco.setBairro(fornecedor.getEndereco().getBairro());
		endereco.setCidade(fornecedor.getEndereco().getCidade());
		endereco.setEstado(fornecedor.getEndereco().getEstado());
		endereco.setComplemento(fornecedor.getEndereco().getComplemento());
		endereco.setCodigoIbge(fornecedor.getEndereco().getCodigoIbge());
		endereco.setTipoEndereco(fornecedor.getEndereco().getTipoEndereco());
		
		Enderecos enderecoSalvo = enderecoRepository.save(endereco);
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, enderecoSalvo.getEnderecoId()));
		
		return enderecoSalvo;
	}
	
	
	private Enderecos buscaPorId(Long codigo) {
		
		Enderecos enderecoSalvo = enderecoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return enderecoSalvo;
	}

}
