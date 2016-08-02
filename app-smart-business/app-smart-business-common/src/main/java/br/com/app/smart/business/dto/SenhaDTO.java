package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;

public class SenhaDTO implements DTO,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String hashSenha;

	private RegistroAuditoriaDTO registroAuditoria;

	private StatusSenhaDTO statusSenha;
	
	private List<UsuarioDTO> usuarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}

	public StatusSenhaDTO getStatusSenha() {
		return statusSenha;
	}

	public void setStatusSenha(StatusSenhaDTO statusSenha) {
		this.statusSenha = statusSenha;
	}

	public RegistroAuditoriaDTO getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoriaDTO registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}
	
	

}
