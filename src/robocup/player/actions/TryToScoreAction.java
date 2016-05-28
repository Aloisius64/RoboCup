package robocup.player.actions;

import robocup.goap.GoapAction;

/*
TRY_TO_SCORE **************************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_CATCHED (TRUE)

EFFECTS:
	TRY_TO_SCORE (TRUE)
	BALL_CATCHED (FALSE)

THINGS TO CHECK:

PERFORMING:
	Il giocatore prova a tirare, qui usiamo la 
	funzione smart kick.
 */
public class TryToScoreAction extends GoapAction {

	public TryToScoreAction() {
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
