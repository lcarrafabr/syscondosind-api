package com.carrafasoft.syscondosind.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carrafasoft.syscondosind.api.model.Portarias;
import com.carrafasoft.syscondosind.api.repository.portaria.PortariasRepositoryQuery;

@Repository
public interface PortariasRepository extends JpaRepository<Portarias, Long>, PortariasRepositoryQuery{
	

}
