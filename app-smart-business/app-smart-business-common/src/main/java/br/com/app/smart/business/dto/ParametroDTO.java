package br.com.app.smart.business.dto;

import java.util.Date;

public class ParametroDTO implements DTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String descricao;
	private Date dataInclusao;
	private Date dataAlteracao;
	private TipoParametroDTO tipoParametro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public TipoParametroDTO getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(TipoParametroDTO tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

}
