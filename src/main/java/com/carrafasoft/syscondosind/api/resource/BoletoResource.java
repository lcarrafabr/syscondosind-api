package com.carrafasoft.syscondosind.api.resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrafasoft.syscondosind.api.event.RecursoCriadoEvent;
import com.carrafasoft.syscondosind.api.model.Boletos;
import com.carrafasoft.syscondosind.api.model.Lancamentos;
import com.carrafasoft.syscondosind.api.repository.BoletosRepository;
import com.carrafasoft.syscondosind.api.service.BoletosService;
import com.carrafasoft.syscondosind.api.utils.FuncoesUtils;

import br.com.caelum.stella.boleto.transformer.GeradorDeBoletoHTML;

@RestController
@RequestMapping("/boletos")
public class BoletoResource {
	
	@Autowired
	private BoletosRepository boletosRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private BoletosService boletoService;
	
	@GetMapping
	public List<Boletos> listarTodos() {
		 
		return boletosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Boletos> cadastrarBoleto(@Valid @RequestBody Boletos boleto, HttpServletResponse response) {
		
		return boletoService.cadastrarBoleto(boleto, response);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Boletos> buscaPorId(@PathVariable Long codigo) {
		
		Optional<Boletos> boletoSalvo = boletosRepository.findById(codigo);
		
		return boletoSalvo.isPresent() ? ResponseEntity.ok(boletoSalvo.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void removerBoleto(@PathVariable Long codigo) {
		
		boletosRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Boletos> atualizarBoleto(@PathVariable Long codigo, @Valid @RequestBody Boletos boleto) {
		
		Boletos boletoAtualizado = boletoService.atualizarBoleto(codigo, boleto);
		
		return ResponseEntity.ok(boletoAtualizado);
	}
	
	@GetMapping("/imprimir-boleto/{codigo}")
	public GeradorDeBoletoHTML imprimirBoleto(@PathVariable Long codigo, HttpServletResponse response) {
		
		GeradorDeBoletoHTML boleto = boletoService.imprimirBoleto(codigo, response);
		
		
		return boleto;
	}
	
	/********************************************************************************************************************************************************************/
	
	@GetMapping("/listar-boletos-sem-lancamentos")
	public List<Boletos> listarTodosBoletosSemLancamentoGerado() {
		
		return boletosRepository.listarTodosBoletosSemLancamentoGerado();
	}
	
	@GetMapping("/listar-boletos-sem-lancamentos-por-data")
	public List<Boletos> listarBoletosSemLancamentoGeradoPorData(@RequestParam("dataIni") String dataIni, @RequestParam("dataFim") String dataFim) {
		
		return boletosRepository.listarBoletosSemLancamentoPorData(
				FuncoesUtils.converterStringParaLocalDate(dataIni), 
				FuncoesUtils.converterStringParaLocalDate(dataFim)
				);
	}
	
	@GetMapping("/gerar-lancamentos-a-receber")
	public ResponseEntity<List<Boletos>> gerarLancamentosAReceber(@RequestParam("dataIni") String dataIni, 
										 @RequestParam("dataFim") String dataFim,
										 @RequestParam("categoriaID") String categoriaId,
										 @RequestParam("centroCustoId") String centroCustoId,
										 @RequestParam("descricao") String descricao) {
		
		ResponseEntity<List<Boletos>> boletoSalvo = boletoService.gerarLancamentosAReceber(dataIni, dataFim, categoriaId, centroCustoId, descricao);
		
		return boletoSalvo;
	}
	
	
	/**---------------------------------------------------------------------------------------------------------------------------------------------------------------**/
	
	@GetMapping("/valor-total-a-pagar-por-periodo")
	public BigDecimal retornarValorTotalAPagarNoMes(@RequestParam("dataIni") String dataIni, @RequestParam("dataFim") String dataFim) {
		
		return boletosRepository.valorTotalAPagarPorPeriodo(
				FuncoesUtils.converterStringParaLocalDate(dataIni), 
				FuncoesUtils.converterStringParaLocalDate(dataFim)
				);
	}
	
	@GetMapping("/valor-a-pagar-periodo-morador")
	public BigDecimal retornarValoresApagarPorPeriodo(@RequestParam("dataIni") String dataIni, @RequestParam("dataFim") String dataFim) {
		
		return boletosRepository.gerarValoresCondominioAReceber(
				FuncoesUtils.converterStringParaLocalDate(dataIni), 
				FuncoesUtils.converterStringParaLocalDate(dataFim)
				);
	}
	
	/**---------------------------------------------------------------------------------------------------------------------------------------------------------------**/
	
	@GetMapping("/gerar-mensalidade-condominio")
	public ResponseEntity<Boletos> gerarParcelamentoCondominio(@RequestParam("valorParcelaTotal") String valorParcelaTotal,
												 @RequestParam("dataVencimento") String dataVencimento,
												 @RequestParam("modeloBoletoId") String modeloBoletoId,
												 @RequestParam("condominioId") String condominioId,
												 HttpServletResponse response) {
		
		
		
		
		return boletoService.gerarMensalidadeCondominio(valorParcelaTotal, dataVencimento, modeloBoletoId, condominioId, response);
	}
	
	/**--------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	
	
	@GetMapping("/lista_valores-para-arredondar-mensalidade")
	public List<BigDecimal> buscaValorParaArredondarMensalidade(@RequestParam("valorMensalidade") String valorMensalidade, 
																@RequestParam("valorAlvo") String valorAlvo, 
																@RequestParam("qtdMoradores") String qtdMoradores) {
		
		List<BigDecimal> opcaoValores = boletoService.buscaValorParaArredondarMensalidade(valorMensalidade, valorAlvo, qtdMoradores);
		
		return opcaoValores;
		
	}
	
	/*******************************************************************************************************************************************************************/

}
