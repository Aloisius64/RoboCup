package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

public class KickOffAction extends GoapAction {

	private boolean isKicked = false;

	public KickOffAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, true);
//		addPrecondition(GoapGlossary.BALL_NEAR_TEAMMATE, false);
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

//		System.out.println(player.getMemory().getuNum() + " " + player.getMemory().getSide() + " " + "Performing "
//				+ getClass().getSimpleName());
		try {

			if (player.getAction().isPlayMode("play_on")) {
				isKicked = true;
				return true;
			}
			if (player.getMemory().getuNum() == 10) {
				if (player.getMemory().getBall() == null) {
					player.getAction().turn(30);
				} else if (player.getMemory().getBall().getDistance() > 0.7) {
					player.getAction().dash(100, player.getMemory().getBall().getDirection());
				} else {
					Position pos = new Position(-5, 10);
					player.getAction().kick(50, player.getMemory().getPolarFromFc(pos).t);
					isKicked = true;
					Thread.sleep(100);
					return true;
				}
			} else {
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
