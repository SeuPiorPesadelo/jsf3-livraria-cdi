package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
//@RequestScoped //é o padrão
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();

	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		if (livro.getAutores().isEmpty()) {
			//p/ personalizacao de mensagem é necessário no 1º param o client Id, e no 2º a mensagem
			FacesContext.getCurrentInstance().addMessage("autor",  new FacesMessage("Livro deve ter pelo menos um autor"));
		}
		new DAO<Livro>(Livro.class).adiciona(this.livro);
		livro = new Livro();
		livros = null;
	}

	public void gravarAutor(){
		Autor buscaPorId = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		livro.adicionaAutor(buscaPorId);
	}
	
	public List<Autor> getAutoresDoLivro(){
		return this.livro.getAutores();
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	//FacesContext obtem info. da view processada no momento
	//UIComponent pega o componente q está sendo validado
	//Object pega o valor q o usuario digitou
	public void comecaComDigitoUm(FacesContext ctx, UIComponent comp, Object value) throws ValidatorException{
		String v = value.toString();
		if(!v.startsWith("1")){
			//ValidatorException diz ao JSF q algo deu errado
			throw new ValidatorException(new FacesMessage("Deve começar com 1"));
		}
	}

	public List<Livro> getLivros() {
		if(livros == null){
			livros = new DAO<Livro>(Livro.class).listaTodos();
		}
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	
	public RedirectView formAutor(){
		System.out.println("Executou o formAutor");
		//?faces-redirect=true faz a URI atualizar
		//pq força um segundo request p/ o xhtml correspondente
//		return "autor?faces-redirect=true";
		return new RedirectView("autor");
	}
}
