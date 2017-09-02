package br.com.caelum.livraria.log;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TempoDasTransacoes implements Serializable {

	private static final long serialVersionUID = -1787180465255492995L;

	@AroundInvoke
	public Object executaTransacao(InvocationContext contexto) {
		long inicio = System.currentTimeMillis();
        String metodo = contexto.getMethod().getName();
        String nomeClass = contexto.getTarget().getClass().getName();
        Object proceder = null;
		try {
			proceder = contexto.proceed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long fim = System.currentTimeMillis();
        long resultado = fim - inicio;
        System.out.println("Método executado: " + metodo + "em: " + resultado + "mls da classe: " + nomeClass);
        return proceder;  
	}

}
