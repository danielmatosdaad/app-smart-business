package br.com.app.smart.business.dao.facede;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.app.smart.business.model.Contato;

public abstract class AbstractFacade<T> {

	@Inject
	private Logger log;

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public T registrar(T entity) {
		log.info("Registrando...");
		getEntityManager().persist(entity);
		log.info("Registrado Sucesso");
		return entity;
	}

	public void registrarLista(List<T> list) {

		for (T t : list) {
			registrar(t);
		}
	}

	public T editar(T entity) {
		log.info("Editando...");
		getEntityManager().merge(entity);
		log.info("Editado Sucesso");

		return entity;
	}

	public void remover(T entity) {
		log.info("Removendo...");
		getEntityManager().remove(getEntityManager().merge(entity));
		log.info("Removido Sucesso");
	}

	public T buscar(Object id) {
		log.info("Buscando...");
		T resultado = getEntityManager().find(entityClass, id);
		log.info("Buscado Sucesso");
		return resultado;

	}

	public List<T> buscarTodos() {
		log.info("Buscando Todos...");
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));

		List<T> resultado = getEntityManager().createQuery(cq).getResultList();

		log.info("Buscado Todos sucesso, quantidade: " + resultado.size());
		return resultado;
	}

	public List<T> buscarPorIntervalo(int[] range) {

		log.info("Buscando por intervalo...");
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);

		List<T> resultado = q.getResultList();
		log.info("Buscado por intervalo sucesso, quantidade: " + resultado.size());
		return resultado;
	}

	public long count() {

		log.info("Contando ...");

		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		long resultado = ((Long) q.getSingleResult()).longValue();
		log.info("Contado sucesso");
		return resultado;
	}

}
