package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

/*
PASS_BALL_GOALIE **********************************
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHED (TRUE)

	EFFECTS:
		KEEP_AREA_SAFE (TRUE)

	THINGS TO CHECK:
		Presenza di un certo numero di avversari
		vicino la porta. Se tale numero non è 
		troppo elevato (minore o circa i propri
		difensori)

	PERFORMING:
		Il portiere passa la palla al difensore
		più libero
 */
public class PassBallGoalieAction extends GoapAction {

	private boolean ballPassed = false;

	public PassBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
	}

	@Override
	public void reset() {
		ballPassed = false;
	}

	@Override
	public boolean isDone() {
		return ballPassed = false;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		Presenza di un certo numero di avversari
		//		vicino la porta. Se tale numero non è 
		//		troppo elevato (minore o circa i propri
		//		difensori)
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il portiere passa la palla al difensore
		//		più libero
		return false;
	}

}
