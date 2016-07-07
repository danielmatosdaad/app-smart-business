package br.com.app.smart.business.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.app.smart.business.dto.ParametroDTO;

@Stateless
public class ParametroServiceImp {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public void registrar(ParametroDTO dto) {

	}
}
