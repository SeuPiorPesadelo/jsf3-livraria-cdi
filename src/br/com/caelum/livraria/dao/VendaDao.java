package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.log.Log;
import br.com.caelum.livraria.modelo.Venda;

public class VendaDao implements Serializable {

	private static final long serialVersionUID = 1732420846784632146L;
	@Inject
	private EntityManager em;
	private DAO<Venda> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Venda>(this.em, Venda.class);
	}
	
	@Log
	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}
}
