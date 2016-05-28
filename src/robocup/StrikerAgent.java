package robocup;

import robocup.player.AbstractPlayer;
import robocup.player.OffensivePlayer;

public class StrikerAgent {

	public static void main(String[] args) throws Exception {
		AbstractPlayer p = new OffensivePlayer();

		p.initPlayer(-5, -10);

		while (true) {
			p.receiveInput();

			if (p.getMemory().timeCheck(p.getTime())) {
				p.setTime(p.getMemory().getObjMemory().getTime());
				p.getAction().findBall();
			}
		}
	}
}
