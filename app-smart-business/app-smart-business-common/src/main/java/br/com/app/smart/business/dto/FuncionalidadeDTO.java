package br.com.app.smart.business.dto;

import java.io.Serializable;

public class FuncionalidadeDTO implements DTO,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomeFuncionalidade;

	private String descricao;

	private GrupoFuncionalidadeDTO grupoFuncionalidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFuncionalidade() {
		return nomeFuncionalidade;
	}

	public void setNomeFuncionalidade(String nomeFuncionalidade) {
		this.nomeFuncionalidade = nomeFuncionalidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public GrupoFuncionalidadeDTO getGrupoFuncionalidade() {
		return grupoFuncionalidade;
	}

	public void setGrupoFuncionalidade(GrupoFuncionalidadeDTO grupoFuncionalidade) {
		this.grupoFuncionalidade = grupoFuncionalidade;
	}


}