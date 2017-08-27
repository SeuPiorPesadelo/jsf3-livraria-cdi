package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	//é um controle de versionamento desse bean
	private static final long serialVersionUID = 8614028697905324892L;
	
	private BarChartModel vendasModel;

	public List<Venda> getVendas(long seed) {
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();
		Random r = new Random(seed);
		for (Livro l : livros) {
			Integer quantidade = r.nextInt(500);
			vendas.add(new Venda(l, quantidade));
		}
		return vendas;
	}

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2017");
		for (Venda v : getVendas(123)) {
			vendaSerie.set(v.getLivro().getTitulo(), v.getQuantidade());
		}

		ChartSeries vendaSerie1 = new ChartSeries();
		vendaSerie1.setLabel("Vendas 2016");
		for (Venda v : getVendas(100)) {
			vendaSerie1.set(v.getLivro().getTitulo(), v.getQuantidade());
		}

		model.addSeries(vendaSerie);
		model.addSeries(vendaSerie1);

		return model;
	}
}
