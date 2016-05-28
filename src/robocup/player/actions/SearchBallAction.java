package robocup.player.actions;

import robocup.goap.GoapAction;

/*
SEARCH_BALL ***************************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_SEEN (FALSE)

EFFECTS:
	BALL_SEEN (TRUE)

THINGS TO CHECK:

PERFORMING:
	L'attaccante sa dove si trova la palla
 */
public class SearchBallAction extends GoapAction {

	public SearchBallAction() {
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
