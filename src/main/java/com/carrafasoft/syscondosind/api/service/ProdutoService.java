package com.carrafasoft.syscondosind.api.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.model.Produtos;
import com.carrafasoft.syscondosind.api.model.apoio.ControleEstoqueModelApoio;
import com.carrafasoft.syscondosind.api.repository.ProdutoRepository;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produtos atualizarProduto(Long codigo, Produtos produto) {
		
		Produtos produtoSalvo = produtoRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(produto, produtoSalvo, "produtoId");
		
		return produtoRepository.save(produtoSalvo);
	}
	
	public List<ControleEstoqueModelApoio> controleEstoque() {
		
		List<Object[]> retornoEstoque = produtoRepository.controleEstoque();
		List<ControleEstoqueModelApoio> lista = new ArrayList<ControleEstoqueModelApoio>();

		
		for(Object[] obj : retornoEstoque){
			
			ControleEstoqueModelApoio controleEstoque = new ControleEstoqueModelApoio();
			
			BigInteger produtoId = (BigInteger) obj[0];
			
			Date data = (Date) obj[3];
			LocalDate localDate = FuncoesUtils.converteSQLDateToLocalDate(data);
			
		    controleEstoque.setProdutoId(produtoId);
		    controleEstoque.setNomeProduto((String) obj[1]);
		    controleEstoque.setCodigoDeBarras((String) obj[2]);
		    controleEstoque.setDataDecompra(localDate);
		    controleEstoque.setQtdEmEstoque((Double) obj[4]);
		    controleEstoque.setQtdComprada((Double) obj[5]);
		    
		    lista.add(controleEstoque);
		}
		
		return lista;
	}
	
	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

}
