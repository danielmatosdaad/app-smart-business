package br.com.app.smart.business.interfaces;

import java.util.List;

import br.com.app.smart.business.dto.DTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;


public interface IServicoLocalDAO<T extends DTO> {

	public T adiconar(T dto) throws InfraEstruturaException,NegocioException;

	public List<T> adiconar(List<T> listaDto) throws InfraEstruturaException,NegocioException;
	
	public  T alterar(T dto) throws InfraEstruturaException,NegocioException;

	public void remover(T dto) throws InfraEstruturaException,NegocioException;
	
	public void removerPorId(Long id) throws InfraEstruturaException,NegocioException;

	public List<T> bustarTodos() throws InfraEstruturaException,NegocioException;

	public T bustarPorID(Long id) throws InfraEstruturaException,NegocioException;

	public List<T> bustarPorIntervaloID(int[] range) throws InfraEstruturaException,NegocioException;
}
