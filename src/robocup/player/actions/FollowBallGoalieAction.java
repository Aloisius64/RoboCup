package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

/*
FOLLOW_BALL_GOALIE ********************************
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHABLE (FALSE)

	EFFECTS:
		BALL_CATCHABLE (TRUE)

	THINGS TO CHECK:
		La palla è controllata dall avversario o comunque
		non è in una zona in cui il portiere è in grado
		di catturarla.

	PERFORMING:
		Il portiere segue i movimenti della palla, minimizzando
		la distanza per cui è possibile catturare la palla
 */
public class FollowBallGoalieAction extends GoapAction {

	private boolean ballFollowed = false;

	public FollowBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHABLE, false);
		addEffect(GoapGlossary.BALL_CATCHABLE, true);
	}

	@Override
	public void reset() {
		ballFollowed = false;
	}

	@Override
	public boolean isDone() {
		return ballFollowed;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		La palla è controllata dall avversario o comunque
		//		non è in una zona in cui il portiere è in grado
		//		di catturarla.
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		if(ballIsControlledByOpponent){
		//			if(!ballCatcheable)
		//				goToLocationInGoalieAreaWichIsNearBall;
		//			if(ballCatcheable)
		//				ballFollowed = true;
		//			return true;
		//		}	
		//		return false;
		return false;
	}

}
