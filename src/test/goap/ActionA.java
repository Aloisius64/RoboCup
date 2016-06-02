package test.goap;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

public class ActionA extends GoapAction {

	private boolean done = false;
	
	public ActionA() {
		super(1.0f);
		addPrecondition(GoapGlossary.P_A, false);
		addPrecondition(GoapGlossary.P_A_1, false);
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
		System.out.println("Performing ActionA");
		done = true;
		return true;
	}

}
