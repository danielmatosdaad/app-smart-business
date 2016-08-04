package br.com.app.smart.business.service;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import br.com.app.smart.business.dao.facede.ContatoFacade;
import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.model.Contato;
import br.com.app.smart.business.model.Parametro;

@Stateless
@Remote(value = { IServicoRemoteDAO.class })
@Local(value = { IServicoLocalDAO.class })
public class ContatoServiceImp implements IServicoRemoteDAO<ContatoDTO>, IServicoLocalDAO<ContatoDTO> {

	@Inject
	private Logger log;

	@EJB
	ContatoFacade contatoFacade;

	@Override
	public ContatoDTO adiconar(ContatoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.adiconar(this.contatoFacade, Contato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContatoDTO> adiconar(List<ContatoDTO> listaDto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.adiconar(this.contatoFacade, Contato.class, listaDto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContatoDTO alterar(ContatoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.alterar(this.contatoFacade, Contato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public void remover(ContatoDTO dto) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			ServiceDAO.remover(this.contatoFacade, Contato.class, dto);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}

	}

	@Override
	public void removerPorId(Long id) throws InfraEstruturaException, NegocioException {
	

	}

	@Override
	public List<ContatoDTO> bustarTodos() throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.bustarTodos(this.contatoFacade, ContatoDTO.class);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public ContatoDTO bustarPorID(Long id) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.bustarPorID(this.contatoFacade, ContatoDTO.class,id);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

	@Override
	public List<ContatoDTO> bustarPorIntervaloID(int[] range) throws InfraEstruturaException, NegocioException {
		try {
			LogUtil.printProcessando(log, ContatoServiceImp.class);
			return ServiceDAO.bustarPorIntervaloID(this.contatoFacade, ContatoDTO.class,range);

		} catch (Exception e) {
			LogUtil.printErro(log, Parametro.class);
			throw new InfraEstruturaException(e);

		}
	}

}
