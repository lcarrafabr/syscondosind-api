package com.carrafasoft.syscondosind.api.model;

import java.math.BigDecimal;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "areas_comuns")
public class AreasComuns {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "area_comum_id")
	private Long areaComumId;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "nome_area", length = 100)
	private String nomeArea;

	@Column(columnDefinition = "TEXT")
	private String descricao;

	@NotNull
	@Column(name = "valor_locacao")
	private BigDecimal valorLocacao;

	@NotNull
	@Column(name = "dias_antecedencia")
	private Integer diasAntecedencia;

	@NotNull
	@Column(name = "usuario_inadimplente")
	private Boolean usuarioInadimplente;

	@NotNull
	@Column(name = "liberar_para_agendar")
	private Boolean liberarParaAgendar;

	@NotNull
	@Column(name = "permite_retirar_valor")
	private Boolean permiteRetirarValor;

	@Column(name = "termo_de_uso", columnDefinition = "TEXT")
	private String termoDeUso;

	@NotNull
	@Column(name = "dias_para_cancelar")
	private Integer diasParaCancelar;

	@NotNull
	@Column(name = "hora_minuto_p_cancelar")
	private LocalTime horasMinutosPCancelar;

	@ManyToOne
	@JsonIgnoreProperties("enderecoCondominio")
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@ManyToOne
	@JsonIgnoreProperties({"condominio", "banco"})
	@JoinColumn(name = "conta_bancaria_id")
	private ContasBancarias contaBancaria;

	@ManyToOne
	@JoinColumn(name = "centro_custo_id")
	private CentroDeCustos centroCusto;

	@ManyToOne
	@JoinColumn(name = "categoria_conta_id")
	private CategoriasContaPR categoriaContas;

	public Long getAreaComumId() {
		return areaComumId;
	}

	public void setAreaComumId(Long areaComumId) {
		this.areaComumId = areaComumId;
	}

	public String getNomeArea() {
		return nomeArea;
	}

	public void setNomeArea(String nomeArea) {
		this.nomeArea = nomeArea;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(BigDecimal valorLocacao) {
		this.valorLocacao = valorLocacao;
	}

	public Integer getDiasAntecedencia() {
		return diasAntecedencia;
	}

	public void setDiasAntecedencia(Integer diasAntecedencia) {
		this.diasAntecedencia = diasAntecedencia;
	}

	public Boolean getUsuarioInadimplente() {
		return usuarioInadimplente;
	}

	public void setUsuarioInadimplente(Boolean usuarioInadimplente) {
		this.usuarioInadimplente = usuarioInadimplente;
	}

	public String getTermoDeUso() {
		return termoDeUso;
	}

	public void setTermoDeUso(String termoDeUso) {
		this.termoDeUso = termoDeUso;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public Boolean getLiberarParaAgendar() {
		return liberarParaAgendar;
	}

	public void setLiberarParaAgendar(Boolean liberarParaAgendar) {
		this.liberarParaAgendar = liberarParaAgendar;
	}

	public Boolean getPermiteRetirarValor() {
		return permiteRetirarValor;
	}

	public void setPermiteRetirarValor(Boolean permiteRetirarValor) {
		this.permiteRetirarValor = permiteRetirarValor;
	}

	public Integer getDiasParaCancelar() {
		return diasParaCancelar;
	}

	public void setDiasParaCancelar(Integer diasParaCancelar) {
		this.diasParaCancelar = diasParaCancelar;
	}

	public LocalTime getHorasMinutosPCancelar() {
		return horasMinutosPCancelar;
	}

	public void setHorasMinutosPCancelar(LocalTime horasMinutosPCancelar) {
		this.horasMinutosPCancelar = horasMinutosPCancelar;
	}

	public ContasBancarias getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContasBancarias contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public CentroDeCustos getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroDeCustos centroCusto) {
		this.centroCusto = centroCusto;
	}

	public CategoriasContaPR getCategoriaContas() {
		return categoriaContas;
	}

	public void setCategoriaContas(CategoriasContaPR categoriaContas) {
		this.categoriaContas = categoriaContas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaComumId == null) ? 0 : areaComumId.hashCode());
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
		AreasComuns other = (AreasComuns) obj;
		if (areaComumId == null) {
			if (other.areaComumId != null)
				return false;
		} else if (!areaComumId.equals(other.areaComumId))
			return false;
		return true;
	}

}
