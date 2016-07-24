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
import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoPadraoBD;
import br.com.app.smart.business.interfaces.IServicoPadraoLocalBD;
import br.com.app.smart.business.model.Contato;

@Stateless
@EJB(name = "java:app/app-smart-business-ejb/ContatoServiceImp", beanName = "ContatoServiceImp", beanInterface = IServicoPadraoBD.class)
@Remote(value = { IServicoPadraoBD.class })
@Local(value = { IServicoPadraoLocalBD.class })
public class ContatoServiceImp implements IServicoPadraoBD<ContatoDTO>, IServicoPadraoLocalBD<ContatoDTO> {

	@Inject
	private Logger log;

	@EJB
	ContatoFacade ContatoFacade;

	@Override
	public ContatoDTO adiconar(ContatoDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Contato.class, dto);

			Contato entidade = FabricaGenericaDados.transferirDados(Contato.class, dto);

			ContatoFacade.registrar(entidade);
			dto.setId(entidade.getId());
			LogUtil.printSucesso(log, Contato.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);

		}
	}
	
	@Override
	public List<ContatoDTO> adiconar(List<ContatoDTO> lista) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Contato.class, lista);

			for (ContatoDTO contatoDTO : lista) {

				adiconar(contatoDTO);
			}

			return lista;

		} catch (Exception e) {
			LogUtil.printErro(log, ContatoDTO.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContatoDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		ContatoDTO dto = null;
		try {
			LogUtil.printProcessando(log, Contato.class, id);
			Contato p = ContatoFacade.buscar(id);
			if (p != null) {

				dto = FabricaGenericaDados.transferirDados(ContatoDTO.class, p);
				LogUtil.printSucesso(log, Contato.class);
			}
		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}
		return dto;
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(ContatoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Contato.class, dto);

			Contato entidade = FabricaGenericaDados.transferirDados(Contato.class, dto);
			ContatoFacade.remover(entidade);

			LogUtil.printSucesso(log, Contato.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}
	}

	public ContatoDTO alterar(ContatoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Contato.class, dto);

			Contato entidade = FabricaGenericaDados.transferirDados(Contato.class, dto);
			entidade = ContatoFacade.editar(entidade);
			dto = FabricaGenericaDados.transferirDados(ContatoDTO.class, entidade);
			LogUtil.printSucesso(log, Contato.class);

			return dto;
		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<ContatoDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Contato.class);

			List<Contato> lista = ContatoFacade.buscarTodos();

			List<ContatoDTO> listaDTO = new ArrayList<ContatoDTO>();

			for (Contato Contato : lista) {
				ContatoDTO dto = FabricaGenericaDados.transferirDados(ContatoDTO.class, Contato);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Contato.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<ContatoDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Contato.class, range);

			List<Contato> lista = ContatoFacade.buscarPorIntervalo(range);
			List<ContatoDTO> listaDTO = new ArrayList<ContatoDTO>();

			for (Contato Contato : lista) {
				ContatoDTO dto = FabricaGenericaDados.transferirDados(ContatoDTO.class, Contato);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Contato.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);
		}
	}

}
