package robocup.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.github.robocup_atan.atan.model.ActionsPlayer;
import com.github.robocup_atan.atan.model.ControllerPlayer;
import com.github.robocup_atan.atan.model.enums.Errors;
import com.github.robocup_atan.atan.model.enums.Flag;
import com.github.robocup_atan.atan.model.enums.Line;
import com.github.robocup_atan.atan.model.enums.Ok;
import com.github.robocup_atan.atan.model.enums.PlayMode;
import com.github.robocup_atan.atan.model.enums.RefereeMessage;
import com.github.robocup_atan.atan.model.enums.ServerParams;
import com.github.robocup_atan.atan.model.enums.ViewAngle;
import com.github.robocup_atan.atan.model.enums.ViewQuality;
import com.github.robocup_atan.atan.model.enums.Warning;

import robocup.ai.GoapAction;
import robocup.ai.GoapAgent;
import robocup.ai.IGoap;
import robocup.ai.KnowledgeBase;
import robocup.formation.FormationManager;
import robocup.sensors.Ball;
import robocup.sensors.SeenPlayer;
import robocup.sensors.SenseBody;

public abstract class AbstractPlayer implements ControllerPlayer, IGoap {

	protected HashMap<ServerParams, Object> serverInfo;
	protected ActionsPlayer player;
	protected KnowledgeBase knowledgeBase;
	protected GoapAgent agent;
	private String formationName = null;	// Team formation name

	public AbstractPlayer() {
		knowledgeBase = new KnowledgeBase();
		agent = new GoapAgent(this);
		formationName = "";
	}
	
	@Override
	public ActionsPlayer getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(ActionsPlayer actionsPlayer) {
		this.player = actionsPlayer;
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public void setType(String newType) {

	}

	@Override
	public void infoCPTOther(int unum) {

	}

	@Override
	public void infoCPTOwn(int unum, int type) {

	}

	@Override
	public void infoHearError(Errors arg0) {

	}

	@Override
	public void infoHearOk(Ok arg0) {

	}

	@Override
	public void infoHearPlayMode(PlayMode playMode) {
		this.knowledgeBase.setPlayMode(playMode);
		
		switch (playMode) {
		case BEFORE_KICK_OFF:
		case GOAL_L:
		case GOAL_R:
			FormationManager.getFormation(formationName).movePlayerToItsPosition(player);
			break;
		case CORNER_KICK_OTHER:
		case CORNER_KICK_OWN:
		case CORNER_KICK_L:
		case CORNER_KICK_R:
		case FREE_KICK_FAULT_OTHER:
		case FREE_KICK_FAULT_OWN:
		case FREE_KICK_FAULT_L:
		case FREE_KICK_FAULT_R:
		case FREE_KICK_OTHER:
		case FREE_KICK_OWN:
		case FREE_KICK_L:
		case FREE_KICK_R:
		case GOAL_KICK_OTHER:
		case GOAL_KICK_OWN:
		case GOAL_KICK_L:
		case GOAL_KICK_R:
		case GOAL_OTHER:
		case GOAL_OWN:
		case KICK_IN_OTHER:
		case KICK_IN_OWN:
		case KICK_IN_L:
		case KICK_IN_R:
		case KICK_OFF_OTHER:
		case KICK_OFF_OWN:
		case KICK_OFF_L:
		case KICK_OFF_R:
		case TIME_OVER:
		default:
			break;
		}
	}

	@Override
	public void infoHearPlayer(int step, double direction, String message) {
		System.out.println(player.getNumber() + " sente " + message);
	}

	@Override
	public void infoHearReferee(RefereeMessage refereeMessage) {

	}

	@Override
	public void infoHearWarning(Warning warning) {

	}

	@Override
	public void infoPlayerParam(double allowMultDefaultType, double dashPowerRateDeltaMax, double dashPowerRateDeltaMin,
			double effortMaxDeltaFactor, double effortMinDeltaFactor, double extraStaminaDeltaMax,
			double extraStaminaDeltaMin, double inertiaMomentDeltaFactor, double kickRandDeltaFactor,
			double kickableMarginDeltaMax, double kickableMarginDeltaMin, double newDashPowerRateDeltaMax,
			double newDashPowerRateDeltaMin, double newStaminaIncMaxDeltaFactor, double playerDecayDeltaMax,
			double playerDecayDeltaMin, double playerTypes, double ptMax, double randomSeed,
			double staminaIncMaxDeltaFactor, double subsMax) {

	}

	@Override
	public void infoPlayerType(int id, double playerSpeedMax, double staminaIncMax, double playerDecay,
			double inertiaMoment, double dashPowerRate, double playerSize, double kickableMargin, double kickRand,
			double extraStamina, double effortMax, double effortMin) {

	}

	@Override
	public void infoSeeBall(double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
		this.knowledgeBase.setBall(
				new Ball(distance, direction, distChange, dirChange, bodyFacingDirection, headFacingDirection));
	}

	@Override
	public void infoSeeFlagCenter(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
		// SeenFlag seenFlag = new SeenFlag(flag, distance, direction,
		// distChange, dirChange, bodyFacingDirection, headFacingDirection);
		// knowledgeBase.addFlag(seenFlag);
	}

	@Override
	public void infoSeeFlagCornerOther(Flag flag, double distance, double direction, double distChange,
			double dirChange, double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagCornerOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagGoalOther(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagGoalOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagLeft(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagOther(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {

	}

	@Override
	public void infoSeeFlagOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSeeFlagPenaltyOther(Flag flag, double distance, double direction, double distChange,
			double dirChange, double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSeeFlagPenaltyOwn(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSeeFlagRight(Flag flag, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSeeLine(Line line, double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSeePlayerOther(int number, boolean goalie, double distance, double direction, double distChange,
			double dirChange, double bodyFacingDirection, double headFacingDirection) {
		knowledgeBase.addOtherPlayer(new SeenPlayer(goalie, distance, direction, distChange, dirChange,
				bodyFacingDirection, headFacingDirection));
	}

	@Override
	public void infoSeePlayerOwn(int number, boolean goalie, double distance, double direction, double distChange,
			double dirChange, double bodyFacingDirection, double headFacingDirection) {
	}

	@Override
	public void infoSenseBody(int step, ViewQuality viewQuality, ViewAngle viewAngle, double stamina, double unknown,
			double effort, double speedAmount, double speedDirection, double headAngle, int kickCount, int dashCount,
			int turnCount, int sayCount, int turnNeckCount, int catchCount, int moveCount, int changeViewCount) {
		this.knowledgeBase.setSenseBody(new SenseBody(viewQuality, viewAngle, stamina, unknown, effort, speedAmount,
				speedDirection, headAngle, kickCount, dashCount, turnCount, sayCount, turnNeckCount, catchCount,
				moveCount, changeViewCount));
	}

	@Override
	public void infoServerParam(HashMap<ServerParams, Object> info) {
		this.serverInfo = info;
	}

	@Override
	public void preInfo() {
		knowledgeBase.clean();
	}

	/********************************************/
	/* IGoap implementations */
	/********************************************/

	@Override
	public HashMap<String, Object> getWorldState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Object> createGoalState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void planFailed(HashMap<String, Object> failedGoal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void planFound(HashMap<String, Object> goal, Queue<GoapAction> actions) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionsFinished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void planAborted(GoapAction aborter) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<GoapAction> getActions() {
		return new LinkedList<>();
	}
	
	public String getFormationName() {
		return formationName;
	}

	public void setFormationName(String formation) {
		this.formationName = formation;
	}
	
}
