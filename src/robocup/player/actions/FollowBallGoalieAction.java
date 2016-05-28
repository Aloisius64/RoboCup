package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public FollowBallGoalieAction() {
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
