package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 9039601385787424895L;

	// é chamado depois de uma fase
	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("Fase: " + event.getPhaseId() + " do Autenticador");
		
		FacesContext fc = event.getFacesContext();
		
		// pega a arvore de componentes p/ pegar o nome da pag
		String nomeDaPagina = fc.getViewRoot().getViewId();
		if (nomeDaPagina.equals("/login.xhtml")) {
			return; // continua as outras fases normalmente
		}
		
		// tenta pegar do HttpSession o usuario pela chave usuarioLogado
		Usuario user = (Usuario) fc.getExternalContext().getSessionMap().get("usuarioLogado");
		if (user != null) {
			return;// usuario está logado
		}
		
		// faz o redireionamento p/ login.xhtml
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, "/login?faces-redirect=true");
		// pula as outras fases e renderiza a resposta
		fc.getRenderResponse();
	}

	@Override
	public PhaseId getPhaseId() {
		// executa somente na primeira fase do ciclo de vida
		return PhaseId.RESTORE_VIEW;
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}
}
