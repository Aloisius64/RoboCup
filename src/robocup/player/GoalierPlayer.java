package robocup.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
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
		System.err.println("Player "+getMemory().getuNum()+" - Plan failed");
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
		System.err.println("Player "+getMemory().getuNum()+" - Plan found "+GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		//		System.err.println("Player "+getMemory().getuNum()+" - Actions finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		//		System.out.println("Player "+getMemory().getuNum()+" - Plan aborted");
	}
	
	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldState = new HashMap<>();

		// Set worldState from player memory
		worldState.put(GoapGlossary.KEEP_AREA_SAFE, !getAction().isBallInOurField().booleanValue());
		worldState.put(GoapGlossary.BALL_CATCHED, false);
		worldState.put(GoapGlossary.BALL_NEAR, getAction().isBallInRangeOf(25));
		worldState.put(GoapGlossary.BALL_NEAR_TEAMMATE_ATTACKER, getAction().isBallNearTeammateAttacker());
		worldState.put(GoapGlossary.BALL_CATCHABLE, getAction().isBallInRangeOf(1.0));
		
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
