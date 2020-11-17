package com.carrafasoft.syscondosind.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.enums.FormaPagamento;
import com.carrafasoft.syscondosind.api.enums.StatusSituacao;
import com.carrafasoft.syscondosind.api.enums.TipoNatureza;
import com.carrafasoft.syscondosind.api.model.AgendamentoAreaComum;
import com.carrafasoft.syscondosind.api.model.AreasComuns;
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.AgendamentoAreaComumRepository;

@Service
public class AgendamentoAreaComumService {
	
	
	@Autowired
	private AgendamentoAreaComumRepository agendamentoAreaComumRepository;
	
	@Autowired LancamentosService lancamentoService;
	
	
	public AgendamentoAreaComum cadastraAgendamentoECriaLancamento(AgendamentoAreaComum agendamento, BigDecimal valorLocacao, AreasComuns areaComumSalva) {
		
		
		cadastrarLancamentoQuandoAlugarAreaComum(valorLocacao, agendamento, areaComumSalva);
		
		AgendamentoAreaComum agendamentoSalvo = agendamentoAreaComumRepository.save(agendamento);
		
		
		return agendamentoSalvo;
	}
	
	
	private void cadastrarLancamentoQuandoAlugarAreaComum(BigDecimal valorAluguel, AgendamentoAreaComum agendamento, AreasComuns areaComumSalva) {
		
		Lancamentos lancNovo = new Lancamentos();
		String descricao = "Lançamento da locação da Area comum: " + agendamento.getTituloAgendamento().trim().toUpperCase();
		
		lancNovo.setTipoNatureza(TipoNatureza.A_PAGAR);
		lancNovo.setValor(valorAluguel);
		lancNovo.setDataVencimento(criarDataLancamento(agendamento.getDataInicioAgendamento()));
		lancNovo.setDataPagamento(null);
		lancNovo.setDescricao(descricao);
		lancNovo.setSituacao(StatusSituacao.PENDENTE);
		lancNovo.setParcelado(false);
		lancNovo.setQuantidadeParcelas(1);
		lancNovo.setNumeroParcela(1);
		lancNovo.setFormaPagamento(FormaPagamento.BOLETO);
		
		lancNovo.setContaBancaria(areaComumSalva.getContaBancaria());
		lancNovo.setCentroCusto(areaComumSalva.getCentroCusto());
		lancNovo.setCategoriaConta(areaComumSalva.getCategoriaContas());
		
		lancamentoService.cadastrarLancamentoQuandoAlugarAreaComum(lancNovo);
		
	}
	
		public Boolean verificaDataDisponivel(AgendamentoAreaComum agendamento) {
		
			Boolean retorno = false;
			
			LocalDateTime dataInicio = agendamento.getDataInicioAgendamento(); 
			LocalDateTime dataFim = agendamento.getDataFimAgendamento();
			
			LocalDateTime verificaDataInicio = agendamentoAreaComumRepository.buscaDataInicioLivre(dataInicio);
			LocalDateTime verificaDataFim = agendamentoAreaComumRepository.buscaDataFimLivre(dataFim);
		
			if(verificaDataInicio == null && verificaDataFim == null) {
				
				System.out.println("Verifica Data inicio: " + verificaDataInicio);
				retorno = true;
			}
		
		return retorno;
	}
	
	private AgendamentoAreaComum buscaPorId(Long codigo, AgendamentoAreaComum agendamento) {
		
		AgendamentoAreaComum agendamentoSalvo = agendamentoAreaComumRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return agendamentoSalvo;
	}
	
	private LocalDate criarDataLancamento(LocalDateTime dataAgendada) {
		
		int ano = dataAgendada.getYear();
		Month mes =  dataAgendada.getMonth();
		int dia = dataAgendada.getDayOfMonth();
		
		LocalDate dataVenc = LocalDate.of(ano, mes, dia).plusDays(30);
		
		return dataVenc;
	}


}
