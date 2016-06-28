package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;

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
public class SearchBallDefensorAction extends GoapAction {

	private boolean ballSeen = false;

	public SearchBallDefensorAction() {
		super(1.0f);
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

		AbstractPlayer player = ((AbstractPlayer) agent);
		if(!player.getAction().isPlayMode("play_on"))
			return false;
		try {
			if (player.getAction().isBallVisible()) {

				ballSeen = true;
				return true;
			} else {
				player.getRoboClient().turn(60);

			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return true;
	}

}
