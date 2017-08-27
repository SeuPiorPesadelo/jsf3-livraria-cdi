package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

//como este bean é sessionScope ele sebreviverá
//durante o tempo q o usuario estiver logado,
//logo, nao mudará de tema
@Named
@SessionScoped //javax.enterprise.context.SessionScoped é do mundo CDI
public class TemaBean implements Serializable{

	//é um controle de versionamento desse bean
	private static final long serialVersionUID = -1866613777010698977L;
	
	private String tema = "bootstrap";

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
	
	public String[] getTemas() {
	    return new String[] { "afterdark", "afternoon", "afterwork", "aristo",
	            "black-tie", "blitzer", "bluesky", "bootstrap", "casablanca",
	            "cupertino", "cruze", "dark-hive", "delta", "dot-luv",
	            "eggplant", "excite-bike", "flick", "glass-x", "home",
	            "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc",
	            "overcast", "pepper-grinder", "redmond", "rocket", "sam",
	            "smoothness", "south-street", "start", "sunny", "swanky-purse",
	            "trontastic", "ui-darkness", "ui-lightness", "vader" };
	}
	
}
