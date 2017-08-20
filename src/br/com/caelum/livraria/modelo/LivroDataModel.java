package br.com.caelum.livraria.modelo;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.caelum.livraria.dao.DAO;

//LazyDataModel
public class LivroDataModel extends LazyDataModel<Livro> {

	private DAO<Livro> dao = new DAO<Livro>(Livro.class);

	// Precisamos dizer ao LazyDataModel qual é o valor
	// máximo de registros que possuímos de livros
	public LivroDataModel() {
		super.setRowCount(dao.quantidadeDeElementos());
	}

	@Override
	public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao,
			Map<String, Object> filtros) {
		String titulo = (String) filtros.get("titulo");
		String isbn = (String) filtros.get("isbn");
		String preco = (String) filtros.get("preco");
		String genero = (String) filtros.get("genero");
		System.out.println("inicio: " + inicio + " quantidade: " + quantidade + ", titulo: " + titulo + ", genero: "
				+ genero + ", preco: " + preco + ", isbn: " + isbn);
		System.out.println("values "+filtros.values());
		//implementar filtro q trabalhe com lazy
		return dao.listaTodosPaginada(inicio, quantidade, "titulo", titulo);
	}
}
