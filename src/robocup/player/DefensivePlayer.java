package robocup.player;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
import robocup.goap.GoapGlossary;
import robocup.player.actions.IdleAction;
import robocup.player.actions.PassBallDefensorAction;
import robocup.player.actions.ShootBallDefensorAction;
import robocup.player.actions.StoleBallDefensorAction;

public class DefensivePlayer extends AbstractPlayer {

	public DefensivePlayer() {
		super("Team");
	}

	public DefensivePlayer(String team) {
		super(team);
	}

	/********************************************/
	/* IGoap implementations */
	/********************************************/

	@Override
	public void planFailed(HashMap<String, Boolean> failedGoal) {
		System.err.println("Player " + getMemory().getuNum() + " " + getMemory().getSide() + " - Plan failed");
		// GoapPlanner.printMap(getWorldState());
	}

	@Override
	public void planFound(HashMap<String, Boolean> goal, Queue<GoapAction> actions) {
		System.err.println("Player " + getMemory().getuNum() + " - Plan found" + GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		// System.err.println("Player "+getMemory().getuNum()+" - Actions
		// finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		System.out.println("Player " + getMemory().getuNum() + " - Plan aborted");
	}

	@Override
	public HashMap<String, Boolean> getWorldState() {
		HashMap<String, Boolean> worldState = new HashMap<>();

		// Set worldState from player memory
		// worldState.put(GoapGlossary.KICK_OFF,
		// getAction().isPlayMode("kick_off_l"));
		worldState.put(GoapGlossary.PLAY_ON, getAction().isPlayMode("play_on"));
		worldState.put(GoapGlossary.KEEP_AREA_SAFE, !getAction().isBallInOurField().booleanValue());
		worldState.put(GoapGlossary.BALL_CATCHED, false);
		worldState.put(GoapGlossary.BALL_SEEN, getAction().isBallVisible());
		worldState.put(GoapGlossary.BALL_NEAR, getAction().isBallInRangeOf(15));
		worldState.put(GoapGlossary.BALL_NEAR_DEFENDER, getAction().isBallNearDefender());
		worldState.put(GoapGlossary.BALL_IN_DEFENSIVE_AREA, getAction().isBallInDefensiveArea());
		worldState.put(GoapGlossary.GOAL_SCORED, getAction().isPlayMode("goal_"));
		// worldState.put(GoapGlossary.TRY_TO_SCORE, true);
		// GoapPlanner.printMap(worldState);
		return worldState;
	}

	@Override
	public HashMap<String, Boolean> createGoalState() {
		HashMap<String, Boolean> goal = new HashMap<>();

		goal.put(GoapGlossary.KEEP_AREA_SAFE, true);

		// Il goal dipende dal tipo di play mode
		// cambiarlo in base alla modalit� di gioco

		return goal;
	}

	@Override
	public List<GoapAction> getActions() {
		List<GoapAction> actions = new ArrayList<>();

		actions.add(new IdleAction());
		actions.add(new ShootBallDefensorAction());
		actions.add(new PassBallDefensorAction());
		actions.add(new StoleBallDefensorAction());
		// actions.add(new ComeBackHomeAction());
		// actions.add(new GoToMoreFreePlaceAction());
		// actions.add(new MarkPlayerAction());

		return actions;
	}

	@Override
	public int evaluate() {
		// TODO Auto-generated method stub
		this.setCurrentEvaluation(0);
		return 0;
	}

	@Override
	public void broadcast(int evaluation) throws UnknownHostException, InterruptedException {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ITALY);
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("##.#", otherSymbols);
		String messagePositionX = getMemory().getuNum() + "X" + format.format(getPosition().x);
		String messagePositionY = getMemory().getuNum() + "Y" + format.format(getPosition().y);
		String messageEvaluation = getMemory().getuNum() + "D" + evaluation;
		String messageDistanceBall = Integer.toString(getMemory().getuNum());
		if (getMemory().getBall() != null)
			messageDistanceBall = messageDistanceBall.concat("B" + getMemory().getBall().getDistance());
		else
			messageDistanceBall = messageDistanceBall.concat("B150");

		// TODO
		messagePositionX = messagePositionX.replace('.', 'p');
		messagePositionX = messagePositionX.replace('-', 'm');

		messagePositionY = messagePositionY.replace('.', 'p');
		messagePositionY = messagePositionY.replace('-', 'm');

		messageDistanceBall = messageDistanceBall.replace('.', 'p');
		getAction().say(messagePositionY);
		getAction().say(messagePositionX);
		getAction().say(messageDistanceBall);
		getAction().say(messageEvaluation);

	}

}
