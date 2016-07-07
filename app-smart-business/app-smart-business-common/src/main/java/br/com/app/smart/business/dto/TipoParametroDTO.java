package br.com.app.smart.business.dto;

public enum TipoParametroDTO {

	CARACTER(1), NUMERAL(2), FLUTUANTE(3), DATA(4), DATAHORA(5);

	private int value;

	private TipoParametroDTO(int valor) {

		this.value = valor;
	}

	public int getValue() {
		return value;
	}
}