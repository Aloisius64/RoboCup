package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.actions.CatchBallGoalieAction;
import robocup.player.actions.FollowBallGoalieAction;
import robocup.player.actions.IdleAction;
import robocup.player.actions.PassBallGoalieAction;
import robocup.player.actions.ShootBallGoalieAction;

/** @class Goalie
 * The Goalie class inherits from the Player class.  The Goalie is a specialized
 * type of Player that may catch the ball under certain conditions and defends the goal
 * from the opposing team. 
 */
public class GoalierPlayer extends AbstractPlayer {

	public GoalierPlayer(String team){
		super(team);
	}
	
	/********************************************/
	/* 			IGoap implementations 			*/
	/********************************************/
	
	@Override
	public HashMap<String, Object> getWorldState() {
		HashMap<String, Object> worldState = new HashMap<>();

		// Set worldState from player memory
		
		return worldState;
	}

	@Override
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<>();

		goal.put(GoapGlossary.KEEP_AREA_SAFE, true);
		
		//	Il goal dipende dal tipo di play mode
		//	cambiarlo in base alla modalità di gioco
		
		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		actions.add(new IdleAction());
		actions.add(new FollowBallGoalieAction());
		actions.add(new CatchBallGoalieAction());
		actions.add(new ShootBallGoalieAction());
		actions.add(new PassBallGoalieAction());
		
		return actions;
	}

}
