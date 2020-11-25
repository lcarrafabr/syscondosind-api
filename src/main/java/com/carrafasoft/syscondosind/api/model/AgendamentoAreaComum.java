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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carrafasoft.syscondosind.api.enums.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "agendamento_area_comum")
public class AgendamentoAreaComum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agendamento_area_comum_id")
	private Long agendamentoAreaComumId;

	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "titulo_agendamento", length = 100)
	private String tituloAgendamento;

	@Column(name = "data_criacao_agendamento", insertable = true, updatable = false)
	private LocalDateTime dataCriacaoAgendamento;

	@NotNull
	@Column(name = "data_inicio_agendamento")
	private LocalDateTime dataInicioAgendamento;

	@NotNull
	@Column(name = "data_fim_agendamento")
	private LocalDateTime dataFimAgendamento;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status_agendamento", length = 20)
	private StatusAgendamento statusAgendamento;

	@ManyToOne
	@JsonIgnoreProperties("condominio")
	@JoinColumn(name = "lista_id")
	private ListaConvidados listaCOnvidados;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoaMorador;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "area_comum_id")
	private AreasComuns areaComum;

	@Column(name = "hash_lancamento", length = 100)
	private String hashLancamento;

	public Long getAgendamentoAreaComumId() {
		return agendamentoAreaComumId;
	}

	public void setAgendamentoAreaComumId(Long agendamentoAreaComumId) {
		this.agendamentoAreaComumId = agendamentoAreaComumId;
	}

	public String getTituloAgendamento() {
		return tituloAgendamento;
	}

	public void setTituloAgendamento(String tituloAgendamento) {
		this.tituloAgendamento = tituloAgendamento;
	}

	public LocalDateTime getDataCriacaoAgendamento() {
		return dataCriacaoAgendamento;
	}

	public void setDataCriacaoAgendamento(LocalDateTime dataCriacaoAgendamento) {
		this.dataCriacaoAgendamento = dataCriacaoAgendamento;
	}

	public LocalDateTime getDataInicioAgendamento() {
		return dataInicioAgendamento;
	}

	public void setDataInicioAgendamento(LocalDateTime dataInicioAgendamento) {
		this.dataInicioAgendamento = dataInicioAgendamento;
	}

	public LocalDateTime getDataFimAgendamento() {
		return dataFimAgendamento;
	}

	public void setDataFimAgendamento(LocalDateTime dataFimAgendamento) {
		this.dataFimAgendamento = dataFimAgendamento;
	}

	public StatusAgendamento getStatusAgendamento() {
		return statusAgendamento;
	}

	public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}

	public ListaConvidados getListaCOnvidados() {
		return listaCOnvidados;
	}

	public void setListaCOnvidados(ListaConvidados listaCOnvidados) {
		this.listaCOnvidados = listaCOnvidados;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Pessoas getPessoaMorador() {
		return pessoaMorador;
	}

	public void setPessoaMorador(Pessoas pessoaMorador) {
		this.pessoaMorador = pessoaMorador;
	}

	public AreasComuns getAreaComum() {
		return areaComum;
	}

	public void setAreaComum(AreasComuns areaComum) {
		this.areaComum = areaComum;
	}

	public String getHashLancamento() {
		return hashLancamento;
	}

	public void setHashLancamento(String hashLancamento) {
		this.hashLancamento = hashLancamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agendamentoAreaComumId == null) ? 0 : agendamentoAreaComumId.hashCode());
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
		AgendamentoAreaComum other = (AgendamentoAreaComum) obj;
		if (agendamentoAreaComumId == null) {
			if (other.agendamentoAreaComumId != null)
				return false;
		} else if (!agendamentoAreaComumId.equals(other.agendamentoAreaComumId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		tituloAgendamento = tituloAgendamento.toUpperCase().trim();
		dataCriacaoAgendamento = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

	@PreUpdate
	public void aoAtualizar() {

		tituloAgendamento = tituloAgendamento.toUpperCase().trim();
	}

}
