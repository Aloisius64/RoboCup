package robocup.ai;

import robocup.memory.Mode;
import robocup.player.OffensivePlayer;

/**@class Brain
 * The brain serves as a place to store the Player modes, marked players for
 * various functions, and a set of strategies for player actions.
 */
public class OffensiveAI extends AbstractAI {
		
	public OffensiveAI(OffensivePlayer offensivePlayer) {
		super(offensivePlayer);
		start();
	}

	public OffensiveAI(Mode currentMode) {
		super();
		this.setCurrentMode(currentMode);
	}

}
