package br.com.caelum.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LogPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 9039601385787424895L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		//é chamado depois de uma fase
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		//é chamado antes de uma fase
		System.out.println("Fase: " + arg0.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		//pega todas as fases do ciclo de vida
		return PhaseId.ANY_PHASE;
	}
	
	

}
