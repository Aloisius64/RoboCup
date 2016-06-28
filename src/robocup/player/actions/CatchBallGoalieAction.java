package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;

/*
CATCH_BALL_GOALIE *********************************
PRECONDITIONS:
	KEEP_AREA_SAFE (FALSE)
	BALL_CATCHABLE (TRUE)
	BALL_CATCHED (FALSE)

EFFECTS:
	BALL_CATCHED (TRUE)	

THINGS TO CHECK:

PERFORMING:
	Il portiere esegue l'azione di cattura
 */
public class CatchBallGoalieAction extends GoapAction {

	private boolean ballCatched = false;

	public CatchBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHABLE, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.BALL_CATCHED, true);
	}

	@Override
	public void reset() {
		ballCatched = false;
	}

	@Override
	public boolean isDone() {
		return ballCatched;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {

//		System.out.println("Performing " + getClass().getSimpleName());

		AbstractPlayer player = (AbstractPlayer) agent;
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		try {
			if (player.getAction().isBallVisible() && player.getAction().isBallInRangeOf(1.0)) {
				player.getAction().catchball(player.getMemory().getBall().getDirection());
				ballCatched = true;
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return false;
	}

}
