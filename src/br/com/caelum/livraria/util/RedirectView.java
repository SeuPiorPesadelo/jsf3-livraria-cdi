package br.com.caelum.livraria.util;

//classe q serva para concatenar ?faces-redirect=true
//p/ facilitar as navegacoes entre paginas
public class RedirectView {

	private String viewName;

	public RedirectView(String viewName) {
		this.viewName = viewName;
	}

	// ?faces-redirect=true faz a URI atualizar
	// pq força um segundo request p/ o xhtml correspondente
	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}
	
}
