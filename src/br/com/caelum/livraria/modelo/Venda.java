package br.com.caelum.livraria.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Venda {

	@Id
	@GeneratedValue
	private Integer id;
	@ManyToOne
	private Livro livro;
	private int quantidade;

	public Venda(){
	}
	
	public Venda(Livro livro, int quantidade) {
		super();
		this.livro = livro;
		this.quantidade = quantidade;
	}

	public Integer getId() {
		return id;
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
