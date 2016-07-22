package br.com.app.smart.business.interfaces;

public interface IException {

	public int codigoErro();
	public String descricaoErro();
	public StackTraceElement[] getPilhaErro();
	
}
