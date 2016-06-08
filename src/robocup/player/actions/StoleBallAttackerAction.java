package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;

/*
ATTACKER_STOLE_BALL *******************************
PRECONDITIONS:
	TRY_TO_SCORE (FALSE)
	BALL_CATCHED (FALSE)

EFFECTS:
	BALL_CATCHED (TRUE)

THINGS TO CHECK:
	La palla � controllata da un avversario o
	non, basta che sia nella propria met� campo,
	ed � ad una certa distanza dal difensore

PERFORMING:
	Il difensore si avvicina alla palla � cerca
	di recuperarla
 */
public class StoleBallAttackerAction extends GoapAction {

	private boolean ballStoled = false;

	public StoleBallAttackerAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
		addPrecondition(GoapGlossary.BALL_NEAR_OPPONENT, true);
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
		// La palla � controllata da un avversario o
		// non, basta che sia nella propria met� campo,
		// ed � ad una certa distanza dal difensore
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		// Il difensore si avvicina alla palla � cerca
		// di recuperarla

		AbstractPlayer player = (AbstractPlayer) agent;

//		System.out.println("Performing " + getClass().getSimpleName());

		try {
			if (player.getAction().isBallVisible()) {
				ObjBall ball = player.getMemory().getBall();
				System.out.println("Player " + player.getAction().inFieldPlayer() + " Ball "
						+ player.getAction().inFieldBall(ball));
				if (!player.getAction().inFieldBall(ball)) {

					return false;
				}

				if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
					player.getRoboClient().turn(ball.getDirection());
				}

				if (ball.getDistance() > 0.7) {
					Polar p = MathHelp.getNextBallPoint(ball);
					Position p2 = MathHelp.getPos(p);
					if ((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36))
						return false;
					else if (player.getAction().stayInBounds()) {
						player.getAction().goToPoint(p);
					}
					return true;

				} else if (ball.getDistance() <= 0.7) {
					ballStoled = true;
				}

				return true;

			} else {
				player.getRoboClient().turn(30);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
