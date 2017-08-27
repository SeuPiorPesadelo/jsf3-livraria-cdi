package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

//@ManagedBean é do mundo JSF
//@ViewScoped NÃO funciona com CDI
//@Named é do mundo CDI
//javax.faces.view.ViewScoped é do CDI
@Named
@ViewScoped
public class AutorBean implements Serializable {
	//CDI exije implements Serializable
	
	//é um controle de versionamento desse bean
	private static final long serialVersionUID = -6383412006308490877L;
	//quem cuidará dessa instancia será o CDI
	@Inject
	private AutorDao autorDao;//CDI faz new AutorDao() por debaixo dos panos
	
	private Integer autorId;
	private Autor autor = new Autor();
	private List<Autor> autores;
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		System.out.println("Chamou getAutores()");
		if (autores == null) {
			autores = autorDao.listaTodos();
		}
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());
		if(this.autor.getId() == null){
			autorDao.adiciona(this.autor);
			autores.add(this.autor);
		} else {
			autorDao.atualiza(this.autor);
			autores = null;
		}
		this.autor = new Autor();
	}

	public RedirectView vaiPraLivros() {
		return new RedirectView("livro");
	}

	public void deletaAutores(Autor a) {
		autorDao.remove(a);
		System.out.println("Atribuiu nulo");
		autores = null;
	}
	
	
	public void carregar(Autor a){
		System.out.println("carregou");
		this.autor = a;
	}
	
	public void carregarAutorPeloId(){
		this.autor = autorDao.buscaPorId(autorId);
	}
}
