package com.carrafasoft.syscondosind.api.model.interfaces;

public interface SolicitacaoCompraInterface {
	
	public abstract void gerarCodigoSolicitacao(Long idAtual);
	
	public abstract void cancelarSolicitacao();

}
