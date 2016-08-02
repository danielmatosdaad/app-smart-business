package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;

public class GrupoFuncionalidadeDTO implements  DTO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomeGrupoFuncionalidade;

	private String descricao;

	private List<FuncionalidadeDTO> funcionalidades;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeGrupoFuncionalidade() {
		return nomeGrupoFuncionalidade;
	}

	public void setNomeGrupoFuncionalidade(String nomeGrupoFuncionalidade) {
		this.nomeGrupoFuncionalidade = nomeGrupoFuncionalidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<FuncionalidadeDTO> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadeDTO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
