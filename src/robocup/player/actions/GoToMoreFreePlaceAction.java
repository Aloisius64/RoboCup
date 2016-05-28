package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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

	private boolean moved = false;

	public GoToMoreFreePlaceAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.BALL_CATCHED_FROM_GOALIER, true);
		//		addEffect(key, value);
	}

	@Override
	public void reset() {
		moved = false;
	}

	@Override
	public boolean isDone() {
		return moved;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//Il portiere controlla la palla
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il difensore si muove in una zona del campo
		//		sempre nella propria metà campo, in cui la 
		//		distanza rispetto agli avversari vicini è
		//		maggiore
		return false;
	}

}
