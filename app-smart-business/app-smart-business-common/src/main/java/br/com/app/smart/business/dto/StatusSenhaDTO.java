package br.com.app.smart.business.dto;

public enum StatusSenhaDTO {

	ATIVO(1), DESABILITADO(2),EXCLUIDO(3);

	private Integer value;

	private StatusSenhaDTO(Integer valor) {

		this.value = valor;
	}

	public Integer getValue() {
		return value;
	}
}
