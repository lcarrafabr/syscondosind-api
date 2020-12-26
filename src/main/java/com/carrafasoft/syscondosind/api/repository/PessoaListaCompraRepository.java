package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.PessoaListaCompras;

@Repository
public interface PessoaListaCompraRepository extends JpaRepository<PessoaListaCompras, Long>{

}
