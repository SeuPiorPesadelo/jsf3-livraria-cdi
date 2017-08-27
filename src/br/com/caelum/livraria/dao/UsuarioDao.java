package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable{

	private static final long serialVersionUID = 7433487415751388021L;
	@Inject
	private EntityManager em;
	private DAO<Usuario> dao;

	// assim q ele Injeta esse EntityManager
	// tbm inicializa o DAO desse UsuarioDao
	@PostConstruct
	void init() {
		this.dao = new DAO<Usuario>(this.em, Usuario.class);
	}
	
	public boolean existe(Usuario usuario) {
		TypedQuery<Usuario> query =  dao.getEntityManager().createQuery("SELECT u from Usuario u WHERE u.email like :email AND u.senha like :senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		Usuario result = null;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			return false;
		}
		//@Inject já cuida p/ q o EM seja fechado
//		} finally {
//			em.close();
//		}
		return result != null;
	}

}
