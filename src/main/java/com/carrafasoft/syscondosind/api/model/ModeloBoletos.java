package com.carrafasoft.syscondosind.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.carrafasoft.syscondosind.api.enums.Aceite;
import com.carrafasoft.syscondosind.api.enums.BancoEnum;
import com.carrafasoft.syscondosind.api.enums.CNAB;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "modelo_boleto")
public class ModeloBoletos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "modelo_boleto_id")
	private Long modeloBoletoId;

	@Column(name = "carteira", length = 45)
	private String carteira;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Aceite aceite;

	@Column(name = "instruncao_01", length = 100)
	private String instruncao01;

	@Column(name = "instruncao_02", length = 100)
	private String instruncao02;

	@Column(name = "instruncao_03", length = 100)
	private String instruncao03;

	@Column(name = "instruncao_04", length = 100)
	private String instruncao04;

	@Column(name = "instruncao_05", length = 100)
	private String instruncao05;

	@Column(name = "local_01", length = 100)
	private String local01;

	@Column(name = "local_02", length = 100)
	private String local02;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "banco_enum", length = 45)
	private BancoEnum bancoEnum;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "cnab")
	private CNAB cnab;

	@NotNull
	private Boolean status;

	@NotNull
	@JsonIgnoreProperties("enderecoCondominio")
	@ManyToOne
	@JoinColumn(name = "condominio_id")
	private Condominios condominio;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "conta_bancaria_id")
	private ContasBancarias contaBancaria;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@ManyToOne
	@JoinColumn(name = "beneficiario_id")
	private Beneficiario beneficiario;

	@NotNull
	@JsonIgnoreProperties("condominio")
	@OneToOne
	@JoinColumn(name = "config_boleto_id")
	private ConfigBoletos configBoletos;

	public Long getModeloBoletoId() {
		return modeloBoletoId;
	}

	public void setModeloBoletoId(Long modeloBoletoId) {
		this.modeloBoletoId = modeloBoletoId;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public Aceite getAceite() {
		return aceite;
	}

	public void setAceite(Aceite aceite) {
		this.aceite = aceite;
	}

	public String getInstruncao01() {
		return instruncao01;
	}

	public void setInstruncao01(String instruncao01) {
		this.instruncao01 = instruncao01;
	}

	public String getInstruncao02() {
		return instruncao02;
	}

	public void setInstruncao02(String instruncao02) {
		this.instruncao02 = instruncao02;
	}

	public String getInstruncao03() {
		return instruncao03;
	}

	public void setInstruncao03(String instruncao03) {
		this.instruncao03 = instruncao03;
	}

	public String getInstruncao04() {
		return instruncao04;
	}

	public void setInstruncao04(String instruncao04) {
		this.instruncao04 = instruncao04;
	}

	public String getInstruncao05() {
		return instruncao05;
	}

	public void setInstruncao05(String instruncao05) {
		this.instruncao05 = instruncao05;
	}

	public String getLocal01() {
		return local01;
	}

	public void setLocal01(String local01) {
		this.local01 = local01;
	}

	public String getLocal02() {
		return local02;
	}

	public void setLocal02(String local02) {
		this.local02 = local02;
	}

	public BancoEnum getBancoEnum() {
		return bancoEnum;
	}

	public void setBancoEnum(BancoEnum bancoEnum) {
		this.bancoEnum = bancoEnum;
	}

	public CNAB getCnab() {
		return cnab;
	}

	public void setCnab(CNAB cnab) {
		this.cnab = cnab;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Condominios getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominios condominio) {
		this.condominio = condominio;
	}

	public ContasBancarias getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContasBancarias contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public ConfigBoletos getConfigBoletos() {
		return configBoletos;
	}

	public void setConfigBoletos(ConfigBoletos configBoletos) {
		this.configBoletos = configBoletos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modeloBoletoId == null) ? 0 : modeloBoletoId.hashCode());
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
		ModeloBoletos other = (ModeloBoletos) obj;
		if (modeloBoletoId == null) {
			if (other.modeloBoletoId != null)
				return false;
		} else if (!modeloBoletoId.equals(other.modeloBoletoId))
			return false;
		return true;
	}

	@PrePersist
	public void aoCadastrar() {
		toUpperCase();
	}

	@PreUpdate
	public void aoAtualizar() {
		toUpperCase();
	}

	private void toUpperCase() {

		instruncao01 = instruncao01.trim().toUpperCase();
		instruncao02 = instruncao02.trim().toUpperCase();
		instruncao03 = instruncao03.trim().toUpperCase();
		instruncao04 = instruncao04.trim().toUpperCase();
		instruncao05 = instruncao05.trim().toUpperCase();

		local01 = local01.trim().toUpperCase();
		local02 = local02.trim().toUpperCase();
	}

}
