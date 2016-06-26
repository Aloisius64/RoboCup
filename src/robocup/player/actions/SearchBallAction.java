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

		AbstractPlayer player = ((AbstractPlayer) agent);

//		System.out.println("Performing "+getClass().getSimpleName());

		try {
			if(player.getAction().isBallVisible()){

//				ObjBall ball = player.getMemory().getBall();

//				if ((ball.getDirection() > 5.0 
//						|| ball.getDirection() < -5.0)) {
//					player.getRoboClient().turn(ball.getDirection());
//				}

				ballSeen = true;
			}
			player.getRoboClient().turn(30);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return true;
	}

}
