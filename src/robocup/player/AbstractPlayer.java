package robocup.player;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.Action;
import robocup.goap.GoapAgent;
import robocup.goap.IGoap;
import robocup.memory.Memory;
import robocup.network.RoboClient;
import robocup.objInfo.ObjInfo;
import robocup.utility.Parser;
import robocup.utility.Position;

/**
 * The Player class defines all objects and methods used for the Player within
 * the RoboCup match. The Player establishes a connection to the server,
 * initializes itself on the team, and performs all actions related to a RoboCup
 * soccer player such as (but not limited to) kicking, dashing, dribbling,
 * passing and scoring. The Player class has a Memory for storing the current
 * RoboCup worldstate. It reacts to stimuli based on strategies provided by the
 * Brain (TBD).
 */
public abstract class AbstractPlayer extends Thread implements IGoap {

	public static final double MAX_VIEW = 90.0;
	protected RoboClient roboClient;
	protected Memory memory;
	protected ObjInfo objInfo;
	protected Parser parser;
	protected Action action;
	protected int time;
	protected String position;
	private GoapAgent agent;
	private String formationName;
	private int currentEvaluation;

	public AbstractPlayer() {
		super();
		init();
	}

	public AbstractPlayer(String team) {
		super();
		init();
		this.roboClient.setTeam(team);
	}

	private void init() {
		this.roboClient = new RoboClient();
		this.memory = new Memory(this);
		this.objInfo = new ObjInfo();
		this.parser = new Parser();
		this.action = new Action(this);
		this.time = 0;
		this.currentEvaluation = 0;
		this.position = "left";
		this.setAgent(new GoapAgent(this));
	}

	public void initPlayer(double x, double y, String pos) throws SocketException, UnknownHostException {
		position = pos;
		roboClient.setDsock(new DatagramSocket());
		roboClient.init(getParser(), getMemory());
		getMemory().setHome(new Position(x, y));

		try {
			getAction().move(x, y);
			Thread.sleep(100);
			if (getMemory().getSide().compareTo("r") == 0) {
				getAction().turn(180);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initGoalie(double x, double y) throws SocketException, UnknownHostException {
		roboClient.setDsock(new DatagramSocket());
		roboClient.initGoalie(getParser(), getMemory());
		getMemory().setHome(new Position(x, y));

		try {
			getAction().move(x, y);
			Thread.sleep(100);
			if (getMemory().getSide().equals("r")) {
				getAction().turn(180);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initPlayer(double x, double y) throws SocketException, UnknownHostException {
		roboClient.setDsock(new DatagramSocket());
		roboClient.init(getParser(), getMemory());
		getMemory().setHome(new Position(x, y));

		try {
			getAction().move(x, y);
			Thread.sleep(100);
			if (getMemory().getSide().equals("r")) {
				getAction().turn(180);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives worldstate data from the RoboCup server.
	 * 
	 * @pre A RoboCup server is available.
	 * @post The current worldstate has been stored in the Memory.
	 */
	public void receiveInput() throws InterruptedException {
		parser.Parse(roboClient.receive(), memory);
	}

	@Override
	public void run() {
		while (true) {

			try {
				receiveInput();
				// Thread.sleep(100);

				if (getMemory().timeCheck(getTime())) {
					setTime(getMemory().getObjMemory().getTime());
					getAgent().update();

					broadcast(evaluate());
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public abstract int evaluate();

	public abstract void broadcast(int evaluation) throws UnknownHostException, InterruptedException;

	/********************************************/
	/* Get And Set */
	/********************************************/

	public double getDirection() {
		return (getMemory().getDirection());
	}

	public Position getHome() {
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

	public Position getPosition() {
		return (getMemory().getPosition());
	}

	public String getStringPosition() {
		return position;
	}

	public void setStringPosition(String position) {
		this.position = position;
	}

	public GoapAgent getAgent() {
		return agent;
	}

	public void setAgent(GoapAgent agent) {
		this.agent = agent;
	}

	public String getFormationName() {
		return formationName;
	}

	public void setFormationName(String formationName) {
		this.formationName = formationName;
	}

	public int getCurrentEvaluation() {
		return currentEvaluation;
	}

	public void setCurrentEvaluation(int currentEvaluation) {
		this.currentEvaluation = currentEvaluation;
	}

}
