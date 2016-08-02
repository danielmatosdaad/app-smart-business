package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import br.com.app.smart.business.dao.facede.MetaDadoFacade;
import br.com.app.smart.business.dto.MetaDadoDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.MetaDado;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class MetaDadoServiceImp implements IServicoRemoteDAO<MetaDadoDTO>, IServicoLocalDAO<MetaDadoDTO> {

	@Inject
	private Logger log;

	@EJB
	MetaDadoFacade metaDadoFacade;

	@Override
	public MetaDadoDTO adiconar(MetaDadoDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, MetaDadoServiceImp.class, dto);
			return ServiceDAO.adiconar(this.metaDadoFacade, MetaDado.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDadoServiceImp.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<MetaDadoDTO> adiconar(List<MetaDadoDTO> listaDto) throws InfraEstruturaException, NegocioException {

		for (MetaDadoDTO MetaDadoDTO : listaDto) {
			adiconar(MetaDadoDTO);
		}
		return listaDto;
	}

	@Override
	public MetaDadoDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, MetaDado.class, id);
			return ServiceDAO.bustarPorID(this.metaDadoFacade, MetaDadoDTO.class, id);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(MetaDadoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, MetaDado.class, dto);

			ServiceDAO.remover(metaDadoFacade, MetaDado.class, dto);

			LogUtil.printSucesso(log, MetaDado.class);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}
	}

	public MetaDadoDTO alterar(MetaDadoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, MetaDado.class, dto);

			return ServiceDAO.alterar(metaDadoFacade, MetaDado.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<MetaDadoDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, MetaDado.class);

			return ServiceDAO.bustarTodos(this.metaDadoFacade, MetaDadoDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<MetaDadoDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, MetaDado.class, range);

			return ServiceDAO.bustarPorIntervaloID(this.metaDadoFacade, MetaDadoDTO.class, range);

		} catch (Exception e) {
			LogUtil.printErro(log, MetaDado.class);
			throw new InfraEstruturaException(e);
		}
	}
}
