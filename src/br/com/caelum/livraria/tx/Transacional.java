package br.com.caelum.livraria.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

//criamos um própria anotação p/ dizer ao CDI
//q será necessário abrir e fechar uma transacao
@InterceptorBinding//@InterceptorBinding é usada p/ acionar o gerenciador de transacoes do CDI
@Target({ElementType.METHOD, ElementType.TYPE})//@Target diz q será usado apenas em metodos, ElementType.TYPE diz q será usado apenas em classes
@Retention(RetentionPolicy.RUNTIME)//será válida apenas na hr de rodar
public @interface Transacional {

}
