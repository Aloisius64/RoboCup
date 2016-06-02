package test.goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
import robocup.goap.GoapGlossary;
import robocup.goap.GoapPlanner;
import robocup.goap.IGoap;

public class GoapAgentPlayer implements IGoap {
	
	private GoapAgent agent;
	
	public GoapAgentPlayer() {
		agent = new GoapAgent(this);
	}	
	
	public void update(){
		agent.update();
	}

	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldData = new HashMap<>();
				
		worldData.put(GoapGlossary.P_A_1, false);
		worldData.put(GoapGlossary.P_A, true);
		worldData.put(GoapGlossary.P_B, false);
		//worldData.put(GoapGlossary.P_B, false);
//		worldData.put(GoapGlossary.GOAL, false);
		
//		GoapPlanner.printMap(worldData);
		
		return worldData;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();
		
		goal.put(GoapGlossary.GOAL, true);
		
		return goal;
	}

	@Override
	public void planFailed(HashMap<String, Boolean> failedGoal) {
		System.out.println("Plan failed");
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
		System.out.println("Plan found "+GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		System.out.println("Actions finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		System.out.println("Plan aborted");
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();
		
		actions.add(new ActionA());
		actions.add(new ActionB());
		actions.add(new ActionC());
		
		return actions;
	}

}
