package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;

public class ContaDTO implements DTO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String descricao;

	private TipoContaDTO tipoConta;

	private List<AssinaturaDTO> assinaturas;
	
	private RegistroAuditoriaDTO registroAuditoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoContaDTO getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaDTO tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<AssinaturaDTO> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<AssinaturaDTO> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public RegistroAuditoriaDTO getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoriaDTO registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
	}

	
}
