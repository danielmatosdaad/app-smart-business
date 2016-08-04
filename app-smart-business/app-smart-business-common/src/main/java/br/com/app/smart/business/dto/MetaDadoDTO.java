package br.com.app.smart.business.dto;

import java.io.Serializable;

public class MetaDadoDTO implements DTO, Serializable {

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

	private Long versao;

	private String xml;

	private String xhtml;

	private RegistroAuditoriaDTO registroAuditoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getXhtml() {
		return xhtml;
	}

	public void setXhtml(String xhtml) {
		this.xhtml = xhtml;
	}

	public RegistroAuditoriaDTO getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoriaDTO registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
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

}
