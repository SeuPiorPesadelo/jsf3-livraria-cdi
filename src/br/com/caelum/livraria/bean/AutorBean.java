package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();
	private List<Autor> autores = new DAO<Autor>(Autor.class).listaTodos();

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		System.out.println("Chamou getAutores()");
		if (autores == null) {
			autores = new DAO<Autor>(Autor.class).listaTodos();
		}
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		new DAO<Autor>(Autor.class).adiciona(this.autor);
		// ?faces-redirect=true faz a URI atualizar
		// pq força um segundo request p/ o xhtml correspondente
		// return "livro?faces-redirect=true";
		// return new RedirectView("livro");
		autores.add(this.autor);
		this.autor = new Autor();
	}

	public RedirectView vaiPraLivros() {
		return new RedirectView("livro");
	}

	public void deletaAutores(Autor a) {
		new DAO<Autor>(Autor.class).remove(a);
		System.out.println("Atribuiu nulo");
		autores = null;
	}
}
