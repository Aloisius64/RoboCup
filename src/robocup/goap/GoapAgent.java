package robocup.goap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import robocup.player.AbstractPlayer;

public class GoapAgent {

	private FSM stateMachine;
	private FSMState idleState; // finds something to do
	private FSMState performActionState; // performs an action
	private HashSet<GoapAction> availableActions;
	private Queue<GoapAction> currentActions;
	private IGoap dataProvider; // this is the implementing class that provides
								// our world data and listens to feedback on
								// planning
	private GoapPlanner planner;
	private Object targetObject;

	public GoapAgent(Object targetObject) {
		stateMachine = new FSM();
		availableActions = new HashSet<GoapAction>();
		currentActions = new LinkedList<>(); // ArrayList
		planner = new GoapPlanner();
		dataProvider = (IGoap) targetObject;
		this.targetObject = targetObject;
		loadActions();
		createIdleState();
		createPerformActionState();
		stateMachine.pushState(idleState);
	}

	public void update() {
		stateMachine.update(targetObject);
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

	public void removeAction(GoapAction goapAction) {
		availableActions.remove(goapAction);
	}

	private boolean hasActionPlan() {
		return currentActions.size() > 0;
	}

	private void createIdleState() {

		idleState = new FSMState() {
			@Override
			public void update(FSM fsm, Object object) {

				// get the world state and the goal we want to plan for
				HashMap<String, Boolean> worldState = dataProvider.getWorldState();
				HashMap<String, Boolean> goal = dataProvider.createGoalState();

				// Plan
				Queue<GoapAction> plan = planner.plan(targetObject, availableActions, worldState, goal);
				if (plan != null) {
					// we have a plan, hooray!
					currentActions = plan;
					dataProvider.planFound(goal, plan);

					fsm.popState(); // move to PerformAction state
					fsm.pushState(performActionState);

				} else {
					// ugh, we couldn't get a plan
					// System.out.println("Failed Plan: "+prettyPrint(goal));
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

				if (!hasActionPlan()) {
					// no actions to perform
					// System.out.println("Done actions");
					fsm.popState();
					fsm.pushState(idleState);
					dataProvider.actionsFinished();
					return;
				}

				GoapAction action = currentActions.peek();
				if (action.isDone()) {
					// the action is done. Remove it so we can perform the next
					// one
					currentActions.poll();
				}

				if (hasActionPlan()) {
					// perform the next action
					action = currentActions.peek();
					AbstractPlayer player = (AbstractPlayer) targetObject;
//					System.out.println(player.getMemory().getuNum() + "  " + player.getMemory().getSide() + " "
//							+ action.getClass().getSimpleName());
					boolean success = action.perform(targetObject);

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

	private void loadActions() {
		for (GoapAction action : dataProvider.getActions()) {
			availableActions.add(action);
		}

		if (dataProvider.getActions().size() > 0)
			System.out.println("Found actions: " + prettyPrint(dataProvider.getActions()));
	}

	public static String prettyPrint(HashMap<String, ? extends Object> state) {
		String s = "";

		for (String key : state.keySet()) {
			s += key + ":" + state.get(key).toString();
			s += ", ";
		}

		return s;
	}

	public static String prettyPrint(Queue<GoapAction> actions) {
		String s = "";
		for (GoapAction action : actions) {
			s += action.getClass().getSimpleName();
			s += "-> ";
		}
		s += "GOAL";
		return s;
	}

	public static String prettyPrint(List<GoapAction> actions) {
		String s = "";
		for (GoapAction action : actions) {
			s += action.getClass().getSimpleName();
			s += ", ";
		}
		return s;
	}

	public static String prettyPrint(GoapAction action) {
		String s = "" + action.getClass().getSimpleName();
		return s;
	}
}
