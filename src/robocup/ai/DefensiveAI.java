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

}