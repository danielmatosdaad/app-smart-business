package br.com.app.smart.business.databuilder;

import java.util.Date;

import br.com.app.smart.business.dto.MetaDadoDTO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;

public class MetaDadoBuilder {

	public static MetaDadoDTO getInstanceDTO(TipoMetaDadoBuilder tipo) {

		switch (tipo) {

		case INSTANCIA:
			return criarMetaDadoDTO();

		default:
			break;
		}
		return criarMetaDadoDTO();
	}

	private static MetaDadoDTO criarMetaDadoDTO() {
		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		MetaDadoDTO dto = new MetaDadoDTO();
		dto.setVersao(1L);
		dto.setXml("xml");
		dto.setXhtml("xhtml");
		dto.setRegistroAuditoria(r);

		return dto;

	}

	public static enum TipoMetaDadoBuilder {

		INSTANCIA;
	}
}
