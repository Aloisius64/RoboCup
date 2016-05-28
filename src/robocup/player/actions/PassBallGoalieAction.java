package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public PassBallGoalieAction() {
		super(1.0f);
		//		addPrecondition(key, value);
		//		addEffect(key, value);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean perform(Object agent) {
		// TODO Auto-generated method stub
		return false;
	}

}
