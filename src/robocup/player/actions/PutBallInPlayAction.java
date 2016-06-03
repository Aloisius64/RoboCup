package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;

/*
PUT_BALL_IN_PLAY *********************************
PRECONDITIONS:
	PUT_BALL_IN_PLAY (FALSE)
	BALL_CATCHED (FALSE)

EFFECTS:
	PUT_BALL_IN_PLAY (TRUE)

THINGS TO CHECK:

PERFORMING:
	Il giocatore (portiere) mette la palla in gioco
 */
public class PutBallInPlayAction extends GoapAction {

	private boolean ballInPlay = false;

	public PutBallInPlayAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.PUT_BALL_IN_PLAY, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.PUT_BALL_IN_PLAY, true);
	}

	@Override
	public void reset() {
		ballInPlay = false;
	}

	@Override
	public boolean isDone() {
		return ballInPlay;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {

		System.out.println("Performing "+getClass().getSimpleName());

		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			if(player.getMemory().isObjVisible("ball") && player.getAction().isBallInRangeOf(1.0)){
				Thread.sleep(500);
				player.getAction().turn(-player.getMemory().getDirection());
				Thread.sleep(200);
				player.getAction().kick(100, 0);
				
				ballInPlay = true;
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return false;
	}

}
