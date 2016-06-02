package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapGlossary;
import robocup.player.actions.CatchBallGoalieAction;
import robocup.player.actions.FollowBallGoalieAction;
import robocup.player.actions.IdleAction;
import robocup.player.actions.PassBallGoalieAction;
import robocup.player.actions.ShootBallGoalieAction;

public class GoalierPlayer extends AbstractPlayer {

	public GoalierPlayer(){
		super();
	}	
	
	public GoalierPlayer(String team){
		super(team);
	}
	
	/********************************************/
	/* 			IGoap implementations 			*/
	/********************************************/
	
	@Override
	public void planFailed(HashMap<String, Boolean> failedGoal) {
	
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
	
	}

	@Override
	public void actionsFinished() {
	
	}

	@Override
	public void planAborted(GoapAction aborter) {
	
	}
	
	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldState = new HashMap<>();

		// Set worldState from player memory
		worldState.put(GoapGlossary.KEEP_AREA_SAFE, true);
		
		return worldState;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();

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
