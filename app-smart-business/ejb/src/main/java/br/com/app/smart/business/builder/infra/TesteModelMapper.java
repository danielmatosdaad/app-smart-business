package br.com.app.smart.business.builder.infra;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.app.smart.business.dto.FuncionalidadeDTO;
import br.com.app.smart.business.model.Funcionalidade;
import br.com.app.smart.business.model.GrupoFuncionalidade;

public class TesteModelMapper {

	public static void main(String args[]) {

		Funcionalidade e1 = new Funcionalidade();
		e1.setId(1L);
		e1.setNomeFuncionalidade("Manter Usuario");
		e1.setDescricao("Possibilita realizar alguma coisa1");

		List<Funcionalidade> lista2 = new ArrayList<Funcionalidade>();
		lista2.add(e1);
		GrupoFuncionalidade g1 = new GrupoFuncionalidade();
		g1.setId(1L);
		g1.setNomeGrupoFuncionalidade("Usuario2");
		g1.setDescricao("Possibilita realizar alguma coisa2");
		g1.setFuncionalidades(lista2);

		e1.setGrupoFuncionalidade(g1);
		List<Funcionalidade> lista = new ArrayList<Funcionalidade>();
		lista.add(e1);
		List<FuncionalidadeDTO> listaDTO = new ArrayList<FuncionalidadeDTO>();

		for (Funcionalidade Funcionalidade : lista) {
			ModelMapper modelMapper = new ModelMapper();
			FuncionalidadeDTO dto = modelMapper.map(Funcionalidade, FuncionalidadeDTO.class);
			listaDTO.add(dto);
		}
	}
}
