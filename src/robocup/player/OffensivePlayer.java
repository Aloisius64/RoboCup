package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robocup.goap.GoapAction;

/** @class Forward (currently unused)
 * The Forward class inherits from the Player class.  The Forward is a specialized
 * type of Player that focuses on offensive behaviors such as scoring and ball interception.
 */
public class OffensivePlayer extends AbstractPlayer{

	public OffensivePlayer() {
		super();
		//this.setAi(new OffensiveAI(this));
	}

	public OffensivePlayer(String team) {
		super(team);
		//this.setAi(new OffensiveAI(this));
	}

	/********************************************/
	/* 			IGoap implementations 			*/
	/********************************************/

	@Override
	public HashMap<String, Object> getWorldState() {
		HashMap<String, Object> worldState = new HashMap<>();
		
		return worldState;
	}

	@Override
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<>();
		
		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();
		
		return actions;
	}

}
