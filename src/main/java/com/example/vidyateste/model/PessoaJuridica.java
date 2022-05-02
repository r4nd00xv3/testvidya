package com.example.vidyateste.model;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica  {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;

	@CNPJ(message = "Cnpj está inválido")
	private String cnpj;
	
	private String nomeFantasia;

	private String email;

	private boolean licenca = true;

	@Temporal(TemporalType.DATE)
	private Date datainit;

	@Temporal(TemporalType.DATE)
	private Date datavalid;



	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Endereco> enderecos = new ArrayList<Endereco>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLicenca() {
		return licenca;
	}

	public void setLicenca(boolean licenca) {
		this.licenca = licenca;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Date getDatainit() {
		return datainit;
	}

	public void setDatainit(Date datainit) {
		this.datainit = datainit;
	}

	public Date getDatavalid() {
		return datavalid;
	}

	public void setDatavalid(Date datavalid) {
		this.datavalid = datavalid;
	}
}
