package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long>{

}
