package test.goap;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

public class ActionC extends GoapAction {

	private boolean done = false;
	
	public ActionC() {
		super(1.0f);
		addPrecondition(GoapGlossary.P_A, false);
		addEffect(GoapGlossary.P_B, true);
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
		System.out.println("Performing ActionC");
		done = true;
		return true;
	}

}
