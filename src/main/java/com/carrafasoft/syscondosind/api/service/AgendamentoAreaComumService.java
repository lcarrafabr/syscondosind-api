package com.carrafasoft.syscondosind.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.enums.FormaPagamento;
import com.carrafasoft.syscondosind.api.enums.StatusAgendamento;
import com.carrafasoft.syscondosind.api.enums.StatusSituacao;
import com.carrafasoft.syscondosind.api.enums.TipoNatureza;
import com.carrafasoft.syscondosind.api.model.AgendamentoAreaComum;
import com.carrafasoft.syscondosind.api.model.AreasComuns;
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.AgendamentoAreaComumRepository;
import com.carrafasoft.syscondosind.api.repository.AreasComunsRepository;

@Service
public class AgendamentoAreaComumService {
	
	
	@Autowired
	private AgendamentoAreaComumRepository agendamentoAreaComumRepository;
	
	@Autowired LancamentosService lancamentoService;
	
	@Autowired 
	private AreasComunsRepository areacomumAreasComunsRepository;
	
	
	public AgendamentoAreaComum cadastraAgendamentoECriaLancamento(AgendamentoAreaComum agendamento, BigDecimal valorLocacao, AreasComuns areaComumSalva) {
		
		
		Lancamentos lancamentoSalvo = cadastrarLancamentoQuandoAlugarAreaComum(valorLocacao, agendamento, areaComumSalva);
		
		agendamento.setHashLancamento(lancamentoSalvo.getChavePesquisa());
		
		AgendamentoAreaComum agendamentoSalvo = agendamentoAreaComumRepository.save(agendamento);
		
		
		return agendamentoSalvo;
	}
	
	
	private Lancamentos cadastrarLancamentoQuandoAlugarAreaComum(BigDecimal valorAluguel, AgendamentoAreaComum agendamento, AreasComuns areaComumSalva) {
		
		Lancamentos lancNovo = new Lancamentos();
		String descricao = "Locação da área comum: " + agendamento.getTituloAgendamento().trim().toUpperCase();
		
		lancNovo.setTipoNatureza(TipoNatureza.RECEBER);
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
		
		Lancamentos lancamentoSalvo = lancamentoService.cadastrarLancamentoQuandoAlugarAreaComum(lancNovo);
		
		return lancamentoSalvo;
		
	}
	
		public Boolean verificaDataDisponivel(AgendamentoAreaComum agendamento) {
		
			Boolean retorno = false;
			
			LocalDateTime dataInicio = agendamento.getDataInicioAgendamento(); 
			LocalDateTime dataFim = agendamento.getDataFimAgendamento();
			Long codigoAreaComum = agendamento.getAreaComum().getAreaComumId();
			
			LocalDateTime verificaDataInicio = agendamentoAreaComumRepository.buscaDataInicioLivre(dataInicio, codigoAreaComum);
			LocalDateTime verificaDataFim = agendamentoAreaComumRepository.buscaDataFimLivre(dataFim, codigoAreaComum);
			
			if(verificaDataInicio == null && verificaDataFim == null) {
				
				retorno = true;
			}
		
		return retorno;
	}
		
		
		public Boolean diasAntecedenciaParaAgendar(AgendamentoAreaComum agendamento, AreasComuns areacomum) {

			boolean valido = false;
			Integer dias = areacomum.getDiasAntecedencia();
			LocalDateTime dataAgendamentoInicio = agendamento.getDataInicioAgendamento();
			LocalDateTime horaAgora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));			
			
			if(horaAgora.isBefore(dataAgendamentoInicio.minusDays(dias))) {

				valido = true;
			}
			
			if(horaAgora.isEqual(dataAgendamentoInicio.minusDays(dias))) {

				valido = true;
			}
			
			if(horaAgora.isAfter(dataAgendamentoInicio.minusDays(dias))) {

				valido = false;
			}
			
			return valido;
		}
		
		public Boolean verificaDataAgendamentoIniEFim(AgendamentoAreaComum agendamento) {
			
			LocalDateTime dataInicio = agendamento.getDataInicioAgendamento();
			LocalDateTime dataFim = agendamento.getDataFimAgendamento();
			Boolean verifica = false;
			
			if(dataInicio.isBefore(dataFim)) {
				
				verifica = true;
			}
			
			return verifica;
		}
		
		public void removerrAgendamento(Long codigo) {
			
			AgendamentoAreaComum agendamentoSalvo = buscaPorId(codigo);
			AreasComuns areaComumSalva = areacomumAreasComunsRepository.findById(agendamentoSalvo.getAreaComum().getAreaComumId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
			
			boolean valido = false;
			Boolean permiteRetirarValor = areaComumSalva.getPermiteRetirarValor();
			Integer diasParaCancelar = areaComumSalva.getDiasParaCancelar();
			LocalDateTime horaAgora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
			LocalDateTime dataAgendamentoInicio = agendamentoSalvo.getDataInicioAgendamento();
			
			if(horaAgora.isBefore(dataAgendamentoInicio.minusDays(diasParaCancelar))) {

				valido = true;
			}
			
			if(horaAgora.isEqual(dataAgendamentoInicio.minusDays(diasParaCancelar))) {

				valido = true;
			}
			
			if(horaAgora.isAfter(dataAgendamentoInicio.minusDays(diasParaCancelar))) {

				valido = false;
			}
			
			if(valido) {
				
				if(permiteRetirarValor) {
					
					lancamentoService.atualizaStatusLancamento(agendamentoSalvo.getHashLancamento());
				}
				
				//Cancelar agendamento
				agendamentoAreaComumRepository.deleteById(codigo);
			}
			
			
		}
		
		public AgendamentoAreaComum atualizaStatus(AgendamentoAreaComum agendamento, Long codigo) {
			
			StatusAgendamento status = agendamento.getStatusAgendamento();
			AgendamentoAreaComum agendamentoSalvo = buscaPorId(codigo);
			AreasComuns areaComumSalva = areacomumAreasComunsRepository.findById(agendamentoSalvo.getAreaComum().getAreaComumId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
			
			Boolean permiteRetirarValor = areaComumSalva.getPermiteRetirarValor();
			
			if(status.equals(StatusAgendamento.CANCELADO)) {
				if(permiteRetirarValor) {
					
					lancamentoService.atualizaStatusLancamento(agendamentoSalvo.getHashLancamento());
				}
			}
			
			BeanUtils.copyProperties(agendamento, agendamentoSalvo, "agendamentoAreaComumId");
			
			return agendamentoAreaComumRepository.save(agendamentoSalvo);
		}
	
	private AgendamentoAreaComum buscaPorId(Long codigo) {
		
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
