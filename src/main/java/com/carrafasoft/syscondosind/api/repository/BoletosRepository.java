package com.carrafasoft.syscondosind.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Boletos;

@Repository
public interface BoletosRepository extends JpaRepository<Boletos, Long>{

}
