package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;

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

	private boolean playerMarked = false;

	public MarkPlayerAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addPrecondition(GoapGlossary.BALL_NEAR, false);
		addPrecondition(GoapGlossary.NEAR_NON_CONTROLLED_BALL_OPPONENT, false);		
		addEffect(GoapGlossary.NEAR_NON_CONTROLLED_BALL_OPPONENT, true);
	}

	@Override
	public void reset() {
		playerMarked = false;
	}

	@Override
	public boolean isDone() {
		return playerMarked;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		Il difensore è ad una distanza maggiore ripetto
		//		all'azione DEFENSOR_STOLE_BALL
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		//		Il difensore segue gli spostamenti dell'avversario
		return false;
	}

}
