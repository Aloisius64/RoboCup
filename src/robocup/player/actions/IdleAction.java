package robocup.player.actions;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.AbstractPlayer;

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
		super(1.0f);
		addPrecondition(GoapGlossary.KEEP_AREA_SAFE, true);
		addEffect(GoapGlossary.KEEP_AREA_SAFE, true);
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

//		System.out.println("Performing "+getClass().getSimpleName());

		playerToHomePosition = true;

		try {
			if(!player.getAction().isHome()){
				player.getAction().goToPoint(player.getMemory().getHome());
			} 
			
			player.getRoboClient().turn(30);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
