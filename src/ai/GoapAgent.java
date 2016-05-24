package ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import team.Player;


public class GoapAgent {

	private FSM stateMachine;
	private FSMState idleState; // finds something to do
	private FSMState performActionState; // performs an action
	private HashSet<GoapAction> availableActions;
	private Queue<GoapAction> currentActions;
	private IGoap dataProvider; // this is the implementing class that provides our world data and listens to feedback on planning
	private GoapPlanner planner;
	
	private Player player;

	public GoapAgent(Player player){
		stateMachine = new FSM();
		availableActions = new HashSet<GoapAction>();
		currentActions = new LinkedList<>(); // ArrayList
		planner = new GoapPlanner();
		findDataProvider();
		createIdleState();
		//createMoveToState();
		createPerformActionState();
		stateMachine.pushState(idleState);
		
		this.player = player;
		
		loadActions();
	}

	void update() {
		stateMachine.update(this.player);
	}

	public void addAction(GoapAction goapAction) {
		availableActions.add(goapAction);
	}

	public GoapAction getAction(String actionName) {
		for (GoapAction goapAction : availableActions) {
			if (goapAction.getClass().getName().equals(actionName))
				return goapAction;
		}
		return null;
	}

	public void removeAction(GoapAction goapAction){
		availableActions.remove(goapAction);
	}

	private boolean hasActionPlan() {
		return currentActions.size() > 0;
	}

	private void createIdleState() {

		idleState = new FSMState() {			
			@Override
			public void update(FSM fsm, Object object) {

				// GOAP planning
				System.out.println("Idle State");

				// get the world state and the goal we want to plan for
				HashMap<String, Object> worldState = dataProvider.getWorldState();
				HashMap<String, Object> goal = dataProvider.createGoalState();

				// Plan
				Queue<GoapAction> plan = planner.plan(object, availableActions, worldState, goal);
				if (plan != null) {
					// we have a plan, hooray!
					currentActions = plan;
					dataProvider.planFound(goal, plan);

					fsm.popState(); // move to PerformAction state
					fsm.pushState(performActionState);

				} else {
					// ugh, we couldn't get a plan
					System.out.println("Failed Plan: "+prettyPrint(goal));
					dataProvider.planFailed(goal);
					fsm.popState(); // move back to IdleAction state
					fsm.pushState(idleState);
				}

			}
		};    	

	}

	private void createPerformActionState() {
		performActionState = new FSMState() {
			
			@Override
			public void update(FSM fsm, Object object) {
				// perform the action
				System.out.println("Perform action state");
	
				if (!hasActionPlan()) {
					// no actions to perform
					System.out.println("Done actions");
					fsm.popState();
					fsm.pushState(idleState);
					dataProvider.actionsFinished();
					return;
				}
	
				GoapAction action = currentActions.peek();
				if (action.isDone()) {
					// the action is done. Remove it so we can perform the next one
					currentActions.poll();
				}
	
				if (hasActionPlan()) {
					// perform the next action
					action = currentActions.peek();
	
					boolean success = action.perform(player);
					if (!success) {
						// action failed, we need to plan again
						fsm.popState();
						fsm.pushState(idleState);
						dataProvider.planAborted(action);
					}
	
				} else {
					// no actions left, move to Plan state
					fsm.popState();
					fsm.pushState(idleState);
					dataProvider.actionsFinished();
				}
				
			}
		};
		
	}

	private void findDataProvider() {
		dataProvider = (IGoap) player;
	}

	private void loadActions() {		
		for (GoapAction action : player.getActions()) {
			availableActions.add(action);
		}
		//System.out.println("Found actions: " + prettyPrint(player.getActions()));		
	}

	public static String prettyPrint(HashMap<String, Object> state) {
		String s = "";

		for (String key : state.keySet()) {
			s+= key+":"+state.get(key).toString();
			s+=", ";
		}

		return s;
	}

	public static String prettyPrint(Queue<GoapAction> actions) {
		String s = "";
		for (GoapAction action : actions) {
			s += action.getClass().getName();
			s += "-> ";
		}
		s += "GOAL";
		return s;
	}

	public static String prettyPrint(List<GoapAction> actions) {
		String s = "";
		for (GoapAction action : actions) {
			s += action.getClass().getName();
			s += ", ";
		}
		return s;
	}

	public static String prettyPrint(GoapAction action) {
		String s = "" + action.getClass().getName();
		return s;
	}
}
