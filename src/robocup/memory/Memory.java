package robocup.memory;

/**
 * @file Memory.java
 *
 * The Memory class stores instances of ObjMemory and SenseMemory and supplies
 * methods to access their innards.
 *
 * @author granthays
 * @date 11/10/11
 * @version 3.0
 *
 */
import java.util.ArrayList;

import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjGoal;
import robocup.objInfo.ObjInfo;
import robocup.objInfo.ObjLine;
import robocup.objInfo.ObjMemory;
import robocup.objInfo.ObjPlayer;
import robocup.utility.Field;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Pos;

/**
 * @class Memory
 */
public class Memory {

	private MathHelp mathHelp = new MathHelp();
	private Field field;
	private Pos home;
	private Pos current = new Pos();
	private boolean isHome = true;
	private ArrayList<ObjPlayer> teammates = new ArrayList<ObjPlayer>();
	private ArrayList<ObjPlayer> opponents = new ArrayList<ObjPlayer>();
	/**
	 * The memory that stores all parsed ObjInfo
	 */
	private ObjMemory objMemory;
	/**
	 * The memory that stores all parsed SenseInfo
	 */
	private SenseMemory senseMemory;
	/**
	 * The memory that stores all message HearInfo
	 */
	private HearMemory hearMemory;
	/**
	 * The play mode as told by the referee
	 */
	private String playMode;

	/**
	 * The string of the opponents side
	 */
	private String oppSide;
	/**
	 * The String of the player's side
	 */
	private String side;
	/**
	 * The player's uniform number
	 */
	private int uNum;
	/**
	 * The Pos of the coordinates of the opponents goal
	 */
	@SuppressWarnings("unused")
	private Pos oppGoal;	

	/**
	 * The default constructor for the Memory.
	 *
	 * This creates new, empty ArrayList for the ObjMemory and SenseMemory,
	 * initiates the time at 0 for both, and creates an ObjMemory and
	 * SenseMemory with the new ArrayLists and time as parameters.
	 */
	public Memory() {
		ArrayList<ObjInfo> newObjArray = new ArrayList<>();
		int time = 0;
		setObjMemory(new ObjMemory(newObjArray, time));
		setSenseMemory(new SenseMemory());
		setHearMemory(new HearMemory());
	}

	/**
	 * This sets the orientation of the Field positions depending on side the
	 * player starts on.
	 *
	 * @param side
	 *
	 * @pre The side String should not be null
	 * @post The Field orientation will be set
	 */
	public void setField(String side) {
		field = new Field(side);
		if (side.compareTo("l") == 0) {
			setOppGoal(getFlagPos("gr"));
			oppSide = "r";
		} else {
			setOppGoal(getFlagPos("gl"));
			oppSide = "l";
		}
	}

	/**
	 * The ObjInfo getter
	 *
	 * This fetches the ObjInfo at index i of the ArrayList ObjArray in
	 * ObjMemory, and returns it as an ObjInfo.
	 *
	 * @param i the index number of the location of the desired ObjInfo in
	 * ObjArray
	 * @pre An index needs to be supplied when calling this
	 * @post A basic ObjInfo will be given.
	 * @return ObjInfo the ObjInfo at location i of the ObjArray
	 */
	// Get Object
	public ObjInfo getObj(int i) {
		return getObjMemory().getObj(i);
	}

	/**
	 * The ObjMemory size
	 *
	 * A getter to quickly retrieve the number of ObjInfo in ObjMemory
	 *
	 * @return size of ObjMemory
	 */
	public int getObjMemorySize() {
		return getObjMemory().getSize();
	}

	/**
	 * Is this ObjInfo visible?
	 *
	 * @param name the ObjName of the ObjInfo we're detecting visibility of
	 * @return true if the ball is in the ObjMemory, false if it is not or if
	 * the the ObjMemory is empty
	 */
	public boolean isObjVisible(String name) {
		if (getObjMemory().getSize() == 0) {
			return false;
		} else {
			for (int i = 0; i < getObjMemory().getSize(); i++) {
				if (getObj(i).getObjName().compareTo(name) == 0) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * The Ball Getter
	 *
	 * @pre Make sure you either check visibility first
	 * @post If the ball is in the Memory, it will be returned. Otherwise a Null
	 * ObjBall will be sent.
	 * @return ObjBall containing the ball
	 */
	public ObjBall getBall() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if (getObj(i).getObjName().compareTo("ball") == 0) {
				return (ObjBall) getObj(i);
			}
		}
		return null;
	}

	public Pos getBallPos(ObjBall b) {
		Pos pt = getMathHelp().vAdd(getPosition(), getMathHelp().getPos(b.getDistance(), b.getDirection() + getDirection()));
		//pt.print("Ball Pos: ");
		return (pt);
	}

	/**
	 * The Flag Getter
	 *
	 * If you're looking for a specific flag, this is you're guy. You need to
	 * pass in the FlagName (i.e. flb30) into it, and out pops the ObjFlag with
	 * that FlagName attached to it.
	 *
	 * @pre Make sure you either check visibility first
	 * @post If the flag is in the Memory, it will be returned. Otherwise a Null
	 * ObjFlag will be sent.
	 * @return ObjFlag containing the flag with specified name
	 */
	public ObjFlag getFlag(String name) {
		ObjFlag newFlag = new ObjFlag();
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if (getObj(i).getObjName().compareTo("flag") == 0) {
				newFlag = (ObjFlag) getObj(i);
			}
			if (newFlag.getFlagName().compareTo(name) == 0) {
				return newFlag;
			}
		}
		return null;
	}

	/**
	 * The Goal Opponent Getter
	 *
	 * This will get the Opponent's ObjGoal if it's in your field of vision.
	 *
	 * @post If you're facing the opponenet's goal, an ObjGoal with it's
	 * information will be returned. Otherwise a null ObjGoal will be sent
	 * @return ObjGoal containing the goal if it's in your vision, null if not
	 */
	public ObjGoal getOppGoal() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if ((getObj(i).getObjName().compareTo("goal") == 0) && (getObj(i).getSide().compareTo(oppSide) == 0)) {
				return (ObjGoal) getObj(i);
			}
		}
		return null;
	}

	/**
	 * This returns the Pos with the coordinate to the goal you're trying to
	 * score on.
	 *
	 * @return the Pos in the Field of your oppenent's goal
	 */
	public Pos getOppGoalPos() {
		if (getSide().compareTo("l") == 0) {
			return (getFlagPos("gr"));
		} else {
			return (getFlagPos("gl"));
		}
	}

	/**
	 * The Goal Own Getter
	 *
	 * This will get your own ObjGoal if it's in your field of vision.
	 *
	 * @post If you're facing your goal, an ObjGoal with it's information will
	 * be returned. Otherwise a null ObjGoal will be sent
	 * @return ObjGoal containing the goal if it's in your vision, null if not
	 */
	public ObjGoal getOwnGoal() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if ((getObj(i).getObjName().compareTo("goal") == 0) && (getObj(i).getSide().compareTo(getSide()) == 0)) {
				return (ObjGoal) getObj(i);
			}
		}
		return null;
	}

	/**
	 * This returns the Pos with the coordinate to the goal you're trying to
	 * guard.
	 *
	 * @return the Pos in the Field of your goal
	 */
	public Pos getOwnGoalPos() {
		if (getSide().compareTo("l") == 0) {
			return (getFlagPos("gl"));
		} else {
			return (getFlagPos("gr"));
		}
	}

	/**
	 * The Player Getter
	 *
	 * This will get the ObjPlayer of the first player you see.
	 *
	 * @return ObjPlayer
	 */
	public ObjPlayer getPlayer() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if (getObj(i).getObjName().compareTo("player") == 0) {
				return (ObjPlayer) getObj(i);
			}
		}
		return null;
	}

	/**
	 * The Line getter This will get the ObjLine of the first line you see.
	 *
	 * @return ObjLine
	 */
	public ObjLine getLine() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if (getObj(i).getObjName().compareTo("line") == 0) {
				return (ObjLine) getObj(i);
			}
		}
		return null;
	}

	/**
	 * This will test a players local time against the ObjMemory's time. This
	 * can be used to ensure that more than one action will not be attempted
	 * during a single simulation step.
	 *
	 * @param t the Player's local time
	 * @pre A player's local time must be initialized and passed in
	 * @post The player's local time needs to be set to the Memory's time after
	 * a true is returned.
	 * @return true if the newly parsed Memory's time is greater than the
	 * players local time. False if the memory time is <= the local time.
	 */
	public boolean timeCheck(int t) {
		if (t < getObjMemory().getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets an ArrayList with all of the Players in your sight
	 *
	 * @return players
	 */
	public ArrayList<ObjPlayer> getPlayers() {
		ArrayList<ObjPlayer> players = new ArrayList<ObjPlayer>();
		for (int i = 0; i < getObjMemory().getSize(); i++) {
			if (getObj(i).getObjName().compareTo("player") == 0) {
				players.add((ObjPlayer) getObj(i));
			}            
		}
		return players;
	}

	public void getPlayerArrays() {
		for (int i = 0; i < getObjMemory().getSize(); i++) {

		}
	}

	/**
	 * This gets the closest line in your sight
	 *
	 * @return line
	 */
	public ObjLine getClosestLine() {
		ObjLine line = new ObjLine();
		ObjLine closestLine = null;
		double dist = 100.0;

		for (int i = 0; i < getObjMemorySize(); i++) {
			if (getObj(i).getObjName().compareTo("line") == 0) {
				line = (ObjLine) getObj(i);
				if (line.getDistance() < dist) {
					closestLine = line;
				}
			}
		}

		return closestLine;
	}

	/**
	 * Calculates the direction your facing from the closest line in your
	 * vision. The direction returned from a line is the angle made by your line
	 * of sight and the point that it crosses the line. This will will allow the
	 * facing direction to be calculated with some arithmetic.
	 *
	 * @return the absolute direction you're facing
	 */
	public double getDirection() {
		ObjLine line = getClosestLine();

		if (line == null) {

		} else if (line.getSide().compareTo("t") == 0) {
			if (line.getDirection() > 0) {
				return (-1 * line.getDirection());
			} else {
				return (-180 - line.getDirection());
			}
		} else if (line.getSide().compareTo("b") == 0) {
			if (line.getDirection() < 0) {
				return (-1 * line.getDirection());
			} else {
				return (180 - line.getDirection());
			}
		} else if (line.getSide().compareTo(getSide()) == 0) {
			if (Math.abs(line.getDirection()) == 90.0) {
				return (180.0);
			} else if (line.getDirection() > 0) {
				return (-90 - line.getDirection());
			} else if (line.getDirection() < 0) {
				return (90 - line.getDirection());
			}
		} else if (line.getSide().compareTo(oppSide) == 0) {
			if (Math.abs(line.getDirection()) == 90.0) {
				return (0.0);
			} else if (line.getDirection() > 0) {
				return (90 - line.getDirection());
			} else if (line.getDirection() < 0) {
				return (-90 - line.getDirection());
			}
		}

		return (0.0);
	}

	public Polar getAbsPolar(Pos pt) {
		Pos p = (getMathHelp().vSub(pt, getPosition()));
		double r = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
		double t = (Math.toDegrees(Math.atan2(p.y, p.x)) - getDirection());
		Polar n = new Polar(r, t);
		//n.print("AbsPolar: ");
		return (n);
	}

	/**
	 * Sets the Pos of the originating point.
	 *
	 * @param x
	 * @param y
	 */
	public void setLocation(double x, double y) {
		this.getHome().x = x;
		this.getHome().y = y;
	}

	/**
	 * Finds the closest flag in your sight
	 *
	 * @return ObjFlag containing closest flag
	 */
	public ObjFlag getClosestFlag() {

		ObjFlag flag = new ObjFlag();
		ObjFlag closestFlag = null;

		double dist = 100.0;

		for (int i = 0; i < getObjMemorySize(); i++) {

			if (getObj(i).getObjName().compareTo("flag") == 0) {
				flag = (ObjFlag) getObj(i);
				if (flag.getDistance() < dist) {
					closestFlag = flag;
					dist = flag.getDistance();
				}
			}
		}

		return closestFlag;
	}

	/**
	 * Finds ObjFlag of the closest boundary flag in players sight.
	 *
	 * @return closest boundary
	 */
	public ObjFlag getClosestBoundary() {

		ObjFlag flag = new ObjFlag();
		ObjFlag closestFlag = null;

		double dist = 100.0;

		for (int i = 0; i < getObjMemorySize(); i++) {

			if (getObj(i).getObjName().compareTo("flag") == 0) {
				flag = (ObjFlag) getObj(i);
				if ((flag.getFlagType().compareTo("b") == 0) && (flag.getDistance() < dist)) {
					closestFlag = flag;
					dist = flag.getDistance();
				}
			}
		}

		return closestFlag;
	}

	/**
	 * Finds ObjFlag of the closest penalty box flag in players sight.
	 *
	 * @return closest penalty box flag
	 */
	public ObjFlag getClosestPenaltyFlag() {

		ObjFlag flag = new ObjFlag();
		ObjFlag closestFlag = null;

		double dist = 100.0;

		for (int i = 0; i < getObjMemorySize(); i++) {

			if (getObj(i).getObjName().compareTo("flag") == 0) {
				flag = (ObjFlag) getObj(i);
				if ((flag.getFlagType().compareTo("p") == 0) && (flag.getDistance() < dist)) {
					closestFlag = flag;
					dist = flag.getDistance();
				}
			}

		}

		return closestFlag;
	}

	/**
	 * Returns the Pos of the coordinate of any flag on the field by name
	 *
	 * @param flagName
	 *
	 * @return Pos with coordinate of flag
	 */
	public Pos getFlagPos(String flagName) {
		for (int i = 0; i < field.posList.size(); i++) {
			if (field.posList.get(i).name.compareTo(flagName) == 0) {
				return field.posList.get(i);
			}
		}

		return null;

	}

	/**
	 * This finds the absolute position of a player using vector arithmetic and
	 * trigonometry and the closest flag to the player and the facing direction
	 * found from the closest line.
	 *
	 * @return Pos containing the coordinate on the field of the player's
	 * absolute position
	 */
	public Pos getPosition() {

		ObjFlag flag = getClosestFlag();

		//System.out.println("getPosition flag: (" + flag.getDistance() + ", " + flag.getDirection() + ")");
		{

			Pos flagCoord = getFlagPos(flag.getFlagName());
			Pos toFlag = getMathHelp().getPos(flag.getDistance(), getDirection() + flag.getDirection());
			Pos self = getMathHelp().vSub(flagCoord, toFlag);

			return (self);

		}

	}

	public void setCurrent() {
		current = getPosition();
	}

	/**
	 * Calculates the angle of goal you're trying to score on when the goal is
	 * not in your sight. This is allows the player to kick or dribble to the
	 * goal, even when it's information isn't available.
	 *
	 * @return double containing the angle of the goal
	 */
	public double getNullGoalAngle() {
		Pos g = getMathHelp().vSub(getPosition(), new Pos(50.5, 0));
		double ga = Math.atan(g.y / g.x);
		return (ga - getDirection());
	}

	// ******************* SenseMemory *******************
	/**
	 * The getter for the Player's stamina
	 */
	public double getStamina() {
		return getSenseMemory().stamina;
	}

	/**
	 * The getter for the Player's stamina recovery
	 */
	public double getRecovery() {
		return getSenseMemory().recovery;
	}

	/**
	 * The getter for the Player's stamina effort
	 */
	public double getEffort() {
		return getSenseMemory().effort;
	}

	/**
	 * The getter for the magnitude of the Player's velocity
	 */
	public double getAmountOfSpeed() {
		return getSenseMemory().amountOfSpeed;
	}

	/**
	 * The getter for the direction of the Player's velocity
	 */
	public double getDirectionOfSpeed() {
		if (getSenseMemory().directionOfSpeed == 0) {
			return 0.0;
		} else {
			return (-1 * getSenseMemory().directionOfSpeed);
		}
	}

	/**
	 * The getter for the angle of the Player's head relative to the orientation
	 * of the Player's positive y-axis (up-field)
	 */
	public double getHeadDirection() {
		return getSenseMemory().headDirection;
	}

	// ****************** Hear Memory ********************
	/**
	 * The getter for the game's current play mode
	 */
	public String getPlayMode() {
		return playMode;
	}

	public HearMemory getHearMemory() {
		return hearMemory;
	}

	public void setHearMemory(HearMemory hearMemory) {
		this.hearMemory = hearMemory;
	}

	public ArrayList<ObjPlayer> getTeammates() {
		return teammates;
	}

	public void setTeammates(ArrayList<ObjPlayer> teammates) {
		this.teammates = teammates;
	}

	public ArrayList<ObjPlayer> getOpponents() {
		return opponents;
	}

	public void setOpponents(ArrayList<ObjPlayer> opponents) {
		this.opponents = opponents;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public Pos getCurrent() {
		return current;
	}

	public void setCurrent(Pos current) {
		this.current = current;
	}

	public int getuNum() {
		return uNum;
	}

	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

	public void setOppGoal(Pos oppGoal) {
		this.oppGoal = oppGoal;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public void setPlayMode(String playMode) {
		this.playMode = playMode;
	}

	public ObjMemory getObjMemory() {
		return objMemory;
	}

	public void setObjMemory(ObjMemory objMemory) {
		this.objMemory = objMemory;
	}

	public SenseMemory getSenseMemory() {
		return senseMemory;
	}

	public void setSenseMemory(SenseMemory senseMemory) {
		this.senseMemory = senseMemory;
	}

	public Pos getHome() {
		return home;
	}

	public void setHome(Pos home) {
		this.home = home;
	}

	public MathHelp getMathHelp() {
		return mathHelp;
	}

	public void setMathHelp(MathHelp mathHelp) {
		this.mathHelp = mathHelp;
	}

}