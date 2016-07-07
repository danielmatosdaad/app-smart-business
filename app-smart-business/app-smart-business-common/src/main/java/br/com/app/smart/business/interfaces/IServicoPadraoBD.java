package br.com.app.smart.business.interfaces;

import java.util.List;


public interface IServicoPadraoBD<T> {

	public void adiconar(T dto);

	public void alterar(T dto);

	public void remover(T dto);

	public List<T> bustarTodos();

	public List<T> bustarPorID();

	public List<T> bustarPorIntervaloID();
}
