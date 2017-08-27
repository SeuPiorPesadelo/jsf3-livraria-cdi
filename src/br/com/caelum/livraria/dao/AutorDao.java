package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {
	
	private static final long serialVersionUID = -5879076958191210480L;
	@Inject
	private EntityManager em;
	private DAO<Autor> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public void adiciona(Autor autor) {
		dao.adiciona(autor);
	}

	public void atualiza(Autor autor) {
		dao.atualiza(autor);
	}

	public void remove(Autor a) {
		dao.remove(a);
	}

	public Autor buscaPorId(Integer autorId) {
		return dao.buscaPorId(autorId);
	}
}
