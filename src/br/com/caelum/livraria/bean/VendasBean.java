package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	//é um controle de versionamento desse bean
	private static final long serialVersionUID = 8614028697905324892L;
	@Inject
	private VendaDao vendaDao;
	private List<Venda> vendas;

	public List<Venda> getVendas() {
		if(vendas == null){
			vendas = vendaDao.listaTodos();
		}
		return vendas;
	}

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2017");
		for (Venda v : getVendas()) {
			vendaSerie.set(v.getLivro().getTitulo(), v.getQuantidade());
		}
		model.addSeries(vendaSerie);
		return model;
	}
}
