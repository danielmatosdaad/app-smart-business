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
import br.com.app.smart.business.dao.facede.ParametroFacade;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.dto.UsuarioDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoPadraoBD;
import br.com.app.smart.business.interfaces.IServicoPadraoLocalBD;
import br.com.app.smart.business.model.Parametro;

@Stateless
@EJB(name = "java:app/app-smart-business-ejb/ParametroServiceImp", beanName = "ParametroServiceImp", beanInterface = IServicoPadraoBD.class)
@Remote(value = { IServicoPadraoBD.class })
@Local(value = { IServicoPadraoLocalBD.class })
public class ParametroServiceImp implements IServicoPadraoBD<ParametroDTO>, IServicoPadraoLocalBD<ParametroDTO> {

	@Inject
	private Logger log;

	@EJB
	ParametroFacade parametroFacede;

	@Override
	public ParametroDTO adiconar(ParametroDTO dto) throws InfraEstruturaException, NegocioException {

		try {
			LogUtil.printProcessando(log, Parametro.class, dto);
			Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);
			parametroFacede.registrar(entidade);
			dto.setId(entidade.getId());
			LogUtil.printSucesso(log, Parametro.class);

			return dto;

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ParametroDTO> adiconar(List<ParametroDTO> listaDto) throws InfraEstruturaException, NegocioException {

		for (ParametroDTO parametroDTO : listaDto) {
			adiconar(parametroDTO);
		}
		return listaDto;
	}

	@Override
	public ParametroDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		ParametroDTO dto = null;
		try {
			LogUtil.printProcessando(log, Parametro.class, id);
			Parametro p = parametroFacede.buscar(id);
			if (p != null) {

				dto = FabricaGenericaDados.transferirDados(ParametroDTO.class, p);
				LogUtil.printSucesso(log, Parametro.class);
			}
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
		return dto;
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
			LogUtil.printProcessando(log, Parametro.class, dto);

			Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);
			parametroFacede.remover(entidade);

			LogUtil.printSucesso(log, Parametro.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	public ParametroDTO alterar(ParametroDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Parametro.class, dto);

			Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);
			parametroFacede.editar(entidade);
			dto.setId(entidade.getId());

			LogUtil.printSucesso(log, Parametro.class);

			return dto;
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}

	}

	@Override
	public List<ParametroDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Parametro.class);

			List<Parametro> lista = parametroFacede.buscarTodos();

			List<ParametroDTO> listaDTO = new ArrayList<ParametroDTO>();

			for (Parametro parametro : lista) {
				ParametroDTO dto = FabricaGenericaDados.transferirDados(ParametroDTO.class, parametro);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Parametro.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}

	@Override
	public List<ParametroDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, Parametro.class, range);

			List<Parametro> lista = parametroFacede.buscarPorIntervalo(range);
			List<ParametroDTO> listaDTO = new ArrayList<ParametroDTO>();

			for (Parametro parametro : lista) {
				ParametroDTO dto = FabricaGenericaDados.transferirDados(ParametroDTO.class, parametro);
				listaDTO.add(dto);
			}

			LogUtil.printSucesso(log, Parametro.class);

			return listaDTO;
		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);
		}
	}
}
