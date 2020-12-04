package com.carrafasoft.syscondosind.api.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpRequest;
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

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.bancos.Bradesco;
import br.com.caelum.stella.boleto.bancos.Caixa;
import br.com.caelum.stella.boleto.bancos.HSBC;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.bancos.Santander;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;

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
	
	
	/**Para gerar em HTML usar o paramentro HttpServletRequest request tbm*/
	public GeradorDeBoletoHTML teste(HttpServletResponse response) {
		
		Datas datas = Datas.novasDatas()
                .comDocumento(10, 12, 2020)
                .comProcessamento(15, 12, 2020) //A data que gerou
                .comVencimento(10, 1, 2021);
		
		Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro("Rua custódio paiva, 205")  
        		.comBairro("Jd São paulo (Zona leste)")  
        		.comCep("08461-530")  
        		.comCidade("São Paulo")  
        		.comUf("SP");  
		
		 //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()  
                .comNomeBeneficiario("Cup Lover")  
                .comAgencia("1824").comDigitoAgencia("013")  
                .comCodigoBeneficiario("00012345")  
                .comDigitoCodigoBeneficiario("6")  
                .comNumeroConvenio("221")  
                .comCarteira("1")  // => Itaú não usa carteira - pg 39 do DOC Itaú // para o santander deve ter 3 numeros // CEF carteira é igual a 1 - 2 ou 24
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("623168") //=> Numero sequencial que não pode ser repetido
                .comDigitoNossoNumero("5");  // =>> para o Itau
        
        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro("Rua custódio paiva, 205 apto 46 torre 09")  
        		.comBairro("Jd São paulo (Zona leste)")  
        		.comCep("08461-530")  
        		.comCidade("São Paulo")  
        		.comUf("SP");  
        
      //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome("Debora da costa S C Benfica")  
                .comDocumento("111.222.333-12")
                .comEndereco(enderecoPagador);
        
        //Banco banco = new BancoDoBrasil(); 
        //Banco banco = new Itau();
       //Banco banco = new Santander(); //Dando Erro
        Banco banco = new Caixa();
        
        Boleto boleto = Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto("0.01")  
                .comNumeroDoDocumento("3471")  
                .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")  
                .comLocaisDePagamento("ATE O VENCIMENTO PAGUE PREFERENCIALMENTE NO ITAU", 
                		"APOS O VENCIMENTO PAGUE SOMENTE NO ITAU");  
        
       // GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        
        GeradorDeBoletoHTML gerador = new GeradorDeBoletoHTML(boleto);
        try {
			gerador.geraPDF(response.getOutputStream());
        	//gerador.geraHTML(response.getWriter(), request); ==>> Para gerar em HTML mas as imagens não funcionam
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
     // Para gerar um boleto em PNG  
        //gerador.geraPNG("BancoDoBrasil.png");
        
        return gerador;
	}


}
