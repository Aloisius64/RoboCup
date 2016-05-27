package robocup.player;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.Action;
import robocup.ai.AbstractAI;
import robocup.memory.Memory;
import robocup.network.RoboClient;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjInfo;
import robocup.objInfo.ObjPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Parser;
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
public abstract class AbstractPlayer extends Thread {

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
	 */
	public void runDefense() throws UnknownHostException, InterruptedException {
		ai.setDefensive();

		while (closestOpponent() == null){
			turn(15);
			Thread.sleep(100);
		}

		//System.out.println("Closest Opponent: " + closestOpponent().getTeam() + " " + closestOpponent().getuNum());
		action.gotoPoint(getMemory().getMathHelp().getNextPlayerPoint(closestOpponent()));

		if (getMemory().isObjVisible("player")) {
			markOpponent(getMemory().getPlayer().getTeam(), Integer.toString(getMemory().getPlayer().getuNum()));
			//System.out.println("Marked Player " + ai.getMarked_team() + " " + ai.getMarked_unum());
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

	@Override
	public void run() {
		while(true) {
			try {
				receiveInput();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(getMemory().getCurrent() != null) {
				Pos pt = mathHelp.vSub(getMemory().getCurrent(), getMemory().getHome());
				getMemory().setHome(!(mathHelp.mag(pt) > 0.5));
			}
		}
	}

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
