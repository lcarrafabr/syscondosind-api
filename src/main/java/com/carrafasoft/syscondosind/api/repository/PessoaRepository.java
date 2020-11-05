package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrafasoft.syscondosind.api.model.Pessoas;

public interface PessoaRepository extends JpaRepository<Pessoas, Long>{

}
