package br.com.app.smart.business.model;

import java.io.Serializable;

public enum TipoAcaoAutorizacao implements Serializable {

	PUBLICA(1),AUTENTICADA(2), AUTORIZADA(3), RESTRITO(4);

	private Integer value;

	private TipoAcaoAutorizacao(Integer valor) {

		this.value = valor;
	}

	public Integer getValue() {
		return value;
	}
}
