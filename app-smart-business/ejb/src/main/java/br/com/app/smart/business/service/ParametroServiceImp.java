package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.dao.facede.ParametroFacade;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.Parametro;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class ParametroServiceImp implements IServicoRemoteDAO<ParametroDTO>, IServicoLocalDAO<ParametroDTO> {

	@Inject
	private Logger log;

	@EJB
	ParametroFacade parametroFacede;

	@Override
	public ParametroDTO adiconar(ParametroDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, ParametroServiceImp.class, dto);
			return ServiceDAO.adiconar(this.parametroFacede, Parametro.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ParametroDTO> adiconar(List<ParametroDTO> listaDto) throws InfraEstruturaException, NegocioException {

		LogUtil.printProcessando(log, ParametroServiceImp.class, listaDto);
		for (ParametroDTO parametroDTO : listaDto) {
			adiconar(parametroDTO);
		}
		return listaDto;
	}

	@Override
	public ParametroDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {

			LogUtil.printProcessando(log, ParametroServiceImp.class, id);
			return ServiceDAO.bustarPorID(this.parametroFacede, ParametroDTO.class, id);
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(ParametroDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ParametroServiceImp.class, dto);
			ServiceDAO.remover(this.parametroFacede, Parametro.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	public ParametroDTO alterar(ParametroDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ParametroServiceImp.class, dto);
			return ServiceDAO.alterar(this.parametroFacede, Parametro.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<ParametroDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ParametroServiceImp.class);

			return ServiceDAO.bustarTodos(this.parametroFacede, ParametroDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<ParametroDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			
			LogUtil.printProcessando(log, ParametroServiceImp.class);

			return ServiceDAO.bustarPorIntervaloID(this.parametroFacede, ParametroDTO.class, range);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}
}
