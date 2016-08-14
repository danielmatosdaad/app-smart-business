package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import br.com.app.smart.business.dao.facede.ContratoFacade;
import br.com.app.smart.business.dto.ContratoDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.Contrato;
import br.com.app.smart.business.model.Parametro;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class ContratoServiceImp implements IServicoRemoteDAO<ContratoDTO>, IServicoLocalDAO<ContratoDTO> {

	@Inject
	private Logger log;

	@EJB
	private ContratoFacade contratoFacade;

	@Override
	public ContratoDTO adiconar(ContratoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.adiconar(this.contratoFacade, Contrato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContratoDTO> adiconar(List<ContratoDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.adiconar(this.contratoFacade, Contrato.class, listaDto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContratoDTO alterar(ContratoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.alterar(this.contratoFacade, Contrato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(ContratoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			ServiceDAO.remover(this.contratoFacade, Contrato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
	

	}

	@Override
	public List<ContratoDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.bustarTodos(this.contratoFacade, ContratoDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContratoDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.bustarPorID(this.contratoFacade, ContratoDTO.class,id);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContratoDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContratoServiceImp.class);
			return ServiceDAO.bustarPorIntervaloID(this.contratoFacade, ContratoDTO.class,range);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

}
