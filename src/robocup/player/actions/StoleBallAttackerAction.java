package robocup.player.actions;

import robocup.goap.GoapAction;

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

	public StoleBallAttackerAction() {
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
