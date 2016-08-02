package br.com.app.smart.business.databuilder;

import java.util.Date;

import br.com.app.smart.business.dto.FuncionalidadeDTO;
import br.com.app.smart.business.dto.GrupoFuncionalidadeDTO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class GrupoFuncionalidadeBuilder {

	public static GrupoFuncionalidadeDTO getInstanceDTO(GrupoTipoFuncionalidadeBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarGrupoFuncionalidadeDTO();

		default:
			break;
		}
		return criarGrupoFuncionalidadeDTO();
	}

	private static GrupoFuncionalidadeDTO criarGrupoFuncionalidadeDTO() {

		GrupoFuncionalidadeDTO dto = new GrupoFuncionalidadeDTO();
		dto.setNomeGrupoFuncionalidade("Usuario");
		dto.setDescricao("Possibilita realizar alguma coisa");
		return dto;

	}

	public static enum GrupoTipoFuncionalidadeBuilder {

		INSTANCIA;
	}
}
