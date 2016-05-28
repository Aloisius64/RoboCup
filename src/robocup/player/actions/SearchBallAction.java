package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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

	private boolean ballSeen = false;

	public SearchBallAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_SEEN, false);
		addEffect(GoapGlossary.BALL_SEEN, true);
	}

	@Override
	public void reset() {
		ballSeen = false;
	}

	@Override
	public boolean isDone() {
		return ballSeen;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		// L'attaccante sa dove si trova la palla
		return false;
	}

}
