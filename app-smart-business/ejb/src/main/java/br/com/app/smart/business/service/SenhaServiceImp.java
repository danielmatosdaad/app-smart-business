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
import br.com.app.smart.business.dao.facede.SenhaFacade;
import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoPadraoBD;
import br.com.app.smart.business.interfaces.IServicoPadraoLocalBD;
import br.com.app.smart.business.model.Contato;
import br.com.app.smart.business.model.Senha;

@Stateless
@EJB(name = "java:app/app-smart-business-ejb/SenhaServiceImp", beanName = "SenhaServiceImp", beanInterface = IServicoPadraoBD.class)
@Remote(value = { IServicoPadraoBD.class })
@Local(value = { IServicoPadraoLocalBD.class })
public class SenhaServiceImp implements IServicoPadraoBD<SenhaDTO>, IServicoPadraoLocalBD<SenhaDTO> {

	@Inject
	private Logger log;

	@EJB
	SenhaFacade SenhaFacade;

	@Override
	public SenhaDTO adiconar(SenhaDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Senha.class, dto);

			Senha entidade = FabricaGenericaDados.transferirDados(Senha.class, dto);

			SenhaFacade.registrar(entidade);
			dto.setId(entidade.getId());
			LogUtil.printSucesso(log, Senha.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<SenhaDTO> adiconar(List<SenhaDTO> lista) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Senha.class, lista);

			for (SenhaDTO senhaDTO : lista) {

				adiconar(senhaDTO);
			}

			return lista;

		} catch (Exception e) {
			LogUtil.printErro(log, Contato.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public SenhaDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		SenhaDTO dto = null;
		try {
			LogUtil.printProcessando(log, Senha.class, id);
			Senha p = SenhaFacade.buscar(id);
			if (p != null) {

				dto = FabricaGenericaDados.transferirDados(SenhaDTO.class, p);
				LogUtil.printSucesso(log, Senha.class);
			}
		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}
		return dto;
	}

	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
		try {
		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(SenhaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Senha.class, dto);

			Senha entidade = FabricaGenericaDados.transferirDados(Senha.class, dto);
			SenhaFacade.remover(entidade);

			LogUtil.printSucesso(log, Senha.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}
	}

	public SenhaDTO alterar(SenhaDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Senha.class, dto);

			Senha entidade = FabricaGenericaDados.transferirDados(Senha.class, dto);
			entidade = SenhaFacade.editar(entidade);
			dto = FabricaGenericaDados.transferirDados(SenhaDTO.class, entidade);
			LogUtil.printSucesso(log, Senha.class);

			return dto;
		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<SenhaDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Senha.class);

			List<Senha> lista = SenhaFacade.buscarTodos();

			List<SenhaDTO> listaDTO = new ArrayList<SenhaDTO>();

			for (Senha Senha : lista) {
				SenhaDTO dto = FabricaGenericaDados.transferirDados(SenhaDTO.class, Senha);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Senha.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<SenhaDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Senha.class, range);

			List<Senha> lista = SenhaFacade.buscarPorIntervalo(range);
			List<SenhaDTO> listaDTO = new ArrayList<SenhaDTO>();

			for (Senha Senha : lista) {
				SenhaDTO dto = FabricaGenericaDados.transferirDados(SenhaDTO.class, Senha);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Senha.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Senha.class);
			throw new InfraEstruturaException(e);
		}
	}

}
