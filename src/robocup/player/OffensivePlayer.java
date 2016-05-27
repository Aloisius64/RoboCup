package robocup.player;

import robocup.ai.OffensiveAI;

/** @class Forward (currently unused)
* The Forward class inherits from the Player class.  The Forward is a specialized
* type of Player that focuses on offensive behaviors such as scoring and ball interception.
*/
public class OffensivePlayer extends AbstractPlayer{
	
	public OffensivePlayer() {
		super();
		this.setAi(new OffensiveAI(this));
	}

	public OffensivePlayer(String team) {
		super(team);
		this.setAi(new OffensiveAI(this));
	}
	
}
