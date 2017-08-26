package br.com.caelum.livraria.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

public class DAO<T> {

	private final Class<T> classe;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public void adiciona(T t) {

		// consegue a entity manager
		EntityManager em = new JPAUtil().getEntityManager();

		// abre transacao
		em.getTransaction().begin();

		// persiste o objeto
		em.persist(t);

		// commita a transacao
		em.getTransaction().commit();

		// fecha a entity manager
		em.close();
	}

	public void remove(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.remove(em.merge(t));

		em.getTransaction().commit();
		em.close();
	}

	public void atualiza(T t) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();

		em.merge(t);

		em.getTransaction().commit();
		em.close();
	}

	public List<T> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

	public T buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		T instancia = em.find(classe, id);
		em.close();
		return instancia;
	}

	public int contaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		Root<T> root = query.from(classe);

		if (valor != null)
			query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		em.close();
		return lista;
	}

//	public List<T> listaTodosPaginada(int firstResult, int maxResults, String campoOrdenacao,
//			SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
//
//		EntityManager em = new JPAUtil().getEntityManager();
//		Session session = em.unwrap(Session.class);
//
//		Criteria criteria = session.createCriteria(classe);
//		criteria.setFirstResult(firstResult);
//		criteria.setMaxResults(maxResults);
//
//		for (String key : filtros.keySet()) {
//			if(key.equalsIgnoreCase("preco")){//caso busque por preco
//				System.out.println("Entrou no IF@@@@@@@@@@@@@@@@@");
//				criteria.add(Restrictions.ge(key, new Double(filtros.get(key).toString())));
//			} else {
//				// ilike nao leva em consideracao maiuscula minuscula
//				criteria.add(Restrictions.ilike(key, filtros.get(key) + "%"));
//			}
//		}
//
//		if (campoOrdenacao != null) {
//			if (sentidoOrdenacao.name().contains("ASC")) {
//				criteria.addOrder(Order.asc(campoOrdenacao));
//			} else {
//				criteria.addOrder(Order.desc(campoOrdenacao));
//			}
//		}
//
//		List<T> list = criteria.list();
//		em.close();
//		return list;
//	}

	public EntityManager getEntityManager() {
		return new JPAUtil().getEntityManager();
	}

	public int quantidadeDeElementos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		em.close();
		return (int) result;
	}
}
