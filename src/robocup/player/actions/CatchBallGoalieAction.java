package robocup.player.actions;

import robocup.goap.GoapAction;

/*
CATCH_BALL_GOALIE *********************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHABLE (TRUE)
	BALL_CATCHED (FALSE)

EFFECTS:
	BALL_CATCHED (TRUE)	

THINGS TO CHECK:

PERFORMING:
	Il portiere esegue l'azione di cattura
 */
public class CatchBallGoalieAction extends GoapAction {

	public CatchBallGoalieAction() {
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
