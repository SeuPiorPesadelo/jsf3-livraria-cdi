package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	//� um controle de versionamento desse bean
	private static final long serialVersionUID = -6391543426027494349L;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario = new Usuario();
	@Inject
	private FacesContext fc;

	public Usuario getUsuario() {
		return usuario;
	}

	public RedirectView efetuarLogin(){
		boolean existe = usuarioDao.existe(this.usuario);
		fc = FacesContext.getCurrentInstance();
		if(existe){
			//guarda os dados do usuario na chave usuarioLogado
			fc.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new RedirectView("livro");
		}
		//� um boa pr�tica usar o ?faces-redirect=true p/ navegar p/ outra p�gina, ou seja,
		//fazer um redirecionamento ap�s submeter um formul�rio, para limpar os dados da requisi��o,
		//entao, p/ manter a mensagem global � usado getFlash() para durar nas duas
		//requisicoes q ser�o geradas por causa do ?faces-redirect=true
		fc.getExternalContext().getFlash().setKeepMessages(true);
		//primeiro param � o id do componente q a msg ser� vinculada
		//no caso � null, entao, essa msg ser� global, estar� vinculada ao h:messages globalOnly="true"
		fc.addMessage(null, new FacesMessage("Usu�rio n�o encontrado"));
		return new RedirectView("login");
	}

	public RedirectView deslogar(){
		fc = FacesContext.getCurrentInstance();
		//remove o usuario logado
		fc.getExternalContext().getSessionMap().remove("usuarioLogado");
		return new RedirectView("login");
	}
}
