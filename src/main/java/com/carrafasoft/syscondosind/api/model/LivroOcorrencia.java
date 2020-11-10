package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.StatusOcorrencia;

@Entity
@Table(name = "livro_ocorrencia")
public class LivroOcorrencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ocorrencia_id")
	private Long ocorrenciaId;

	@Column(name = "data_ocorrencia", updatable = false)
	private LocalDateTime dataOcorrencia;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_ocorrencia", length = 10)
	private StatusOcorrencia statusOcorrencia;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "data_fechamento")
	private LocalDateTime dataFechamento;

	@Column(columnDefinition = "TEXT", name = "descricao_final")
	private String descricaoFinal;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	public Long getOcorrenciaId() {
		return ocorrenciaId;
	}

	public void setOcorrenciaId(Long ocorrenciaId) {
		this.ocorrenciaId = ocorrenciaId;
	}

	public LocalDateTime getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public StatusOcorrencia getStatusOcorrencia() {
		return statusOcorrencia;
	}

	public void setStatusOcorrencia(StatusOcorrencia statusOcorrencia) {
		this.statusOcorrencia = statusOcorrencia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getDescricaoFinal() {
		return descricaoFinal;
	}

	public void setDescricaoFinal(String descricaoFinal) {
		this.descricaoFinal = descricaoFinal;
	}

	public Pessoas getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoas pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ocorrenciaId == null) ? 0 : ocorrenciaId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LivroOcorrencia other = (LivroOcorrencia) obj;
		if (ocorrenciaId == null) {
			if (other.ocorrenciaId != null)
				return false;
		} else if (!ocorrenciaId.equals(other.ocorrenciaId))
			return false;
		return true;
	}
	
	@PrePersist
	public void aoCadastrarOcorrencia() {
		
		dataOcorrencia = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		statusOcorrencia = StatusOcorrencia.ABERTO;
	}

}
