package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;

/*
IDLE **********************************************
PRECONDITIONS:
	KEEP_AREA_SAFE (TRUE)

EFFECTS:
	KEEP_AREA_SAFE (TRUE)

THINGS TO CHECK:

PERFORMING:
	Il giocatore ritorna alla sua posizione
	di default 
 */
public class IdleAction extends GoapAction {

	private boolean playerToHomePosition = false;

	public IdleAction() {
		super(10.0f);
		// addPrecondition(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.TRY_TO_SCORE, true);
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

		// System.out.println("Performing "+getClass().getSimpleName());

		try {

			if (!player.getAction().isHome()) {
				player.getAction().goToPoint(player.getMemory().getHome());
				return true;
			}
			// System.out.println(player.getMemory().getuNum() + " " +
			// player.getTime());
			playerToHomePosition = true;
			if (player.getMemory().getBall() == null)
				player.getRoboClient().turn(30);
			else
				player.getAction().turn(player.getMemory().getBall().getDirection());
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
