package br.com.caelum.livraria.modelo;

public class Venda {

	private Livro livro;
	private int quantidade;

	public Venda(Livro livro, int quantidade) {
		super();
		this.livro = livro;
		this.quantidade = quantidade;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
