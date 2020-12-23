package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select *, (preco_de_compra * quantidade) as \"valor_total\" "
					+ "from produtos ")
	List<Produtos> listarProdutosComCalculoDeItensPorPreco();

}
