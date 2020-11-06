package com.carrafasoft.syscondosind.api.repository.portaria;

import java.util.List;

import com.carrafasoft.syscondosind.api.model.Portarias;
import com.carrafasoft.syscondosind.api.repository.filter.PortariasFilter;

public interface PortariasRepositoryQuery {
	
	public List<Portarias> filtrar(PortariasFilter portariaFilter);

}
