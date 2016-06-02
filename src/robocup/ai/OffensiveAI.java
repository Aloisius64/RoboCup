package robocup.ai;

import robocup.player.OffensivePlayer;

/**
 * @class Brain The brain serves as a place to store the Player modes, marked
 *        players for various functions, and a set of strategies for player
 *        actions.
 */
public class OffensiveAI extends AbstractAI {

	public OffensiveAI(OffensivePlayer offensivePlayer) {
		super(offensivePlayer);

	}

	@Override
	public void run() {

		while (true) {
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("play a turn" + getPlayer().getMemTime());
			if (getPlayer().getMemory().timeCheck(getPlayer().getTime())) {
				getPlayer().setTime(getPlayer().getMemory().getObjMemory().getTime());
				try {
					if (getPlayer().getMemory().getPlayMode().compareTo("play_on") == 0) {
						getPlayer().getAction().findBall();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
