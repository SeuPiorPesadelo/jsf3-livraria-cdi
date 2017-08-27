package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.RedirectView;

//javax.faces.bean.ViewScoped.ViewScoped NÃO funciona com CDI
//@Named é do mundo CDI
//javax.faces.view.ViewScoped é do CDI
@Named
@ViewScoped
public class LivroBean implements Serializable {

	//é um controle de versionamento desse bean
	private static final long serialVersionUID = 7382097856949769773L;
	
	private Integer livroId;
	private Livro livro = new Livro();
	private Integer autorId;
	private List<Livro> livros;
	private Livro livroSelecionado;
	@Inject
	private LivroDataModel livroDataModel = new LivroDataModel();
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	@Inject
	private LivroDao livroDao;
	@Inject
	private AutorDao autorDao;

	public List<String> getGeneros() {
	    return generos;
	}
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public Livro getLivroSelecionado() {
		return livroSelecionado;
	}

	public void setLivroSelecionado(Livro livroSelecionado) {
		this.livroSelecionado = livroSelecionado;
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		if (livro.getAutores().isEmpty()) {
			// p/ personalizacao de mensagem é necessário no 1º param o client
			// Id, e no 2º a mensagem
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um autor"));
		}
		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
		} else {
			livroDao.atualiza(this.livro);
		}
		livros = null;
		livro = new Livro();
		
//		faz resetar os valores do filtro da Criteria da dataTable de livros
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formTabelaLivros:tabelaLivros");
		dataTable.reset();
	}

	public void gravarAutor() {
		Autor buscaPorId = autorDao.buscaPorId(autorId);
		livro.adicionaAutor(buscaPorId);
	}
	
	public void remover(Livro l) {
		System.out.println("deletou");
		livroDao.remove(l);
		livros.remove(l);
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	// FacesContext obtem info. da view processada no momento
	// UIComponent pega o componente q está sendo validado
	// Object pega o valor q o usuario digitou
	public void comecaComDigitoUm(FacesContext ctx, UIComponent comp, Object value) throws ValidatorException {
		String v = value.toString();
		if (!v.startsWith("1")) {
			// ValidatorException diz ao JSF q algo deu errado
			throw new ValidatorException(new FacesMessage("Deve começar com 1"));
		}
	}

	public List<Livro> getLivros() {
		System.out.println("chamou getLivros()");
		if (livros == null) {
			livros = livroDao.listaTodos();
		}
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public RedirectView formAutor() {
		System.out.println("Executou o formAutor");
		// ?faces-redirect=true faz a URI atualizar
		// pq força um segundo request p/ o xhtml correspondente
		// return "autor?faces-redirect=true";
		return new RedirectView("autor");
	}

	public void removerAutorDoLivro(Autor a) {
		livro.removeAutor(a);
	}

	// primeiro parâmetro é o valor da coluna, o segundo é o filtro, o terceiro
	// define a locale
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale
		// tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();
		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);
		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}
		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}
		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o
			// value é menor do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;
		} catch (NumberFormatException e) {
			// usuario nao digitou um numero
			return false;
		}
	}
	
	public void carregar(Livro l) {
		System.out.println("carregou" + l);
		//carrega já com Autores
		this.livro = livroDao.carregarComAutores(l);
	}

	public void carregaPelaId() {
		this.livro = livroDao.carregaPelaId(livroId);
	}
}
