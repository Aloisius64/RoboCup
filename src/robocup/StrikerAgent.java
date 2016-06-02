package robocup;

import robocup.player.AbstractPlayer;
import robocup.player.OffensivePlayer;

public class StrikerAgent {

	public static void main(String[] args) throws Exception {

		AbstractPlayer p = new OffensivePlayer("Team");
		p.initPlayer(-5, -10);

		p.start();
	}
}
