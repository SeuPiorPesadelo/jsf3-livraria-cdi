package br.com.caelum.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("livraria");

	//quanto tiver um @Inject de EntityManager o ...
	@Produces//@Produces dir� pro CDI q � esse metodo q produzir� o EM
	@RequestScoped//@RequestScoped - produzir� a cada requisicao
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	//@Disposes diz pro CDI q esse metodo fechar� o EM depois
	//de cada requisicao
	public void close(@Disposes EntityManager em) {
		em.close();
	}
}
