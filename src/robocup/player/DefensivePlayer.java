package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.actions.GoToMoreFreePlaceAction;
import robocup.player.actions.IdleAction;
import robocup.player.actions.MarkPlayerAction;
import robocup.player.actions.PassBallDefensorAction;
import robocup.player.actions.ShootBallDefensorAction;
import robocup.player.actions.StoleBallDefensorAction;

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
	}

	public DefensivePlayer(String team) {
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
		actions.add(new ShootBallDefensorAction());
		actions.add(new PassBallDefensorAction());
		actions.add(new StoleBallDefensorAction());
		actions.add(new GoToMoreFreePlaceAction());
		actions.add(new MarkPlayerAction());
		
		return actions;
	}

}
