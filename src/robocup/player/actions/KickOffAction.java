package robocup.player.actions;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationManager;
import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;

public class KickOffAction extends GoapAction {

	private boolean isKicked = false;

	public KickOffAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, true);
		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
		addEffect(GoapGlossary.BALL_NEAR_TEAMMATE, true);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
		addEffect(GoapGlossary.KICK_OFF, false);
		addEffect(GoapGlossary.PLAY_ON, true);

	}

	@Override
	public void reset() {
		isKicked = false;

	}

	@Override
	public boolean isDone() {

		return isKicked;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;

		try {
			if (player.getAction().isPlayMode("play_on")) {
				isKicked = true;
				return true;
			}
			if (player.getAction().isBallNearTeammate()) {
				isKicked = true;
				return true;
			}
			if (player.getMemory().getBall() == null) {
				player.getAction().turn(30);
			} else if (player.getMemory().getBall().getDistance() > 0.7) {
				player.getAction().dash(100, player.getMemory().getBall().getDirection());
			} else {
				player.getAction().kick(50, 180);
				isKicked = true;
				Thread.sleep(100);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
