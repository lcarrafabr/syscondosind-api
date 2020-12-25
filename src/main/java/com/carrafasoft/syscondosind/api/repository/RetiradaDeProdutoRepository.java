package com.carrafasoft.syscondosind.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Produtos;
import com.carrafasoft.syscondosind.api.model.RetiradaDeProdutos;

@Repository
public interface RetiradaDeProdutoRepository extends JpaRepository<RetiradaDeProdutos, Long>{

}
