package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements DTO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String sobrenome;
	private String login;
	private RegistroAuditoriaDTO registroAuditoria;
	private List<ContatoDTO> contatos;
	private List<SenhaDTO> senhas;
	private StatusUsuarioDTO statusUsuario;

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<ContatoDTO> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoDTO> contatos) {
		this.contatos = contatos;
	}

	public List<SenhaDTO> getSenhas() {
		return senhas;
	}

	public void setSenhas(List<SenhaDTO> senhas) {
		this.senhas = senhas;
	}

	public RegistroAuditoriaDTO getRegistroAuditoria() {
		return registroAuditoria;
	}

	public void setRegistroAuditoria(RegistroAuditoriaDTO registroAuditoria) {
		this.registroAuditoria = registroAuditoria;
	}

	public StatusUsuarioDTO getStatusUsuario() {
		return statusUsuario;
	}

	public void setStatusUsuario(StatusUsuarioDTO statusUsuario) {
		this.statusUsuario = statusUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
