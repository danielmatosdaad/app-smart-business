package br.com.app.smart.business.service;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.app.smart.business.builder.infra.FabricaGenericaDados;
import br.com.app.smart.business.dao.facede.ParametroFacade;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.model.Parametro;

@Stateless
public class ParametroServiceImp {

	@Inject
	private Logger log;

	@EJB
	ParametroFacade parametroFacede;

	public void registrar(ParametroDTO dto) throws InfraEstruturaException {

		try {

			
			FabricaGenericaDados<Parametro> fabrica = new FabricaGenericaDados<Parametro>(Parametro.class);

			Parametro entidade = fabrica.transferirDados(dto);
			log.info("-------------------------------------------");
			parametroFacede.registrar(entidade);
			dto.setId(entidade.getId());
			log.info("-------------------------------------------");

		} catch (Exception e) {

			throw new InfraEstruturaException(e);
		}

	}

	public ParametroDTO buscarPorId(final Long id) throws InfraEstruturaException {
		ParametroDTO dto = null;
		try {
			log.info("-------------------------------------------");
			log.info("Buscando ID: " + id);
			Parametro p = parametroFacede.buscar(id);
			log.info("Parametro: ID:" + (p == null ? "ID nao achado" : p.getId()));
			if (p != null) {

				FabricaGenericaDados<ParametroDTO> fabrica = new FabricaGenericaDados<ParametroDTO>(ParametroDTO.class);
				dto = fabrica.transferirDados(p);
				log.info("-------------------------------------------");

			}
		} catch (Exception e) {

			throw new InfraEstruturaException(e);
		}
		return dto;
	}

	public void removerPorId(Long id) throws InfraEstruturaException {
		try {
			log.info("-------------------------------------------");
			log.info("-------------------------------------------");
		} catch (Exception e) {
			throw new InfraEstruturaException(e);
		}
	}

	public void remover(ParametroDTO dto) throws InfraEstruturaException {
		try {
			log.info("-------------------------------------------");
			FabricaGenericaDados<Parametro> fabrica = new FabricaGenericaDados<Parametro>(Parametro.class);

			Parametro entidade = fabrica.transferirDados(dto);
			log.info("Exluindo ID: " + entidade.getId());
			parametroFacede.remover(entidade);
			log.info("-------------------------------------------");
		} catch (Exception e) {
			throw new InfraEstruturaException(e);
		}
	}
}
