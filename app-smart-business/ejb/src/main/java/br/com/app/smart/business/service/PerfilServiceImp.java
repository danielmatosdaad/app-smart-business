package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.dao.facede.PerfilFacade;
import br.com.app.smart.business.dto.FuncionalidadeDTO;
import br.com.app.smart.business.dto.PerfilDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.Perfil;

@Stateless(mappedName = "PerfilServiceImp")
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class PerfilServiceImp implements IServicoRemoteDAO<PerfilDTO>, IServicoLocalDAO<PerfilDTO> {

	@Inject
	private Logger log;

	@EJB(beanName = "FuncionalidadeServiceImp", beanInterface = IServicoLocalDAO.class)
	private IServicoLocalDAO<FuncionalidadeDTO> funcionalidadeServiceImp;

	@EJB
	private PerfilFacade perfilFacade;

	@Override
	public PerfilDTO adiconar(PerfilDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, PerfilServiceImp.class, dto);

			ServiceDAO.adiconar(perfilFacade, Perfil.class, dto);
			LogUtil.printSucesso(log, PerfilServiceImp.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, PerfilServiceImp.class);
			e.printStackTrace();
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<PerfilDTO> adiconar(List<PerfilDTO> lista) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Perfil.class, lista);

			for (PerfilDTO PerfilDTO : lista) {

				adiconar(PerfilDTO);
			}

			return lista;

		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public PerfilDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarPorID(this.perfilFacade, PerfilDTO.class, id);
		} catch (Exception e) {
			LogUtil.printErro(log, Perfil.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, PerfilServiceImp.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(PerfilDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Perfil.class, dto);
			ServiceDAO.remover(this.perfilFacade, Perfil.class, dto);
			LogUtil.printSucesso(log, Perfil.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Perfil.class);
			throw new InfraEstruturaException(e);
		}
	}

	public PerfilDTO alterar(PerfilDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			ServiceDAO.alterar(this.perfilFacade, Perfil.class, dto);
			return dto;
		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<PerfilDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarTodos(this.perfilFacade, PerfilDTO.class);
		} catch (Exception e) {
			LogUtil.printErro(log, Perfil.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<PerfilDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarPorIntervaloID(this.perfilFacade, PerfilDTO.class, range);
		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);
		}
	}

}
