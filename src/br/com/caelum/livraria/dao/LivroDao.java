package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import br.com.caelum.livraria.modelo.Livro;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = -8533001085927666735L;
	@Inject
	private EntityManager em;
	private DAO<Livro> dao;

	// assim q ele Injeta esse EntityManager
	// tbm inicializa o DAO desse LivroDao
	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);
	}

	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	public void adiciona(Livro livro) {
		dao.adiciona(livro);
	}

	public void atualiza(Livro livro) {
		dao.atualiza(livro);
	}

	public void remove(Livro l) {
		dao.remove(l);
	}

	public Livro carregarComAutores(Livro l) {
		// busca com join fetch, pq o relacionamento é lazy
		TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l JOIN FETCH l.autores a WHERE l.id = :id",
				Livro.class);
		query.setParameter("id", l.getId());
		return query.getResultList().get(0);
	}

	public Livro carregaPelaId(Integer livroId) {
		// busca com join fetch, pq o relacionamento é lazy
		TypedQuery<Livro> query = em.createQuery("SELECT l FROM Livro l JOIN FETCH l.autores a WHERE l.id = :id",
				Livro.class);
		query.setParameter("id", livroId);
		try {
			return query.getResultList().get(0);
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage("Passe um id válido"));
		}
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}

	public Session getSession() {
		EntityManager em = dao.getEntityManager();
		return em.unwrap(Session.class);
	}
}
