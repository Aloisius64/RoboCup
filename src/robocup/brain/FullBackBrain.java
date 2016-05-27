package robocup.brain;

import java.net.UnknownHostException;

import robocup.memory.Mode;
import robocup.player.DefensivePlayer;

/**@class Brain
 * The brain serves as a place to store the Player modes, marked players for
 * various functions, and a set of strategies for player actions.
 */
public class FullBackBrain extends Brain {
	
	public FullBackBrain() {
		super();
	}
	
	public FullBackBrain(DefensivePlayer defensivePlayer) {
		super(defensivePlayer);
		start();
	}

	public FullBackBrain(Mode currentMode) {
		super();
		this.setCurrentMode(currentMode);
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
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(getPlayer().getMemory().timeCheck(getPlayer().getTime())) {
				getPlayer().setTime(getPlayer().getMemTime());

				try {
					getPlayer().runDefense();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}		
		}
	}
}