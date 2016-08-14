package br.com.app.smart.business.dto;

import java.io.Serializable;
import java.util.List;


public class PerfilDTO implements Comparable<PerfilDTO>, DTO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String nomePerfil;

	private String descricao;

	private List<FuncionalidadeDTO> funcionalidades;

	private List<PerfilDTO> perfilFilhos;

	private PerfilDTO perfilPai;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
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

	public List<PerfilDTO> getPerfilFilhos() {
		return perfilFilhos;
	}

	public void setPerfilFilhos(List<PerfilDTO> perfilFilhos) {
		this.perfilFilhos = perfilFilhos;
	}

	public PerfilDTO getPerfilPai() {
		return perfilPai;
	}

	public void setPerfilPai(PerfilDTO perfilPai) {
		this.perfilPai = perfilPai;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PerfilDTO)) {

			return false;
		}

		PerfilDTO perfil = (PerfilDTO) obj;

		if (perfil.id != this.id) {

			return false;
		}

		return true;
	}

	public int compareTo(PerfilDTO o) {
		if (o.id == this.id) {
			return 0;
		} else if (o.id > this.id) {
			return 1;
		} else {
			return -1;
		}
	}

}
