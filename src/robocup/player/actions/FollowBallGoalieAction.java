package robocup.player.actions;

import java.net.UnknownHostException;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Position;

/*
FOLLOW_BALL_GOALIE ********************************
	PRECONDITIONS:
		KEEP_AREA_SAFE (FALSE)
		BALL_CATCHABLE (FALSE)

	EFFECTS:
		BALL_CATCHABLE (TRUE)

	THINGS TO CHECK:
		La palla è controllata dall avversario o comunque
		non è in una zona in cui il portiere è in grado
		di catturarla.

	PERFORMING:
		Il portiere segue i movimenti della palla, minimizzando
		la distanza per cui è possibile catturare la palla
 */
public class FollowBallGoalieAction extends GoapAction {

	private boolean ballFollowed = false;
	private Position upper = new Position(-49, -6);
	private Position middle = new Position(-49, 0);
	private Position lower = new Position(-49, 6);

	public FollowBallGoalieAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_CATCHABLE, false);
		addEffect(GoapGlossary.BALL_CATCHABLE, true);
	}

	@Override
	public void reset() {
		ballFollowed = false;
	}

	@Override
	public boolean isDone() {
		return ballFollowed;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		//		La palla è controllata dall avversario o comunque
		//		non è in una zona in cui il portiere è in grado
		//		di catturarla.
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		System.out.println("Performing "+getClass().getSimpleName());

		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			if (!player.getMemory().isObjVisible("ball")) {
				player.getAction().turn(30);
				return false;
			} else {
				ObjBall ball = player.getMemory().getBall();

				if ((ball.getDirection() > 5.0) || (ball.getDirection() < -5.0)) {
					player.getAction().turn(ball.getDirection() * (1 + (5 * player.getMemory().getAmountOfSpeed())));
				}

				if (player.getAction().ballInGoalzone(ball)) {
					if (ball.getDistance() > 1.0) {
						player.getAction().gotoPoint(MathHelp.getNextBallPoint(ball));
					} else {
						ballFollowed = true;
					}
				} else {
					Position ballPos = MathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
					ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

					if (ballPos.y < -18) {
						player.getAction().gotoSidePoint(upper);
					} else if (ballPos.y > -18 && ballPos.y < 18) {
						player.getAction().gotoSidePoint(middle);
					} else {
						player.getAction().gotoSidePoint(lower);
					}
				}
			}
		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
