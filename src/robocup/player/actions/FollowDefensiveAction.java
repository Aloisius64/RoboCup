package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

public class FollowDefensiveAction extends GoapAction {

	private boolean follow = false;

	public FollowDefensiveAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, false);
		addPrecondition(GoapGlossary.BALL_SEEN, true);
		addPrecondition(GoapGlossary.BALL_IN_DEFENSIVE_AREA, true);
		// addPrecondition(GoapGlossary.BEHIND_BALL_LINE, true);
		addPrecondition(GoapGlossary.BALL_NEAR_DEFENDER, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
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

		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			follow = true;
			if (player.getMemory().getBall() != null) {

				Position ballPos = player.getMemory().getBallPos(player.getMemory().getBall());
				player.getAction().goToSidePoint(new Position(ballPos.x, player.getMemory().getHome().y));
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
