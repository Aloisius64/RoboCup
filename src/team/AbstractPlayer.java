package team;

import team.playerSeenData.Ball;
import team.playerSeenData.SeenFlag;
import java.util.HashMap;

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
import team.formation.DefaultFormation;
import team.formation.Formation;
import team.playerSeenData.SeenLine;
import team.playerSeenData.SeenPlayer;

public abstract class AbstractPlayer implements ControllerPlayer {

	protected HashMap<ServerParams, Object> serverInfo;

	protected ActionsPlayer player;
	protected Formation teamFormation;
	protected KnowledgeBase knowledgeBase;

	public AbstractPlayer() {
		teamFormation = new DefaultFormation();
		knowledgeBase = new KnowledgeBase();
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
		if (playMode == PlayMode.BEFORE_KICK_OFF || playMode == PlayMode.GOAL_L || playMode == PlayMode.GOAL_R) {
			teamFormation.movePlayerToPosition(player);
		}
	}

	@Override
	public void infoHearPlayer(int step, double direction, String message) {
		// System.out.println(player.getNumber() + " sente " + message);
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

}
