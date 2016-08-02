package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.dao.facede.SenhaFacade;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.model.Parametro;
import br.com.app.smart.business.model.Senha;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class SenhaServiceImp implements IServicoRemoteDAO<SenhaDTO>, IServicoLocalDAO<SenhaDTO> {

	@Inject
	private Logger log;

	@EJB
	SenhaFacade senhaFacede;

	@Override
	public SenhaDTO adiconar(SenhaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class, dto);
			return ServiceDAO.adiconar(this.senhaFacede, Senha.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public List<SenhaDTO> adiconar(List<SenhaDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class, listaDto);
			return ServiceDAO.adiconar(this.senhaFacede, Senha.class, listaDto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public SenhaDTO alterar(SenhaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class, dto);
			return ServiceDAO.adiconar(this.senhaFacede, Senha.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(SenhaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class, dto);
			ServiceDAO.remover(this.senhaFacede, Senha.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SenhaDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class);
			return ServiceDAO.bustarTodos(this.senhaFacede, SenhaDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public SenhaDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class);
			return ServiceDAO.bustarPorID(this.senhaFacede, SenhaDTO.class, id);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<SenhaDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, SenhaServiceImp.class);
			return ServiceDAO.bustarPorIntervaloID(this.senhaFacede, SenhaDTO.class, range);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

}
