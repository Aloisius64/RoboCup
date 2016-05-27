package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robocup.goap.GoapAction;

/** @class Goalie
 * The Goalie class inherits from the Player class.  The Goalie is a specialized
 * type of Player that may catch the ball under certain conditions and defends the goal
 * from the opposing team. 
 */
public class GoalierPlayer extends AbstractPlayer {

	public GoalierPlayer() {
		super();
		//this.setAi(new GoalieAI(this));
	}

	public GoalierPlayer(String team){
		super(team);
		//this.setAi(new GoalieAI(this));
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
