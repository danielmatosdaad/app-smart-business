package br.com.app.smart.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.builder.infra.FabricaGenericaDados;
import br.com.app.smart.business.dao.facede.UsuarioFacade;
import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.dto.UsuarioDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.Contato;
import br.com.app.smart.business.model.Parametro;
import br.com.app.smart.business.model.Usuario;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class UsuarioServiceImp implements IServicoRemoteDAO<UsuarioDTO>, IServicoLocalDAO<UsuarioDTO> {

	@Inject
	private Logger log;

	@EJB
	UsuarioFacade usuarioFacade;

	@EJB(beanName = "SenhaServiceImp", beanInterface = IServicoLocalDAO.class)
	private IServicoLocalDAO<SenhaDTO> senhaServiceImp;

	@EJB(beanName = "ContatoServiceImp", beanInterface = IServicoLocalDAO.class)
	private IServicoLocalDAO<ContatoDTO> contatoServiceImp;

	@Override
	public UsuarioDTO adiconar(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class, dto);

			if (dto.getSenhas() != null || !dto.getSenhas().isEmpty()) {

				dto.setSenhas(senhaServiceImp.adiconar(dto.getSenhas()));
			}

			if (dto.getSenhas() != null || !dto.getSenhas().isEmpty()) {

				dto.setContatos(contatoServiceImp.adiconar(dto.getContatos()));
			}

			return ServiceDAO.adiconar(this.usuarioFacade, Usuario.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<UsuarioDTO> adiconar(List<UsuarioDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class, listaDto);
			return ServiceDAO.adiconar(this.usuarioFacade, Usuario.class, listaDto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public UsuarioDTO alterar(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class);
			return ServiceDAO.alterar(this.usuarioFacade, Usuario.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class);
			ServiceDAO.remover(this.usuarioFacade, Usuario.class, dto);

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
	public List<UsuarioDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class);
			return ServiceDAO.bustarTodos(this.usuarioFacade, UsuarioDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public UsuarioDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class);
			return ServiceDAO.bustarPorID(this.usuarioFacade, UsuarioDTO.class, id);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<UsuarioDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, UsuarioServiceImp.class);
			return ServiceDAO.bustarPorIntervaloID(this.usuarioFacade, UsuarioDTO.class, range);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

}
