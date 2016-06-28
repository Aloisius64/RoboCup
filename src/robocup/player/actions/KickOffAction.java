package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;
import robocup.utility.Position;

public class KickOffAction extends GoapAction {

	private boolean isKicked = false;

	public KickOffAction() {
		super(1.0f);
		addPrecondition(GoapGlossary.KICK_OFF, false);
		addEffect(GoapGlossary.KICK_OFF, true);

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
		AbstractPlayer player = (AbstractPlayer) agent;
		if(!player.getAction().isPlayMode("kick_off")){
			return false;
		}
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = (AbstractPlayer) agent;

		// System.out.println(player.getMemory().getuNum() + " " +
		// player.getMemory().getSide() + " " + "Performing "
		// + getClass().getSimpleName());

		try {

			if (player.getAction().isPlayMode("play_on")) {

				return false;
			}
			if (player.getMemory().getuNum() == 10) {
				if (player.getMemory().getBall() == null) {
					player.getAction().turn(30);
				} else if (player.getMemory().getBall().getDistance() > 0.7) {
					player.getAction().dash(100, player.getMemory().getBall().getDirection());
				} else {
					Position pos = new Position(-5, 10);
					player.getAction().kick(32, player.getMemory().getPolarFromFc(pos).t);
					isKicked = true;
					Thread.sleep(1000);
					return true;
				}
			} else {
				if (player.getMemory().getBall() == null) {
					player.getAction().turn(60);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
