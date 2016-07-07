package br.com.app.smart.business.model;

public enum TipoParametro {

	CARACTER(1), NUMERAL(2), FLUTUANTE(3), DATA(4), DATAHORA(5);

	private Integer value;

	private TipoParametro(Integer valor) {

		this.value = valor;
	}

	public Integer getValue() {
		return value;
	}
	
}
