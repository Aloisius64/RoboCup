package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;

/*
*/
public class ComeBackHomeAction extends GoapAction {

	private boolean playerToHomePosition = false;

	public ComeBackHomeAction() {
		super(5.0f);
		addPrecondition(GoapGlossary.REPOSITIONED, true);
		addEffect(GoapGlossary.REPOSITIONED, true);
	}

	@Override
	public void reset() {
		playerToHomePosition = false;
	}

	@Override
	public boolean isDone() {
		return playerToHomePosition;
	}

	@Override
	public boolean checkProceduralPrecondition(Object agent) {
		return true;
	}

	@Override
	public boolean perform(Object agent) {
		AbstractPlayer player = ((AbstractPlayer) agent);
		if (!player.getAction().isPlayMode("goal_"))
			return false;
		System.out.println(player.getMemory().getuNum() + "  Sto andando a casa");
		try {
			// player.getAction().move(player.getHome().x, player.getHome().y);
			if (!player.getAction().isHome()) {
				player.getAction().goToPoint(player.getMemory().getHome());
				return true;
			}
			playerToHomePosition = true;
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
