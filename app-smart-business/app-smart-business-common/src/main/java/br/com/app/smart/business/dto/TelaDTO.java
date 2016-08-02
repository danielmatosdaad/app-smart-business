package br.com.app.smart.business.dto;

import java.io.Serializable;

public class TelaDTO implements DTO,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private int numeroTela;

	private String nomeTela;

	private String tituloTela;

	private String descricaoTela;

	private String urlTela;

	private FuncionalidadeDTO funcionalidades;

	private MetaDadoDTO metadado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroTela() {
		return numeroTela;
	}

	public void setNumeroTela(int numeroTela) {
		this.numeroTela = numeroTela;
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTituloTela() {
		return tituloTela;
	}

	public void setTituloTela(String tituloTela) {
		this.tituloTela = tituloTela;
	}

	public String getDescricaoTela() {
		return descricaoTela;
	}

	public void setDescricaoTela(String descricaoTela) {
		this.descricaoTela = descricaoTela;
	}

	public String getUrlTela() {
		return urlTela;
	}

	public void setUrlTela(String urlTela) {
		this.urlTela = urlTela;
	}

	public FuncionalidadeDTO getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(FuncionalidadeDTO funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public MetaDadoDTO getMetadado() {
		return metadado;
	}

	public void setMetadado(MetaDadoDTO metadado) {
		this.metadado = metadado;
	}

}
