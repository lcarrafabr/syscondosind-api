package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long>{
	
	
	@Query(nativeQuery = true,
			value = "select *, (preco_de_compra * quantidade) as \"valorTotal\" "
					+ "from produtos ")
	List<Produtos> listarProdutosComCalculoDeItensPorPreco();
	
	

	@Query(nativeQuery = true,
			value = "select if((p.quantidade - sum(r.quantidade)) is null, p.quantidade, (p.quantidade - sum(r.quantidade)) - :qtdARetirar) as \"qtd_em_estoque\" "
					+ "from produtos p "
					+ "left join retirada_de_produtos r on p.produto_id = r.produto_id "
					+ "where p.produto_id = :codigo "
					+ "group by p.produto_id ")
	Double verificaQtdRetirada(Long codigo, Double qtdARetirar);
	
	
	@Query(nativeQuery = true,
			value = "select p.produto_id, p.nome_produto, p.codigo_de_barras, p.data_de_compra, "
					+ "if((p.quantidade - sum(r.quantidade)) is null, p.quantidade, (p.quantidade - sum(r.quantidade))) as \"qtd_em_estoque\" , p.quantidade as \"qtd_comprada\" "
					+ "from produtos p "
					+ "left join retirada_de_produtos r on p.produto_id = r.produto_id "
					+ "group by p.produto_id ")
	List<Object[]> controleEstoque();
	
	
	@Query(nativeQuery = true,
			value = "select * from produtos "
					+ "where codigo_de_barras = :codigoDeBarras ")
	List<Produtos> findByCodigoDeBarras(String codigoDeBarras);

}
