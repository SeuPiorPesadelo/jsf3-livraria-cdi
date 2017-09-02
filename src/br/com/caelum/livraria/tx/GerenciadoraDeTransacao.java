package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

//Classe responsavel por centralizar as transacoes.
//Um interceptador nao funciona so com anotacoes,
//tbm � preciso deixar explicito no beans.xml

//@Transacional diz q esta ser� a classe a ser associada com as transacoes
@Transacional
@Interceptor//@Interceptor diz q esta classe far� uma coisa antes e outra depois, no caso, em.begin() e em.commit()
public class GerenciadoraDeTransacao implements Serializable{
	
	private static final long serialVersionUID = -6810496670658056558L;
	@Inject
	private EntityManager em;

	//@AroundInvoke diz p/ o CDI q este metodo � q tem q ser chamado em
	//casa interceptacao
	@AroundInvoke
	public Object executaTransacao(InvocationContext contexto){
		//InvocationContext pega o contexto (o m�todo q chamou este m�todo)
		
		//abre transacao
		em.getTransaction().begin();

		Object o = null;
		try {
			//chama os DAOs q precisam de uma transacao
			//quem far� isso ser� o CDI
			o = contexto.proceed();//continua executando
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//commit da transacao
		em.getTransaction().commit();
		return o;
	}
}
