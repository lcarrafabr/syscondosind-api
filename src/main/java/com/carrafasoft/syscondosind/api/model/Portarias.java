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

import com.carrafasoft.syscondosind.api.enums.TipoVisita;

@Entity
@Table(name = "portaria")
public class Portarias {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "controle_portaria_id")
	private Long controlePortariaId;

	@NotNull
	@Size(min = 3)
	@Column(name = "nome_visitante", length = 200)
	private String nomeVisitante;

	@Column(name = "cpf_rg", length = 20)
	private String cpfRg;

	@Column(length = 255)
	private String empresa;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoVisita tipoVisita;

	@Column(length = 50)
	private String veiculo;

	@Column(length = 8)
	private String placa;

	@NotNull
	@Column(name = "placa_mercosul")
	private Boolean placaMercosul;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@Column(name = "data_entrada", updatable = false)
	private LocalDateTime dataEntrada;

	@Column(name = "data_saida", insertable = false)
	private LocalDateTime dataSaida;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private UsuarioSistema usuario;

	@ManyToOne
	@JoinColumn(name = "morador_id")
	private Moradores morador;

	public Long getControlePortariaId() {
		return controlePortariaId;
	}

	public void setControlePortariaId(Long controlePortariaId) {
		this.controlePortariaId = controlePortariaId;
	}

	public String getNomeVisitante() {
		return nomeVisitante;
	}

	public void setNomeVisitante(String nomeVisitante) {
		this.nomeVisitante = nomeVisitante;
	}

	public String getCpfRg() {
		return cpfRg;
	}

	public void setCpfRg(String cpfRg) {
		this.cpfRg = cpfRg;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public TipoVisita getTipoVisita() {
		return tipoVisita;
	}

	public void setTipoVisita(TipoVisita tipoVisita) {
		this.tipoVisita = tipoVisita;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Boolean getPlacaMercosul() {
		return placaMercosul;
	}

	public void setPlacaMercosul(Boolean placaMercosul) {
		this.placaMercosul = placaMercosul;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Moradores getMorador() {
		return morador;
	}

	public void setMorador(Moradores morador) {
		this.morador = morador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((controlePortariaId == null) ? 0 : controlePortariaId.hashCode());
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
		Portarias other = (Portarias) obj;
		if (controlePortariaId == null) {
			if (other.controlePortariaId != null)
				return false;
		} else if (!controlePortariaId.equals(other.controlePortariaId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {

		dataEntrada = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

	@PreUpdate
	public void aoAtualizar() {

		dataSaida = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}

}
