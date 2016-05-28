package robocup.player.actions;

import robocup.goap.GoapAction;

/*
MARK_PLAYER ***************************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHED (FALSE)
	BALL_NEAR (FALSE)
	NEAR_NON_CONTROLLED_BALL_OPPONENT (FALSE)

EFFECTS:
	NEAR_NON_CONTROLLED_BALL_OPPONENT (TRUE)

THINGS TO CHECK:
	Il difensore è ad una distanza maggiore ripetto
	all'azione DEFENSOR_STOLE_BALL

PERFORMING:
	Il difensore segue gli spostamenti dell'avversario
 */
public class MarkPlayerAction extends GoapAction {

	public MarkPlayerAction() {
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
