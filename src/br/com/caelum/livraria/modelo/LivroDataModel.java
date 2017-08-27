package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.LivroDao;

public class LivroDataModel extends LazyDataModel<Livro> implements Serializable {

	private static final long serialVersionUID = -1597533916810271875L;
    @Inject
    private LivroDao livroDao;

//  @PostConstruct só é chamado depois que seu objeto já foi
//  construído, então passar a quantidade de linhas dentro do
//  construtor LivroDataModel() vai dar NullPointer
    @PostConstruct
    void init(){
        super.setRowCount(livroDao.quantidadeDeElementos());
    }
	
//	public LivroDataModel() {
//		vai dar NullPointer, coloque no @PostConstruct
//		super.setRowCount(livroDao.quantidadeDeElementos());
//	}
	
	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao,
			Map<String, Object> filtros) {
		Session session = livroDao.getSession();

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
		
		@SuppressWarnings("unchecked")
		List<Livro> list = criteria.list();
		return list;
	}
}
