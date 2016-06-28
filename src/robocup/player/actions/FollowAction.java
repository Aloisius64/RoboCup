package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjBall;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

public class FollowAction extends GoapAction {

	private boolean follow = false;

	public FollowAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		// addPrecondition(GoapGlossary.BEHIND_BALL_LINE, true);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, true);
		// addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, true);
		addPrecondition(GoapGlossary.BALL_CATCHED, false);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
	}

	@Override
	public void reset() {
		follow = false;

	}

	@Override
	public boolean isDone() {
		return follow;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;
		ObjBall ball = player.getMemory().getBall();
		if (ball != null) {
			Position ballPos = player.getMemory().getBallPos(ball);
			if (player.getPosition().x >= ballPos.x + 20) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;
		if (!player.getAction().isPlayMode("play_on"))
			return false;
		try {
			follow = true;
			ObjBall ball = player.getMemory().getBall();
			if (ball != null) {
				Position ballPos = player.getMemory().getBallPos(ball);
				Position playerPos = player.getPosition();
				if (playerPos.x >= ballPos.x + 20 || ballPos.x < 0.0) {
					return false;
				}
			}
			if (player.getMemory().getBall() == null) {
				player.getAction().turn(30.0);
				return true;
			} else {

				player.getAction().goToPoint(
						new Position(player.getMemory().getBallPos(ball).x + 24, player.getMemory().getHome().y));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
