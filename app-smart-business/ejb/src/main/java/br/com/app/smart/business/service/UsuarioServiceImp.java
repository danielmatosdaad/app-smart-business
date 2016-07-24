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
import br.com.app.smart.business.dao.facede.ContatoFacade;
import br.com.app.smart.business.dao.facede.SenhaFacade;
import br.com.app.smart.business.dao.facede.UsuarioFacade;
import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.dto.UsuarioDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoPadraoBD;
import br.com.app.smart.business.interfaces.IServicoPadraoLocalBD;
import br.com.app.smart.business.model.Contato;
import br.com.app.smart.business.model.Usuario;

@Stateless
@EJB(name = "java:app/app-smart-business-ejb/UsuarioServiceImp", beanName = "UsuarioServiceImp", beanInterface = IServicoPadraoBD.class)
@Remote(value = { IServicoPadraoBD.class })
@Local(value = { IServicoPadraoLocalBD.class })
public class UsuarioServiceImp implements IServicoPadraoBD<UsuarioDTO>, IServicoPadraoLocalBD<UsuarioDTO>{

	@Inject
	private Logger log;

	@EJB
	UsuarioFacade usuarioFacade;

	@EJB(beanName = "SenhaServiceImp", beanInterface = IServicoPadraoLocalBD.class)
	private IServicoPadraoLocalBD<SenhaDTO> senhaServiceImp;
	
	@EJB(beanName = "ContatoServiceImp", beanInterface = IServicoPadraoLocalBD.class)
	private IServicoPadraoLocalBD<ContatoDTO> contatoServiceImp;

	public UsuarioDTO adiconar(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Usuario.class, dto);

			senhaServiceImp.adiconar(dto.getSenhas());
			
			contatoServiceImp.adiconar(dto.getContatos());
			
			Usuario entidade = FabricaGenericaDados.transferirDados(Usuario.class, dto);
			usuarioFacade.registrar(entidade);
			
			
			dto.setId(entidade.getId());
			LogUtil.printSucesso(log, Usuario.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, Usuario.class);
			throw new InfraEstruturaException(e);

		}
	}
	
	public List<UsuarioDTO> adiconar(List<UsuarioDTO> lista) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Contato.class, lista);

			for (UsuarioDTO usuarioDTO : lista) {

				adiconar(usuarioDTO);
			}

			return lista;

		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public UsuarioDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		UsuarioDTO dto = null;
		try {
			LogUtil.printProcessando(log, Usuario.class, id);
			Usuario p = usuarioFacade.buscar(id);
			if (p != null) {

				dto = FabricaGenericaDados.transferirDados(UsuarioDTO.class, p);
				LogUtil.printSucesso(log, Usuario.class);
			}
		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);
		}
		return dto;
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Usuario.class, dto);

			Usuario entidade = FabricaGenericaDados.transferirDados(Usuario.class, dto);
			usuarioFacade.remover(entidade);

			LogUtil.printSucesso(log, Usuario.class);

		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);
		}
	}

	public UsuarioDTO alterar(UsuarioDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Usuario.class, dto);

			Usuario entidade = FabricaGenericaDados.transferirDados(Usuario.class, dto);
			entidade = usuarioFacade.editar(entidade);
			dto = FabricaGenericaDados.transferirDados(UsuarioDTO.class, entidade);
			LogUtil.printSucesso(log, Usuario.class);

			return dto;
		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<UsuarioDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Usuario.class);

			List<Usuario> lista = usuarioFacade.buscarTodos();

			List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();

			for (Usuario Usuario : lista) {
				UsuarioDTO dto = FabricaGenericaDados.transferirDados(UsuarioDTO.class, Usuario);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Usuario.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, UsuarioDTO.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<UsuarioDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Usuario.class, range);

			List<Usuario> lista = usuarioFacade.buscarPorIntervalo(range);
			List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();

			for (Usuario Usuario : lista) {
				UsuarioDTO dto = FabricaGenericaDados.transferirDados(UsuarioDTO.class, Usuario);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Usuario.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Usuario.class);
			throw new InfraEstruturaException(e);
		}
	}

}
