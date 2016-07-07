package br.com.app.smart.business.conversores;

import java.util.List;

import br.com.app.smart.business.builder.infra.FabricaGenericaDados;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.model.Parametro;

public class ParametroConversor implements IConversorEntidadeDTO<Parametro, ParametroDTO> {

	@Override
	public ParametroDTO converterParaDTO(Parametro entidade) {

		try {

			FabricaGenericaDados<ParametroDTO> fabrica = new FabricaGenericaDados<ParametroDTO>(
					ParametroDTO.class);
			ParametroDTO dto = fabrica.transferirDados(entidade);

			return dto;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public Parametro converterParaEntidade(ParametroDTO dto) {
		try {

			FabricaGenericaDados<Parametro> fabrica = new FabricaGenericaDados<Parametro>(
					Parametro.class);
			Parametro entidade = fabrica.transferirDados(dto);

			return entidade;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public List<ParametroDTO> converterListaParaDTO(List<Parametro> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parametro> converterListaParaEntidade(List<ParametroDTO> dto) {
		// TODO Auto-generated method stub
		return null;
	}


}
