package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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

	private boolean tryToScore = false;

	public TryToScoreAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, true);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
		addEffect(GoapGlossary.BALL_CATCHED, false);
	}

	@Override
	public void reset() {
		tryToScore = false;
	}

	@Override
	public boolean isDone() {
		return tryToScore;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il giocatore prova a tirare, qui usiamo la 
		//		funzione smart kick.
		return false;
	}

}
