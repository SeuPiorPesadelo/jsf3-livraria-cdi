package br.com.caelum.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao {

	private EntityManager em;
	
	public boolean existe(Usuario usuario) {
		em = new DAO<Usuario>(Usuario.class).getEntityManager();
		TypedQuery<Usuario> query =  em.createQuery("SELECT u from Usuario u WHERE u.email like :email AND u.senha like :senha", Usuario.class);
		query.setParameter("email", usuario.getEmail());
		query.setParameter("senha", usuario.getSenha());
		Usuario result = null;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
		return result != null;
	}

}
