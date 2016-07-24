package br.com.app.smart.business.conversores;

import java.util.List;

import br.com.app.smart.business.builder.infra.FabricaGenericaDados;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.model.Parametro;

public class ParametroConversor implements IConversorEntidadeDTO<Parametro, ParametroDTO> {

	@Override
	public ParametroDTO converterParaDTO(Parametro entidade) {

		try {

			ParametroDTO dtoConvertido = FabricaGenericaDados.transferirDados(ParametroDTO.class, entidade);

			return dtoConvertido;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public Parametro converterParaEntidade(ParametroDTO dto) {
		try {

			Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);

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
