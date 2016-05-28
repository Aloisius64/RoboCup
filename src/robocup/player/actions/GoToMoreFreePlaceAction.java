package robocup.player.actions;

import robocup.goap.GoapAction;

/*
GO_TO_MORE_FREE_PLACE *****************************
PRECONDITIONS:
	BALL_CATCHED_FROM_GOALIER (TRUE)

EFFECTS:
	// Per ora non so cosa può comportare

THINGS TO CHECK:
	Il portiere controlla la palla

PERFORMING:
	Il difensore si muove in una zona del campo
	sempre nella propria metà campo, in cui la 
	distanza rispetto agli avversari vicini è
	maggiore
 */
public class GoToMoreFreePlaceAction extends GoapAction {

	public GoToMoreFreePlaceAction() {
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
