package robocup.ai;

import robocup.player.DefensivePlayer;

/**@class Brain
 * The brain serves as a place to store the Player modes, marked players for
 * various functions, and a set of strategies for player actions.
 */
public class DefensiveAI extends AbstractAI {
	
	public DefensiveAI(DefensivePlayer defensivePlayer) {
		super(defensivePlayer);
		start();
	}

	/**
	 * The FullBackBrain thread run method. It instructs the FullBack in soccer behaviors
	 * 
	 * @post FullBack will act accordingly during match. 
	 */
	@Override
	public void run() {
		while (true) {						
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(getPlayer().getMemory().timeCheck(getPlayer().getTime())) {
				getPlayer().setTime(getPlayer().getMemTime());
				try {
					getPlayer().runDefense();
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}		
		}
	}
}