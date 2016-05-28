package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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

	private boolean ballCatched = false;
	
	public CatchBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHABLE, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
	}

	@Override
	public void reset() {
		ballCatched = false;
	}

	@Override
	public boolean isDone() {
		return ballCatched;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {
//		if(isBallCatcheable){
//			ballCatched = catchTheBall;
//			return true;
//		}
//		return false;
		return false;
	}

}
