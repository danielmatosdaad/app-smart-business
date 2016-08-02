package br.com.app.smart.business.databuilder;


import br.com.app.smart.business.databuilder.GrupoFuncionalidadeBuilder.GrupoTipoFuncionalidadeBuilder;
import br.com.app.smart.business.dto.FuncionalidadeDTO;

public class FuncionalidadeBuilder {

	public static FuncionalidadeDTO getInstanceDTO(TipoFuncionalidadeBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarFuncionalidadeDTO();

		default:
			break;
		}
		return criarFuncionalidadeDTO();
	}

	private static FuncionalidadeDTO criarFuncionalidadeDTO() {

		FuncionalidadeDTO dto = new FuncionalidadeDTO();
		dto.setNomeFuncionalidade("Manter Usuario");
		dto.setDescricao("Possibilita realizar alguma coisa");
		dto.setGrupoFuncionalidade(GrupoFuncionalidadeBuilder.getInstanceDTO(GrupoTipoFuncionalidadeBuilder.INSTANCIA));
		return dto;

	}

	public static enum TipoFuncionalidadeBuilder {

		INSTANCIA;
	}
}
