package br.com.app.smart.business.exception;

public class InfraEstruturaException extends TransacaoException{

	
	public InfraEstruturaException() {
		// TODO Auto-generated constructor stub
	}

	public InfraEstruturaException(Exception e) {
		super(e);
	}

	public InfraEstruturaException(String descricao, Exception e) {
		super(descricao, e);
	}

	public InfraEstruturaException(int codioErro, String descricao) {
		super(codioErro, descricao);
	}
}
