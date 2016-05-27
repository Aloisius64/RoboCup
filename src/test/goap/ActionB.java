package test.goap;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

public class ActionB extends GoapAction {

	private boolean done = false;
	
	public ActionB() {
		super();
		addPrecondition(GoapGlossary.P_B, true);
		addEffect(GoapGlossary.GOAL, true);
	}
	
	@Override
	public void reset() {
		done = false;		
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		System.out.println("Performing ActionB");
		done = true;
		return true;
	}
	
}
