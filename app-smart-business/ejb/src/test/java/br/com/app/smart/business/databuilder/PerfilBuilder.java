package br.com.app.smart.business.databuilder;

import java.util.Date;

import br.com.app.smart.business.dto.PerfilDTO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class PerfilBuilder {

	public static PerfilDTO getInstanceDTO(TipoPerfilBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarPerfilDTO();

		default:
			break;
		}
		return criarPerfilDTO();
	}

	private static PerfilDTO criarPerfilDTO() {
		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		PerfilDTO dto = new PerfilDTO();

		return dto;

	}

	public static enum TipoPerfilBuilder {

		INSTANCIA;
	}
}
