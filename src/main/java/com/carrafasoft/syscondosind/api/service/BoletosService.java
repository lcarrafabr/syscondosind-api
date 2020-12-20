package com.carrafasoft.syscondosind.api.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carrafasoft.syscondosind.api.enums.BancoEnum;
import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Boletos;
import com.carrafasoft.syscondosind.api.model.ConfigBoletos;
import com.carrafasoft.syscondosind.api.model.ModeloBoletos;
import com.carrafasoft.syscondosind.api.repository.BoletosRepository;
import com.carrafasoft.syscondosind.api.repository.MoradorRepository;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.bancos.Bradesco;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.bancos.Santander;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;

@Service
public class BoletosService {
	
	@Autowired
	private BoletosRepository boletosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ConfigBoletoService configBoletoService;
	
	@Autowired
	private ModeloBoletoService modeloBoletoService;
	
	@Autowired
	private LancamentosService lancamentosService;
	
	@Autowired
	private MoradorRepository moradorRepository;
	
	
	public ResponseEntity<Boletos> cadastrarBoleto(Boletos boleto, HttpServletResponse response) {
		
		Boletos novoBoleto = new Boletos();
		ConfigBoletos configAtual = dadosConfigBoleto(boleto);
		
		Long ultimoNumeroDocConfigBoleto = configAtual.getUltimoNumeroDoc();
		
		Long ultimoNumeroDoc = configAtual.getUltimoNumeroDoc();
		
		
		if(ultimoNumeroDocConfigBoleto == ultimoNumeroDoc) {
			
			novoBoleto.setCondominio(boleto.getCondominio());
			novoBoleto.setModeloBoleto(boleto.getModeloBoleto());
			novoBoleto.setMorador(boleto.getMorador());
			novoBoleto.setDataDocumento(boleto.getDataDocumento());
			novoBoleto.setDataProcessamento(boleto.getDataProcessamento());
			novoBoleto.setDataVencimento(boleto.getDataVencimento());
			novoBoleto.setValor(boleto.getValor());
			novoBoleto.setNumeroDocumento(ultimoNumeroDoc + 1);
			novoBoleto.setNossoNumero(configAtual.getNossoNumeroAtual() + 1);
			
			Boletos boletoSalvo = boletosRepository.save(novoBoleto);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, boletoSalvo.getBoletoId()));
			
			configAtual.setNossoNumeroAtual(configAtual.getNossoNumeroAtual() + 1);
			configAtual.setUltimoNumeroDoc(ultimoNumeroDoc + 1);
			
			atualizarConfig(configAtual);
			
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
	}
	
	/***************************************************************************************************************************************************/
	
	public Boletos atualizarBoleto(Long codigo, Boletos boleto) {
		
		Boletos boletoSalvo = buscaPorId(codigo);
		
		BeanUtils.copyProperties(boleto, boletoSalvo, "boletoId");
		return boletosRepository.save(boletoSalvo);
	}
	
	/***************************************************************************************************************************************************/
	
	
	/**Para gerar em HTML usar o paramentro HttpServletRequest request tbm*/
	public GeradorDeBoletoHTML imprimirBoleto(Long codigo, HttpServletResponse response) {
		
		Boletos boletoSalvo = buscaPorId(codigo);
		
		Datas datas = Datas.novasDatas()
                .comDocumento(
                		boletoSalvo.getDataDocumento().getDayOfMonth(), //DIA
                		boletoSalvo.getDataDocumento().getMonthValue(), //MÊS
                		boletoSalvo.getDataDocumento().getYear()		//ANO
                		)
                
                .comProcessamento(
                		boletoSalvo.getDataProcessamento().getDayOfMonth(), //DIA
                		boletoSalvo.getDataProcessamento().getMonthValue(), //MÊS
                		boletoSalvo.getDataProcessamento().getYear()		//ANO
                		) //A data que gerou
                
                .comVencimento(
                		boletoSalvo.getDataVencimento().getDayOfMonth(), 
                		boletoSalvo.getDataVencimento().getMonthValue(), 
                		boletoSalvo.getDataVencimento().getYear()
                		);
		
		
		String logradouro = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getLogradouro();
		String numero = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getNumero();
		String bairro = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getBairro();
		String cep = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getCep();
		String cidade = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getCidade();
		String estado = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getEstado();
		String complemento = boletoSalvo.getModeloBoleto().getBeneficiario().getEnderecos().getComplemento();
		
		if(complemento.equals(null)) {
			complemento = "";
		}
		
		Endereco enderecoBeneficiario = Endereco.novoEndereco()
        		.comLogradouro(logradouro.concat(", ").concat(numero).concat(" - ").concat(complemento))  
        		.comBairro(bairro)  
        		.comCep(cep)  
        		.comCidade(cidade)  
        		.comUf(estado);  
		
		
		/**FILTROS**/
		String carteira = "";
		String digitoNossoNumero = "";
		if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.ITAU)) {
			digitoNossoNumero = boletoSalvo.getModeloBoleto().getConfigBoletos().getDigitoNossoNumero();
		}
		if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.BB)) {
			carteira = boletoSalvo.getModeloBoleto().getCarteira();
		}
		if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.SANTANDER)) {
			carteira = boletoSalvo.getModeloBoleto().getCarteira();
		}
		
		
		 //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()  
                .comNomeBeneficiario(boletoSalvo.getModeloBoleto().getBeneficiario().getNomeBeneficiario())  
                .comAgencia(boletoSalvo.getModeloBoleto().getContaBancaria().getAgencia())
                .comDigitoAgencia("4")  // TODO atualizar contas bancarias e incluir o digito
                .comCodigoBeneficiario(boletoSalvo.getModeloBoleto().getBeneficiario().getCodigoBeneficiario())  
                .comDigitoCodigoBeneficiario(boletoSalvo.getModeloBoleto().getBeneficiario().getDigitoBeneficiario())  
                .comNumeroConvenio(boletoSalvo.getModeloBoleto().getBeneficiario().getNumeroConvenio())  
                .comCarteira(carteira)  // => Itaú não usa carteira - pg 39 do DOC Itaú // para o santander deve ter 3 numeros
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero(boletoSalvo.getNossoNumero().toString()) //=> Numero sequencial que não pode ser repetido
                .comDigitoNossoNumero(digitoNossoNumero);  // =>> para o Itau
		
      
        
        
        String logradouroPagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getLogradouro();
		String numeroPagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getNumero();
		String bairroPagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getBairro();
		String cepPagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getCep();
		String cidadePagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getCidade();
		String estadoPagador = boletoSalvo.getMorador().getPessoa().getCondominio().getEnderecoCondominio().getEstado();
		String complementoPagador = "AP: " + boletoSalvo.getMorador().getApartamento().getNomeApartamento() 
				+ " Bloco: " + boletoSalvo.getMorador().getTorreBloco().getNomeBloco();
        
        
        Endereco enderecoPagador = Endereco.novoEndereco()
        		.comLogradouro(logradouroPagador.concat(", ").concat(numeroPagador).concat(" ").concat(complementoPagador))  
        		.comBairro(bairroPagador)  
        		.comCep(cepPagador)  
        		.comCidade(cidadePagador)  
        		.comUf(estadoPagador);  
        
      //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()  
                .comNome(boletoSalvo.getMorador().getPessoa().getNomePessoa()	)  
                .comDocumento(boletoSalvo.getMorador().getCpf())
                .comEndereco(enderecoPagador);
        
        //Banco banco = new BancoDoBrasil(); 
        //Banco banco = new Itau();
        //Banco banco = new Santander(); //Dando Erro
        
        Banco banco = null;
        
        if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.ITAU)) {
        	banco = new Itau();
        }
        if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.BB)) {
        	banco = new BancoDoBrasil();
        }
        if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.BRADESCO)) {
        	banco = new Bradesco();
        }
        if(boletoSalvo.getModeloBoleto().getBancoEnum().equals(BancoEnum.SANTANDER)) {
        	banco = new Santander();
        }
        
        
        Boleto boleto = Boleto.novoBoleto()  
                .comBanco(banco)  
                .comDatas(datas)  
                .comBeneficiario(beneficiario)  
                .comPagador(pagador)  
                .comValorBoleto(boletoSalvo.getValor())  
                .comNumeroDoDocumento(boletoSalvo.getNumeroDocumento().toString())  
                .comInstrucoes(
                		boletoSalvo.getModeloBoleto().getInstruncao01(), 
                		boletoSalvo.getModeloBoleto().getInstruncao02(), 
                		boletoSalvo.getModeloBoleto().getInstruncao03(), 
                		boletoSalvo.getModeloBoleto().getInstruncao04(), 
                		boletoSalvo.getModeloBoleto().getInstruncao05()
                		)  
                .comLocaisDePagamento(
                		boletoSalvo.getModeloBoleto().getLocal01(), 
                		boletoSalvo.getModeloBoleto().getLocal02()
                		);  
        
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

	
	
	/**
	 * @return *************************************************************************************************************************************************/
	
	public ResponseEntity<List<Boletos>> gerarLancamentosAReceber(String dataIni, String dataFim, String categoriaId, String centroCustoId, String descricao) {
		
		ResponseEntity<List<Boletos>> httpstatus = new ResponseEntity<List<Boletos>>(HttpStatus.NO_CONTENT);
		
		List<Boletos> boletosList = boletosRepository.listarBoletosSemLancamentoPorData(
				FuncoesUtils.converterStringParaLocalDate(dataIni), 
				FuncoesUtils.converterStringParaLocalDate(dataFim)
				);
		List<Boletos> boletoSalvo = new ArrayList<Boletos>();
		
		if(!boletosList.isEmpty()) {

			Long categoriaId2 = Long.parseLong(categoriaId);
			Long centroCustoId2 = Long.parseLong(centroCustoId);
			
			Boolean salvo = lancamentosService.gerarLancamentoAReceberDosBoletos(boletosList, centroCustoId2, categoriaId2, descricao);
			
			if(salvo) {

				for (Boletos boletos : boletosList) {
					
					boletoSalvo.add(atualizarStatusLancGerado(boletos.getBoletoId(), true));
				}
			}
			httpstatus = ResponseEntity.ok(boletoSalvo);
		}

		return httpstatus;
	}
	
	/*************************************************************************************************************************************************/
	/**---------------------------------------------------------------------------------------------------------------------------------------------------------------**/
	
	
	public void gerarMensalidadeCondominio(String dataIni, String dataFim, String valorParcelaTotal) {
		
		BigDecimal valorTotal = new BigDecimal(valorParcelaTotal);
		Integer qtdMorador = moradorRepository.quatidadeDeMoradoresparaGerarBoleto();
		
		BigDecimal qtdMoradorParaGerarBoleto = new BigDecimal(qtdMorador);
		
		BigDecimal mensalidade = valorTotal.divide(qtdMoradorParaGerarBoleto);
		
		System.out.println("mensalidade: " + mensalidade);
		
		
	}
	
	
	/**---------------------------------------------------------------------------------------------------------------------------------------------------------------**/
	/***************************************************************************************************************************************************/
	
	
	public List<BigDecimal> buscaValorParaArredondarMensalidade(String valorMensalidade, String valorAlvo, String qtdMoradores) {
		
		BigDecimal valormens = new BigDecimal(valorMensalidade);
		BigDecimal valorAlvo2 = new BigDecimal(valorAlvo);
		BigDecimal bd = new BigDecimal(qtdMoradores);
		BigDecimal adicao = new BigDecimal("0.01");
		Boolean finalizar = true;
		
		BigDecimal resto = new BigDecimal(-1);
		
		List<BigDecimal> opcoes = new ArrayList<BigDecimal>();
		
		while(finalizar) {
			
			resto = valormens.remainder(bd);
			
			if(resto.equals(new BigDecimal("0.00"))) {
				
				opcoes.add(valormens);
				
			}
			valormens = valormens.add(adicao);
			
			if(valormens.compareTo(valorAlvo2) == 1) {
				finalizar = false;
			}

		}

		return opcoes;
	}
	
	
	/***************************************************************************************************************************************************/
	
	
	private Boletos atualizarStatusLancGerado(Long codigo, Boolean ativo) {
		
		Boletos boletoSalvo = buscaPorId(codigo);
		
		boletoSalvo.setLancamentoGerado(ativo);
		
		boletosRepository.save(boletoSalvo);
		return boletoSalvo;
	}
	
	
	/***************************************************************************************************************************************************/
	
	private Boletos buscaPorId(Long codigo) {
		
		Boletos boletoSalvo = boletosRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return boletoSalvo;
	}
	
	/***************************************************************************************************************************************************/
	
	private ConfigBoletos dadosConfigBoleto(Boletos boleto) {
		
		ModeloBoletos modeloBoletoSalvo = modeloBoletoService.buscaPorId(boleto.getModeloBoleto().getModeloBoletoId());
		
		ConfigBoletos configSalva = configBoletoService.buscaPorId(modeloBoletoSalvo.getConfigBoletos().getConfigBoletoId());
		return configSalva;
	}
	
	/***************************************************************************************************************************************************/
	
	private void atualizarConfig(ConfigBoletos config) {
		
		Long codigo = config.getConfigBoletoId();
		
		configBoletoService.atualizarConfig(config, codigo);
	}

}
