package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

public class FollowAction extends GoapAction {

	private boolean follow = false;

	public FollowAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.TRY_TO_SCORE, false);
		// addPrecondition(GoapGlossary.BEHIND_BALL_LINE, true);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, true);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, true);
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

		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			follow = true;
			if (player.getMemory().getOppGoal() == null || player.getMemory().getBall() == null) {
				player.getAction().turn(30.0);
				return true;
			} else {
				player.getAction()
						.goToSidePoint(new Position(player.getMemory().getBallPos(player.getMemory().getBall()).x + 10,
								player.getMemory().getHome().y));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
