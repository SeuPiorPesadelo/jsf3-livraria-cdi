package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.DAO;

//LazyDataModel
public class LivroDataModel extends LazyDataModel<Livro> {

	private static final long serialVersionUID = -5406372211973770161L;
	private DAO<Livro> dao = new DAO<Livro>(Livro.class);

	// Precisamos dizer ao LazyDataModel qual é o valor
	// máximo de registros que possuímos de livros
	public LivroDataModel() {
		super.setRowCount(dao.quantidadeDeElementos());
	}

	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao,
			Map<String, Object> filtros) {
		EntityManager em = dao.getEntityManager();
		Session session = em.unwrap(Session.class);

		Criteria criteria = session.createCriteria(Livro.class);
		criteria.setFirstResult(inicio);
		criteria.setMaxResults(quantidade);

		for (String key : filtros.keySet()) {
			if(key.equalsIgnoreCase("preco")){//caso busque por preco
				criteria.add(Restrictions.ge(key, new Double(filtros.get(key).toString())));
			} else {
				// ilike nao leva em consideracao maiuscula minuscula
				criteria.add(Restrictions.ilike(key, filtros.get(key) + "%"));
			}
		}

		if (campoOrdenacao != null) {
			if (sentidoOrdenacao.name().contains("ASC")) {
				criteria.addOrder(Order.asc(campoOrdenacao));
			} else {
				criteria.addOrder(Order.desc(campoOrdenacao));
			}
		}

		List<Livro> list = criteria.list();
		em.close();
		return list;
	}
}
