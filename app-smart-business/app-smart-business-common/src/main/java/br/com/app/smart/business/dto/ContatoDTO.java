package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;

public class ContatoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private TipoContatoDTO tipoContato;
	private String valor;
	private RegistroAuditoriaDTO registroAuditoria;
	private List<UsuarioDTO> usuarios;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoContatoDTO getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContatoDTO tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
