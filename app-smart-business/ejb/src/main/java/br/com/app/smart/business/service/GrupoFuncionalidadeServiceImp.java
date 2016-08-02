package br.com.app.smart.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.dao.facede.GrupoFuncionalidadeFacade;
import br.com.app.smart.business.dto.GrupoFuncionalidadeDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.GrupoFuncionalidade;

@Stateless(mappedName = "GrupoGrupoFuncionalidadeServiceImp")
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class GrupoFuncionalidadeServiceImp
		implements IServicoRemoteDAO<GrupoFuncionalidadeDTO>, IServicoLocalDAO<GrupoFuncionalidadeDTO> {

	@Inject
	private Logger log;

	@EJB
	GrupoFuncionalidadeFacade grupoFuncionalidadeFacede;

	@Override
	public GrupoFuncionalidadeDTO adiconar(GrupoFuncionalidadeDTO dto)
			throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, GrupoFuncionalidadeServiceImp.class, dto);

			ServiceDAO.adiconar(grupoFuncionalidadeFacede, GrupoFuncionalidade.class, dto);
			LogUtil.printSucesso(log, GrupoFuncionalidadeServiceImp.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, GrupoFuncionalidadeServiceImp.class);
			e.printStackTrace();
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<GrupoFuncionalidadeDTO> adiconar(List<GrupoFuncionalidadeDTO> lista)
			throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, GrupoFuncionalidade.class, lista);

			for (GrupoFuncionalidadeDTO GrupoFuncionalidadeDTO : lista) {

				adiconar(GrupoFuncionalidadeDTO);
			}

			return lista;

		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public GrupoFuncionalidadeDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarPorID(this.grupoFuncionalidadeFacede, GrupoFuncionalidadeDTO.class, id);
		} catch (Exception e) {
			LogUtil.printErro(log, GrupoFuncionalidade.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, GrupoFuncionalidadeServiceImp.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(GrupoFuncionalidadeDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			ServiceDAO.remover(this.grupoFuncionalidadeFacede, GrupoFuncionalidade.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, GrupoFuncionalidade.class);
			throw new InfraEstruturaException(e);
		}
	}

	public GrupoFuncionalidadeDTO alterar(GrupoFuncionalidadeDTO dto) throws InfraEstruturaException, NegocioException {
		try {

			ServiceDAO.alterar(this.grupoFuncionalidadeFacede, GrupoFuncionalidade.class, dto);
			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<GrupoFuncionalidadeDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarTodos(this.grupoFuncionalidadeFacede, GrupoFuncionalidadeDTO.class);
		} catch (Exception e) {
			LogUtil.printErro(log, GrupoFuncionalidade.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<GrupoFuncionalidadeDTO> bustarPorIntervaloID(int[] range)
			throws InfraEstruturaException, NegocioException {
		try {

			return ServiceDAO.bustarPorIntervaloID(this.grupoFuncionalidadeFacede, GrupoFuncionalidadeDTO.class, range);
		} catch (Exception e) {
			LogUtil.printErro(log, getClass());
			throw new InfraEstruturaException(e);
		}
	}

}
