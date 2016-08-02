package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.Date;

public class RegistroAuditoriaDTO implements DTO,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dataCadastro;

	private Date dataAlteracao;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
