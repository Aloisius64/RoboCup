package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

/*
ATTACKER_STOLE_BALL *******************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_CATCHED (FALSE)

EFFECTS:
	BALL_CATCHED (TRUE)

THINGS TO CHECK:
	La palla è controllata da un avversario o
	non, basta che sia nella propria metà campo,
	ed è ad una certa distanza dal difensore

PERFORMING:
	Il difensore si avvicina alla palla è cerca
	di recuperarla
 */
public class StoleBallAttackerAction extends GoapAction {

	private boolean ballStoled = false;

	public StoleBallAttackerAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
	}

	@Override
	public void reset() {
		ballStoled = false;
	}

	@Override
	public boolean isDone() {
		return ballStoled;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		La palla è controllata da un avversario o
		//		non, basta che sia nella propria metà campo,
		//		ed è ad una certa distanza dal difensore
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il difensore si avvicina alla palla è cerca
		//		di recuperarla
		return false;
	}

}
