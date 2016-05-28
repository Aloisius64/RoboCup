package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robocup.goap.GoapAction;

/** @file FullBack.java
 * Class file for FullBack class
 * @author Joel Tanzi
 * @date 5 November 2011
 * @version 1.0 
 */

/** @class FullBack
 * The FullBack class inherits from the Player class.  The FullBack is a specialized
 * type of Player that focuses on defensive behaviors such as interfering with opponent scoring.
 */
public class DefensivePlayer extends AbstractPlayer{

	public DefensivePlayer() {
		super();
		//this.setAi(new DefensiveAI(this));
	}

	public DefensivePlayer(String team) {
		super(team);
		//this.setAi(new DefensiveAI(this));
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
