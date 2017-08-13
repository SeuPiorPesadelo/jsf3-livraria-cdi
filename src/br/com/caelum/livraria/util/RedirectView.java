package br.com.caelum.livraria.util;

//classe q serva para concatenar ?faces-redirect=true
//p/ facilitar as navegacoes entre paginas
public class RedirectView {

	private String viewName;

	public RedirectView(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}
	
}
