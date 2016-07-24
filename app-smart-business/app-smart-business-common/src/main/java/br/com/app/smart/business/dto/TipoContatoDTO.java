package br.com.app.smart.business.dto;

public enum TipoContatoDTO {

	EMAIL(1), TELEFONE_RESIDENCIAL(2), CELULAR(3);

	private Integer value;

	private TipoContatoDTO(Integer valor) {

		this.value = valor;
	}

	public Integer getValue() {
		return value;
	}
}
