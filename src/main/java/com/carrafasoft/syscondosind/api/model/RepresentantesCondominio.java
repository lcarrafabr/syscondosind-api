package com.carrafasoft.syscondosind.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.TipoMandato;

@Entity
@Table(name = "representantes_condominio")
public class RepresentantesCondominio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "representante_condominio_id")
	private Long representanteCondominioId;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_mandato", length = 15)
	private TipoMandato tipoMandato;

	@NotNull
	@Column(name = "morador_do_condominio")
	private Boolean moradorDoCondominio;

	@NotNull
	@Column(name = "data_inicio_mandato")
	private LocalDate dataInicioMandato;

	@Column(name = "data_fim_mandato")
	private LocalDate dataFimMandato;

	//@Lob
	@Column(columnDefinition="TEXT")
	private String descricao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoas pessoa;

	public Long getRepresentanteCondominioId() {
		return representanteCondominioId;
	}

	public void setRepresentanteCondominioId(Long representanteCondominioId) {
		this.representanteCondominioId = representanteCondominioId;
	}

	public TipoMandato getTipoMandato() {
		return tipoMandato;
	}

	public void setTipoMandato(TipoMandato tipoMandato) {
		this.tipoMandato = tipoMandato;
	}

	public Boolean getMoradorDoCondominio() {
		return moradorDoCondominio;
	}

	public void setMoradorDoCondominio(Boolean moradorDoCondominio) {
		this.moradorDoCondominio = moradorDoCondominio;
	}

	public LocalDate getDataInicioMandato() {
		return dataInicioMandato;
	}

	public void setDataInicioMandato(LocalDate dataInicioMandato) {
		this.dataInicioMandato = dataInicioMandato;
	}

	public LocalDate getDataFimMandato() {
		return dataFimMandato;
	}

	public void setDataFimMandato(LocalDate dataFimMandato) {
		this.dataFimMandato = dataFimMandato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ((representanteCondominioId == null) ? 0 : representanteCondominioId.hashCode());
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
		RepresentantesCondominio other = (RepresentantesCondominio) obj;
		if (representanteCondominioId == null) {
			if (other.representanteCondominioId != null)
				return false;
		} else if (!representanteCondominioId.equals(other.representanteCondominioId))
			return false;
		return true;
	}

}
