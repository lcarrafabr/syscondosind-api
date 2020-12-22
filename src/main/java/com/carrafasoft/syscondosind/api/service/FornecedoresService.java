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
import com.carrafasoft.syscondosind.api.repository.FornecedorRepository;

import br.com.caelum.stella.boleto.Endereco;

@Service
public class FornecedoresService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private EnderecosService enderecosService;;
	
	
	public Fornecedores atualizarFornecedor(Long codigo, Fornecedores fornecedor) {
		
		Fornecedores fornecedorSalvo = buscaPorId(codigo);
		BeanUtils.copyProperties(fornecedor, fornecedorSalvo, "fornecedorId");
		
		return fornecedorRepository.save(fornecedorSalvo);
	}
	
	public void atualizaStatusAtivo(Long codigo, Boolean ativo) {
		
		Fornecedores fornecedorSalvo = buscaPorId(codigo);
		fornecedorSalvo.setStatus(ativo);
		fornecedorRepository.save(fornecedorSalvo);
	}
	
	/**
	 * @return *********************************************************************************************************************/
	
	public Fornecedores cadastrarFornecedores(Fornecedores fornecedor, HttpServletResponse response) {
		
		String cep = fornecedor.getEndereco().getCep();

		if(cep != null && !cep.isEmpty()) {

			Enderecos enderecoSalvo =  enderecosService.cadastraEndereco(fornecedor, response);
			fornecedor.setEndereco(enderecoSalvo);
		}
	
		Fornecedores fornecedorSalvo = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getFornecedorId()));
		
		return fornecedorSalvo;
	}
	
	private Fornecedores buscaPorId(Long codigo) {
		
		Fornecedores fornecedorSalvo = fornecedorRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return fornecedorSalvo;
	}

}
