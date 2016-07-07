package br.com.app.smart.business.conversores;

import java.util.List;

import br.com.app.smart.business.dto.DTO;
import br.com.app.smart.business.model.Entidade;

public interface IConversorEntidadeDTO<Entidade,DTO> {

	public DTO converterParaDTO(Entidade e);
	public Entidade converterParaEntidade(DTO dto);	
	public List<DTO> converterListaParaDTO(List<Entidade> e);
	public List<Entidade> converterListaParaEntidade(List<DTO> dto);
}
