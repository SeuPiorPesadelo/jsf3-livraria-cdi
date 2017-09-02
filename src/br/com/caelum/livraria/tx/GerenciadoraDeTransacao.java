package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

//Classe responsavel por centralizar as transacoes.
//Um interceptador nao funciona so com anotacoes,
//tbm é preciso deixar explicito no beans.xml

//@Transacional diz q esta será a classe a ser associada com as transacoes
@Transacional
@Interceptor//@Interceptor diz q esta classe fará uma coisa antes e outra depois, no caso, em.begin() e em.commit()
public class GerenciadoraDeTransacao implements Serializable{
	
	private static final long serialVersionUID = -6810496670658056558L;
	@Inject
	private EntityManager em;

	//@AroundInvoke diz p/ o CDI q este metodo é q tem q ser chamado em
	//casa interceptacao
	@AroundInvoke
	public Object executaTransacao(InvocationContext contexto){
		//InvocationContext pega o contexto (o método q chamou este método)
		
		//abre transacao
		em.getTransaction().begin();

		Object o = null;
		try {
			//chama os DAOs q precisam de uma transacao
			//quem fará isso será o CDI
			o = contexto.proceed();//continua executando
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//commit da transacao
		em.getTransaction().commit();
		return o;
	}
}
