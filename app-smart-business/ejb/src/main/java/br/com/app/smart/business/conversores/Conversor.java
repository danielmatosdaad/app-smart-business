package br.com.app.smart.business.conversores;

import org.modelmapper.ModelMapper;

import br.com.app.smart.business.dto.DTO;
import br.com.app.smart.business.exception.InfraEstruturaException;

public class Conversor {

	@SuppressWarnings("unchecked")
	public static <T> T converter(Object objeto, Class<T> destino) throws InfraEstruturaException {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(objeto, destino);

	}
}
