package br.com.caelum.livraria.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

//criamos um pr�pria anota��o p/ dizer ao CDI
//q ser� necess�rio abrir e fechar uma transacao
@InterceptorBinding//@InterceptorBinding � usada p/ acionar o gerenciador de transacoes do CDI
@Target({ElementType.METHOD, ElementType.TYPE})//@Target diz q ser� usado apenas em metodos, ElementType.TYPE diz q ser� usado apenas em classes
@Retention(RetentionPolicy.RUNTIME)//ser� v�lida apenas na hr de rodar
public @interface Transacional {

}
