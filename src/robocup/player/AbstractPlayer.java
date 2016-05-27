package robocup.player;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Queue;

import robocup.Action;
import robocup.ai.AbstractAI;
import robocup.goap.GoapAction;
import robocup.goap.GoapAgent;
import robocup.goap.IGoap;
import robocup.memory.Memory;
import robocup.network.RoboClient;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjInfo;
import robocup.objInfo.ObjPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Parser;
import robocup.utility.Polar;
import robocup.utility.Pos;

/** @class Player
 * The Player class defines all objects and methods used for the 
 * Player within the RoboCup match.  The Player establishes a connection
 * to the server, initializes itself on the team, and 
 * performs all actions related to a RoboCup soccer player such as
 * (but not limited to) kicking, dashing, dribbling, passing and scoring. 
 * The Player class has a Memory for storing the current RoboCup worldstate.
 * It reacts to stimuli based on strategies provided by the Brain (TBD). 
 */
public abstract class AbstractPlayer extends Thread implements IGoap {

	protected RoboClient roboClient;
	protected Memory memory;
	protected ObjInfo objInfo;
	protected Parser parser;
	protected Action action;
	protected int time;
	protected MathHelp mathHelp;
	protected boolean wait;
	protected String position;
	protected AbstractAI ai;
	protected GoapAgent agent;	
	protected boolean ballTurn = false;
	protected boolean ballCaught = false;

	public AbstractPlayer() {
		super();
		init();
	}

	public AbstractPlayer(String team){
		super();
		init();
		this.roboClient.setTeam(team);
	}

	private void init(){
		this.roboClient = new RoboClient();
		this.memory = new Memory();
		this.objInfo = new ObjInfo();
		this.parser = new Parser();
		this.action = new Action(memory, roboClient);
		this.time = 0;
		this.mathHelp = new MathHelp();
		this.wait = true;
		this.position = "left";
		this.agent = new GoapAgent(this);
	}

	public void initPlayer(double x, double y, String pos) throws SocketException, UnknownHostException {
		position = pos;
		roboClient.setDsock(new DatagramSocket());
		roboClient.init(getParser(), getMemory());
		getMemory().setHome(new Pos(x, y));

		try {
			move(x, y);
			Thread.sleep(100);
			if(getMemory().getSide().compareTo("r") == 0) {
				turn(180);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initPlayer(double x, double y) throws SocketException, UnknownHostException {
		roboClient.setDsock(new DatagramSocket());
		roboClient.init(getParser(), getMemory());
		getMemory().setHome(new Pos(x, y));

		try {
			move(x, y);
			Thread.sleep(100);
			if(getMemory().getSide().compareTo("r") == 0) {
				turn(180);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives worldstate data from the RoboCup server.
	 * @pre A RoboCup server is available.
	 * @post The current worldstate has been stored in the Memory.
	 */
	public void receiveInput() throws InterruptedException  {		
		parser.Parse(roboClient.receive(), memory);
	}

	/**
	 * Teleports the Player to the specified coordinates.
	 * @param x x-coordinate of the point to move the player to.
	 * @param y y-coordinate of the point to move the player to.
	 * @throws InterruptedException 
	 * @pre Playmode is before-kickoff, goal-scored, free-kick.
	 * @post The Player has been moved to the correct position.
	 */
	public void move(double x, double y) throws UnknownHostException, InterruptedException {
		roboClient.move(x, y);
	}

	/**
	 * Causes Player to kick the ball.
	 * @param dir The direction in which to kick the ball in the form of a decimal value. 
	 * representing the angle in degrees in relation go the player.
	 * @param power The power with which to kick the ball in the form of a decimal value.
	 * @throws InterruptedException 
	 * @pre Playmode is play_on, ball is in kickable range.
	 * @post The ball has been kicked in the specified direction and power.
	 */
	public void kick(double power, double dir) throws UnknownHostException, InterruptedException {
		roboClient.kick(power, dir);
	}

	/**
	 * Causes Player to dash.
	 * @param power The power with which to dash in the form of a decimal value.
	 * @throws Exception 
	 * @pre Play mode is play_on.
	 * @post The player has dashed at the given power.
	 */	
	public void dash(double power) throws Exception {
		roboClient.dash(power);
	}

	/**
	 * Causes Player to dash.
	 * @param power The power with which to dash in the form of a decimal value.
	 * @param direction: The direction to dash in.
	 * @throws Exception 
	 * @pre Play mode is play_on.
	 * @post The player has dashed at the given power.
	 */	
	public void dash(double power, double direction) throws Exception {
		roboClient.dash(power, direction);
	}

	/**
	 * Causes Player to turn according to a specified turn moment.
	 * @param moment The turn angle in degrees. 
	 * @throws InterruptedException 
	 * @pre Playmode is play_on, ball is in kickable range.
	 * @post The ball has been kicked in the specified direction and power.
	 */
	public void turn(double moment) throws UnknownHostException, InterruptedException {
		roboClient.turn(moment);
	}

	public void turn_neck(double moment) throws UnknownHostException, InterruptedException {
		roboClient.turn_neck(moment);
	}

	/**
	 * Instructs the player to prepare to receive a pass from another teammate.
	 * @param ball The ball in play.
	 * @param p The player to receive the ball from.
	 * @pre Playmode is play_on, ball is being passed to player.
	 * @post The player has possession of the ball.
	 */
	public void receivePass(ObjBall ball, ObjPlayer p) throws UnknownHostException, InterruptedException {
		//Turn toward direction ball is coming from
		turn(p.getDirection());
		Thread.sleep(100);

		//Halt ball to allow for dribbling
		if (ball.getDistance() < 0.5) {
			kick(5, 0);
		}
	}

	/**
	 * Causes Player to say the given message.  It has a limitation of 512 characters by default.
	 * @param message The string to be spoken by the player. 
	 * @throws InterruptedException 
	 * @pre None
	 * @post The player has spoken the message.
	 */	
	public void say(String message) throws UnknownHostException, InterruptedException {
		roboClient.say(message);
	}

	/**
	 * Marks opposing players for defense
	 */
	public void markOpponent(String team, String number) {
		//b.setMarked_team(team);
		//b.setMarked_unum(number);
	}

	/**
	 * Follows opposing players on defense
	 * (Currently unused)
	 * @throws Exception 
	 */
	//	public void runDefense() throws UnknownHostException, InterruptedException {
	//		ai.setDefensive();
	//
	//		while (closestOpponent() == null){
	//			turn(15);
	//			Thread.sleep(100);
	//		}
	//
	//		//System.out.println("Closest Opponent: " + closestOpponent().getTeam() + " " + closestOpponent().getuNum());
	//		action.gotoPoint(getMemory().getMathHelp().getNextPlayerPoint(closestOpponent()));
	//
	//		if (getMemory().isObjVisible("player")) {
	//			markOpponent(getMemory().getPlayer().getTeam(), Integer.toString(getMemory().getPlayer().getuNum()));
	//			//System.out.println("Marked Player " + ai.getMarked_team() + " " + ai.getMarked_unum());
	//		}
	//	}
	public void runDefense() throws Exception {
		if (!inFullBackZone()) {
			getAction().goHome();
		}

		getAction().FullBack_findBall();
	}

	public boolean inFullBackZone(){
		if (getMemory().getPosition().x < -10) {
			return true;
		} else {
			return false;
		}
	}	

	/**
	 * Returns the closest opponent to the player
	 * @pre Players are in sight of the goalie.
	 * @post The closest opponent to the player has been determined.
	 * @return ObjPlayer
	 * @throws InterruptedException 
	 * @throws UnknownHostException 
	 */
	public ObjPlayer closestOpponent() throws UnknownHostException, InterruptedException {
		ObjPlayer closestOpponent = new ObjPlayer();
		double distance = 0;

		//Loop through arraylist of ObjPlayers
		for (int i = 0; i < getMemory().getPlayers().size(); ++i) {

			if (getMemory().getPlayers().isEmpty()) {	//No other players in player's sight, so turn to another point to check again  
				turn(15);
			}

			if (!getMemory().getPlayers().isEmpty()) {  
				if (distance == 0 && getMemory().getPlayers().get(i).getTeam() != roboClient.getTeam()) {
					distance = getMemory().getPlayers().get(i).getDistance();
					closestOpponent = getMemory().getPlayers().get(i);
				}
				else {
					//Test if this player is closer than the previous one
					if (distance > getMemory().getPlayers().get(i).getDistance() && getMemory().getPlayers().get(i).getTeam() != roboClient.getTeam()) {
						distance = getMemory().getPlayers().get(i).getDistance();
						closestOpponent = getMemory().getPlayers().get(i);
					}
				}
			}
		}	

		return closestOpponent;
	}

	/**
	 * Returns the closest player to the FullBack on the same team.
	 * @post The closest player to the FullBack has been determined.
	 * @return ObjPlayer
	 * @throws InterruptedException 
	 * @throws UnknownHostException 
	 */
	public ObjPlayer closestPlayer() throws UnknownHostException, InterruptedException {
		ObjPlayer closestPlayer = new ObjPlayer();
		double distance = 0;

		//Loop through arraylist of ObjPlayers
		for (int i = 0; i < getMemory().getPlayers().size(); ++i) {
			if(getMemory().getPlayers().isEmpty()){
				turn(30);
			}

			if(!getMemory().getPlayers().isEmpty()){
				if (distance == 0 && getMemory().getPlayers().get(i).getTeam() == roboClient.getTeam()) {
					distance = getMemory().getPlayers().get(i).getDistance();
				}
				else {
					//Test if this player is closer than the previous one
					if (distance > getMemory().getPlayers().get(i).getDistance() && getMemory().getPlayers().get(i).getTeam() == roboClient.getTeam()) {
						distance = getMemory().getPlayers().get(i).getDistance();
						closestPlayer = getMemory().getPlayers().get(i);
					}
				}
			}
		}

		return closestPlayer;
	}

	/**
	 * Causes the Goalie to catch the ball.
	 * @pre Playmode is play-on, ball is within goalkeeper zone and in the catchable area.
	 * @post The Goalie has caught the ball.
	 */
	public void catchball(double d) throws UnknownHostException{
		roboClient.catchball(d);
		ballCaught = true;
	}

	/**
	 * Turns goalie toward the ball
	 * @throws Exception 
	 * @post The goalie will turn in the direction of the ball
	 */
	public void followBall() throws Exception {
		try {
			if(!getMemory().isObjVisible("ball")) {
				turn(45);
				return;
			}
			if(getMemory().isObjVisible("ball")) {
				ObjBall ball = getMemory().getBall();

				if((ball.getDirection() > 5.0) || (ball.getDirection() < -5.0)) {
					turn(ball.getDirection() * (1 + (5 * getMemory().getAmountOfSpeed())));
				}
				if(ballInGoalzone(ball)){
					defendGoal(ball);
				} else {
					positionGoalie(ball);
				}
			}
		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true or false depending on whether the ball is within the catchable range
	 * of the goalie.
	 * @pre The ball is visible to the goalie
	 * @post The ball is determined to catchable or not.
	 * @return boolean True if catchable, false if not.
	 */
	public boolean catchable() {
		boolean catchable = false;

		//Test for visibility
		if (getMemory().isObjVisible("ball") && ballInGoalzone(getMemory().getBall())) {
			//Test for moment range
			if (getMemory().getBall().getDirection()> -180 && getMemory().getBall().getDirection() < 180) {
				//Test for catchable distance
				if (getMemory().getBall().getDistance() < 2.0) {
					catchable = true;
				}
			}
		}		
		return catchable;
	}

	/**
	 * A method to determine whether the ball is in the penalty box
	 * 
	 * @param ball the ObjBall to follow
	 * @pre this must be called with an ObjBall
	 * @post true if ball is in penalty box, false if it's not
	 * @return boolean
	 */
	public boolean ballInGoalzone(ObjBall ball) {
		if(ball == null)
			return false;

		Pos ballPos = mathHelp.getPos(ball.getDistance(), getDirection() + ball.getDirection());
		ballPos = mathHelp.vAdd(getPosition(), ballPos);

		if(((ballPos.x <= -36) && (ballPos.x >= -52.5)) && ((-20.16 <= ballPos.y) && (ballPos.y <= 20.16)))
			return true;
		else
			return false;			
	}

	/**
	 * Causes the goalie to act to intercept the ball as it approaches the goal.
	 * @param ObjBall representing the ball in play.
	 * @throws Exception 
	 * @pre The ball has entered the goal zone.
	 * @post The ball has been caught by the goalie, or the goalie has missed the ball.
	 */
	public void defendGoal(ObjBall ball) throws Exception {
		Pos ridBallPoint = new Pos(0,0);

		//Move to catchable range of ball
		if (ball.getDistance() > 1.0) {
			getAction().gotoPoint(mathHelp.getNextBallPoint(ball));
		} else {
			if((getMemory().getSide().compareTo("l") == 0) && ((getMemory().getPlayMode().compareTo("goalie catch ball_l") == 0) || (getMemory().getPlayMode().compareTo("free_kick_l") == 0))) {
				Thread.sleep(500);
				turn(-getMemory().getDirection());
				Thread.sleep(200);
				kick(100, 0);
				Thread.sleep(100);	
			} else if((getMemory().getSide().compareTo("r") == 0) && ((getMemory().getPlayMode().compareTo("goalie catch ball_r") == 0) || (getMemory().getPlayMode().compareTo("free_kick_r") == 0))) {
				Thread.sleep(500);
				turn(-getMemory().getDirection());
				Thread.sleep(200);
				kick(100, 0);
				Thread.sleep(100);
			} else {
				catchball(getMemory().getBall().getDirection());
			}

			//If ball is in catchable area, catch it
			System.out.println("catchable");
			if (!ballCaught) {
				catchball(getMemory().getBall().getDirection());
				Thread.sleep(100);
				ballCaught = true;	
			}
			//kickToPlayer(closestPlayer());
			getAction().kickToPoint(ball, ridBallPoint);
			Thread.sleep(100);	
		}
	} //end method


	/**
	 * Moves goalie to specific points within the goalbox dependent upon where the ball is on the field.
	 * @param ball An ObjBall representing the ball in play.
	 * @throws Exception 
	 * @pre The ball is visible.
	 * @post The goalie has moved to a strategic position to get between the ball and the goal.
	 */
	public void positionGoalie(ObjBall ball) throws Exception {
		Pos ballPos = mathHelp.getPos(ball.getDistance(), getDirection() + ball.getDirection());
		ballPos = mathHelp.vAdd(getPosition(), ballPos);
		Pos upper = new Pos(-49, -6);
		Pos middle = new Pos(-49, 0);
		Pos lower = new Pos (-49, 6);		

		if (!ballInGoalzone(ball)) {
			if (ballPos.y < -18) {  //If ball is in upper portion of field
				//System.out.println("flag1");
				getAction().gotoSidePoint(upper);
				Thread.sleep(100);
			}
			else if (ballPos.y > -18 && ballPos.y < 18) { //If ball is midfield vertically
				//System.out.println("flag2");
				getAction().gotoSidePoint(middle);
				Thread.sleep(100);
			}
			else {  //If ball is in lower portion of field
				//System.out.println("flag3");
				getAction().gotoSidePoint(lower);
				Thread.sleep(100);
			}
		}
	}

	/**
	 * Moves goalie between the ball and the goal (under construction)
	 * @param ball An ObjBall.
	 * @throws Exception 
	 * @pre Ball is visible to the goalie.
	 * @post The goalie has moved to a point on the line between the ball and the goal.
	 */
	public void getBtwBallAndGoal(ObjBall ball) throws Exception {
		Pos ballPos = mathHelp.getPos(ball.getDistance(), getDirection() + ball.getDirection());
		ballPos = mathHelp.vAdd(getPosition(), ballPos);
		Pos goalPos = getMemory().getOwnGoalPos();
		Pos newPos = new Pos();
		boolean between = false;

		double slope = (goalPos.y - ballPos.y)/(goalPos.x - ballPos.x);
		double x_p = 0.66 * (goalPos.x - ballPos.x) + ballPos.x;
		double y_int = ballPos.y - ballPos.x * slope;
		double y_p = slope * x_p + y_int;
		newPos.x = x_p;
		newPos.y = y_p;

		if (ball.getDirChng() > 20 & !between) {
			getAction().gotoPoint(newPos);
			between = true;
		}		
	}

	/**
	 * Causes goalie to kick the ball to a specific player.  (Currently unused.)
	 * @pre A player is in sight of the goalie.
	 * @post The goalie has kicked the ball to the player passed to the function.
	 * @param player An ObjPlayer representing the player to receive the ball.
	 */
	public void kickToPlayer(ObjPlayer player) {
		if(getMemory().isObjVisible("ball")) {
			ObjBall ball = getMemory().getBall();
			getAction().kickToPoint(ball, mathHelp.getPos(new Polar(player.getDistance(), player.getDirection())));
			ballCaught = false;
		}
	}

	/**
	 * Causes the goalie to kick the ball out of bounds (Currently unused.)
	 * @pre Goalie has control of the ball
	 * @post Ball has been kicked out of bounds
	 */
	public void kickBallOutOfBounds() {
		try {
			//Locate closest flag on a boundary line
			ObjFlag kickFlag = new ObjFlag();
			kickFlag = getMemory().getClosestBoundary();
			//System.out.println("Flag name: " + kickFlag.getFlagName());

			//Test to ensure the flag is within a kickable range, and
			// is not dangerously close to the goal, and kick it if allowable
			if (kickFlag.getDistance() < 25 && kickFlag.getFlagName() != "flt10" && kickFlag.getFlagName() != "fl0"
					&& kickFlag.getFlagName() != "flb10" && kickFlag.getFlagName() != "frt10" 
					&& kickFlag.getFlagName() != "fr0" && kickFlag.getFlagName() != "frb10") {
				kick(90,kickFlag.getDirection());
				//ballCaught = false;
				Thread.sleep(100);
			}			
			else {  //Turn to a new position and check flag again
				turn(-30);
				Thread.sleep(100);

				//Kick if the boundary flag is now reachable
				kickFlag = getMemory().getClosestBoundary();
				if (kickFlag.getDistance() < 25 && kickFlag.getFlagName() != "flt10" && kickFlag.getFlagName() != "fl0"
						&& kickFlag.getFlagName() != "flb10" && kickFlag.getFlagName() != "frt10" 
						&& kickFlag.getFlagName() != "fr0" && kickFlag.getFlagName() != "frb10") {
					kick(90,kickFlag.getDirection());
					ballCaught = false;
					Thread.sleep(100);
				}	
			}
		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void smartKick() {
		//		double leftFlagDirection = knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.LEFT).getDirection();
		//		double rightFlagDirection = knowledgeBase.getFlags().get(FlagCategory.GOAL_OTHER).get(Flag.RIGHT)
		//				.getDirection();
		//
		//		List<SeenPlayer> otherPlayers = knowledgeBase.getOtherPlayers();
		//		double playerSize = (double) this.serverInfo.get(ServerParams.PLAYER_SIZE);
		//
		//		GoalView goalView = KickMathUtility.getGoalView(leftFlagDirection, rightFlagDirection, otherPlayers,
		//				playerSize);
		//
		//		ViewInterval largerInterval = goalView.getLargerInterval();
		//		if (largerInterval.getStart().getBoundType() == BoundType.POST) {
		//			player.kick(9001, largerInterval.getStart().getAngle() + 5);
		//		} else if (largerInterval.getEnd().getBoundType() == BoundType.POST) {
		//			player.kick(9001, largerInterval.getEnd().getAngle() - 5);
		//		} else {
		//			player.kick(9001, largerInterval.getMidAngle());
		//		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				receiveInput();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// GOAP update
			agent.update();
		}
	}

	/********************************************/
	/* 			IGoap implementations 			*/
	/********************************************/

	@Override
	public void planFailed(HashMap<String, Object> failedGoal) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan failed");
	}

	@Override
	public void planFound(HashMap<String, Object> goal, Queue<GoapAction> actions) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan found "+GoapAgent.prettyPrint(actions));
	}

	@Override
	public void actionsFinished() {
		System.out.println("Player "+getMemory().getuNum()+" - Actions finished");
	}

	@Override
	public void planAborted(GoapAction aborter) {
		System.out.println("Player "+getMemory().getuNum()+" - Plan aborted");
	}

	/********************************************/
	/* 				Get And Set 				*/
	/********************************************/

	public double getDirection() {
		return (getMemory().getDirection());
	}

	public Pos getHome() {
		return getMemory().getHome();
	}

	public int getMemTime() {
		return getMemory().getObjMemory().getTime();
	}

	public RoboClient getRoboClient() {
		return roboClient;
	}

	public void setRoboClient(RoboClient roboClient) {
		this.roboClient = roboClient;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public ObjInfo getObjInfo() {
		return objInfo;
	}

	public void setObjInfo(ObjInfo objInfo) {
		this.objInfo = objInfo;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public MathHelp getMathHelp() {
		return mathHelp;
	}

	public void setMathHelp(MathHelp mathHelp) {
		this.mathHelp = mathHelp;
	}

	public boolean isWait() {
		return wait;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public Pos getPosition() {
		return (getMemory().getPosition());
	}

	public String getStringPosition() {
		return position;
	}

	public void setStringPosition(String position) {
		this.position = position;
	}

	public AbstractAI getAi() {
		return ai;
	}

	public void setAi(AbstractAI brain) {
		this.ai = brain;
	}

}
