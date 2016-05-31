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
import robocup.objInfo.ObjInfo;
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
		this.action = new Action(memory, roboClient, this);
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
			getAction().move(x, y);
			Thread.sleep(100);
			if(getMemory().getSide().compareTo("r") == 0) {
				getAction().turn(180);
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
			getAction().move(x, y);
			Thread.sleep(100);
			if(getMemory().getSide().compareTo("r") == 0) {
				getAction().turn(180);
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
