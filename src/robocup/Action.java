package robocup;

import java.net.UnknownHostException;

import robocup.memory.Memory;
import robocup.network.RoboClient;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjGoal;
import robocup.objInfo.ObjPlayer;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Pos;

/**
 * @class Action
 *
 * This class holds basic actions for the player to perform, such as ball searching and
 * intercepting, dashing to points, finding the ball and points and getting their coordinates.
 *
 */
public class Action {

	private MathHelp mathHelp = new MathHelp();
	private Memory memory;
	private RoboClient roboClient;
	private Polar oppGoal;
	private boolean atGoal;

	public Action() {

	}

	/**
	 * Constructor with parameters
	 *
	 * @param mem The Memory containing all the parsed information from the server
	 * @param rc The RoboClient that is the player's connection to the server
	 *
	 * @pre Both a full memory and initialized RoboClient must be passed in to avoid any
	 * errors
	 * @post A new set of actions will be available for the player to call on
	 */
	public Action(Memory mem, RoboClient rc) {
		this.memory = mem;
		this.roboClient = rc;
	}

	public double getTurn(Polar go) {
		double angle = go.t - memory.getDirection();
		if(angle > 180)
			angle -= 360;
		else if(angle < -180)
			angle += 360;
		else if(Math.abs(angle) == 180)
			angle = 180;
		return(angle  * (1 + (5*memory.getAmountOfSpeed())));
	}

	/**
	 * This tells the player to turn and run to a point
	 *
	 * @param go The Polar coordinates of the final position, with
	 * the player's position as an origin
	 * @throws Exception 
	 *
	 * @pre The player must have a valid position on the field passed in
	 * @post If the player is not facing the direction of the final position, s/he will
	 * first turn toward it. If the player is approximately facing the position, s/he
	 * will dash toward the direction of the position.
	 */
	public void gotoPoint(Polar go) throws Exception {
		if((go.t > 5.0) || (go.t < -5.0)) {
			roboClient.turn(go.t * (1 + (5*memory.getAmountOfSpeed())));
		}
		roboClient.dash(mathHelp.getDashPower(mathHelp.getPos(go), memory.getAmountOfSpeed(), memory.getDirection(), memory.getEffort(), memory.getStamina()));
	}

	public void gotoSidePoint(Pos p) throws Exception {
		Polar go = memory.getAbsPolar(p);
		if(go.r >= 0.5) {
			roboClient.dash((mathHelp.getDashPower(mathHelp.getPos(go), memory.getAmountOfSpeed(), memory.getDirection(), memory.getEffort(), memory.getStamina())), go.t);
		}
	}

	/**
	 * A cartesian wrapper for the gotoPoint with Polar coordinate
	 *
	 * @param p The Cartesian Pos of position to go to
	 * @throws Exception 
	 * @pre The player must have a valid position on the field passed in
	 *
	 * @post First, the Pos will be converted to a Polar coordinateIf the player is
	 * not facing the direction of the final position, s/he will turn toward it.
	 * If the player is approximately facing the position, s/he will dash toward the
	 * direction of the position.
	 */
	public void gotoPoint(Pos p) throws Exception {
		Polar go = memory.getAbsPolar(p);
		if(go.r >= 0.5) {
			if((go.t) > 5.0 || (go.t) < -5.0) {
				roboClient.turn(go.t * (1 + (5*memory.getAmountOfSpeed())));
			}
			roboClient.dash(mathHelp.getDashPower(mathHelp.getPos(go), memory.getAmountOfSpeed(), memory.getDirection(), memory.getEffort(), memory.getStamina()));
		}
	}

	/**
	 * Take the Player back to his home
	 * 
	 * @pre The player's home should be set at initialization
	 * @post The player will be at his home point 
	 * 
	 * @return true if the player is in the near vicinity of his home, false if he's not there yet
	 * @throws Exception 
	 */

	public void goHome() throws Exception {
		if(!memory.isHome()) {
			gotoSidePoint(memory.getHome());
		}
	}

	/**
	 * A method to find the ball on the field. If it's not in view, the player turns
	 * until he finds it. If the ball is too far, he dashes to get to it. If the ball
	 * is within 20 distance, he intercepts the ball.
	 * @throws Exception 
	 */
	public void findBall() throws Exception {
		if(memory.isObjVisible("ball")) {
			ObjBall ball = memory.getBall();
			if((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
				roboClient.turn(ball.getDirection() * (1 + (5*memory.getAmountOfSpeed())));
				Thread.sleep(100);
			}

			Pos ballPos = memory.getBallPos(ball);

			if((ballPos.x > 52) || (ballPos.x < -20) || (ballPos.y > (memory.getHome().y + 6.4)) || (ballPos.y < (memory.getHome().y - 6.4))) {
				gotoSidePoint(new Pos(memory.getBallPos(ball).x, memory.getHome().y));
			}
			else if((ballPos.x <= 52) && (ballPos.x >= -20) && (ballPos.y <= (memory.getHome().y + 6.4)) && (ballPos.y >= (memory.getHome().y - 6.4)) && (ball.getDistance() > 0.7)){
				interceptBall(ball);
			}
			else if(ball.getDistance() <= 0.7)  {
				dribbleToGoal(ball);
			}
			//Receive pass if hear call from other player
			//TODO
		}
		else
			roboClient.turn(30);
	}

	/**
	 * Defines kickoff behavior
	 * @throws Exception 
	 */
	public void kickOff() throws Exception {
		ObjBall ball = memory.getBall();
		if((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
			roboClient.turn(ball.getDirection() * (1 + (5  *memory.getAmountOfSpeed())));
			Thread.sleep(100);
		}
		interceptBall(ball);
		Thread.sleep(100);
		if(ball.getDistance() <= 0.7)  {
			kickToGoal(ball);
		}
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
		for (int i = 0; i < memory.getPlayers().size(); ++i) {

			if (memory.getPlayers().isEmpty()) {  
				roboClient.turn(30);
			}

			if (!memory.getPlayers().isEmpty()) {  
				if (distance == 0 && memory.getPlayers().get(i).getTeam() == roboClient.getTeam()) {
					distance = memory.getPlayers().get(i).getDistance();
				} else { //Test if this player is closer than the previous one
					if (distance > memory.getPlayers().get(i).getDistance() && memory.getPlayers().get(i).getTeam() == roboClient.getTeam()) {
						distance = memory.getPlayers().get(i).getDistance();
						closestPlayer = memory.getPlayers().get(i);
					}
				}
			}
		}		

		return closestPlayer;
	}

	/*
	 * Passes the ball to the nearest Forward (currently Player).
	 * @param ball An ObjBall for the ball in play.
	 * @param fwd The player to pass the ball to.
	 * @pre The FullBack has control of the ball.
	 * @post The ball has been kicked to the forward.
	 */
	public void passBall(ObjBall ball, ObjPlayer p) throws UnknownHostException {
		roboClient.say("pass" + p.getuNum());
		kickToPoint(ball, mathHelp.getNextPlayerPoint(p));		
	}

	/**
	 * A method to find the ball on the field for FullBacks. If it's not in view, the FullBack turns
	 * until he finds it. If the ball is out of kickable range, he dashes to get to it. If the ball
	 * is within 15 distance, he intercepts the ball, and kicks it away.
	 * @throws Exception 
	 */
	public void FullBack_findBall() throws Exception {
		if(memory.isObjVisible("ball")) {
			ObjBall ball = memory.getBall();
			if((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
				roboClient.turn(ball.getDirection());
				Thread.sleep(100);
			}

			if((ball.getDistance() > 15) && (memory.isHome() == false)) {
				goHome();
			}
			else if((ball.getDistance() <= 15.0) && (ball.getDistance() > 0.7)){
				interceptBall(ball);
			}
			else if(ball.getDistance() <= 0.7)  {
				//kickToPoint(ball, new Pos(0,0));
				passBall(ball, closestPlayer());
			}			
		}
		else
			roboClient.turn(30);
	}

	/**
	 * This method goes to the position that the ball will be in at time t+1 and kicks
	 * it if it is within 0.5 distance.
	 * @param ball
	 * @throws Exception 
	 * @pre A ball must be present and passed
	 * @post The player (should) go to the point where the ball is and kick it
	 */
	private void interceptBall(ObjBall ball) throws Exception {
		Polar p = mathHelp.getNextBallPoint(ball);
		Pos p2 = mathHelp.getPos(p);
		if((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36))
			return;
		else if(stayInBounds()) {
			gotoPoint(p);
			roboClient.dash(mathHelp.getDashPower(mathHelp.getPos(p), memory.getAmountOfSpeed(), memory.getDirection(), memory.getEffort(), memory.getStamina()));
		}
	}

	/**
	 * This will attempt to keep the player from making any sudden moves while the play
	 * mode is one in which he can't get to the ball. This keeps them from going out of
	 * bounds for a ball.
	 * 
	 * @pre The Memory.side should not be null
	 * @return True if the player is able to play, false if he is not
	 */
	private boolean stayInBounds() {
		if(memory.getSide().compareTo("l") == 0) {
			if((memory.getPlayMode().compareTo("play_on") != 0) && (memory.getPlayMode().compareTo("kick_off_l") != 0)) {
				return false;
			} else
				return true;
		}
		else {
			if((memory.getPlayMode().compareTo("play_on") != 0) && (memory.getPlayMode().compareTo("kick_off_r") != 0)) {
				return false;
			} else
				return true;
		}
	}

	/**
	 * If the goal is within sight, this will kick the ball to it with maximum power. If
	 * the goal is not in sight, this will kick towards the direction of the goal with 
	 * maximum power.
	 * 
	 * @param ball
	 * @throws UnknownHostException 
	 * 
	 * @pre The ball passed in should not be null
	 * @post The ball will be kicked in the direction of the goal
	 * 
	 */
	private void kickToGoal(ObjBall ball) throws UnknownHostException {
		ObjGoal goal = memory.getOppGoal();
		if(goal != null) {
			roboClient.kick(100, goal.getDirection() - memory.getDirection());
			System.out.println("I see the goal");
		}
		else {
			roboClient.kick(100, memory.getDirection());
			System.out.println("Null Goal");
		}
	}

	/**
	 * Kicks ball to a certain Polar point
	 * 
	 * @param ball
	 * @param p The Polar coordinate to kick the ball to
	 * 
	 * @pre The ball passed in should not be null and p should be within the field from the player
	 * @post The ball will be kicked to the vicinity of the point
	 * 
	 */
	public void kickToPoint(ObjBall ball, Polar p) {
		if(ball.getDistance() <= 0.7) {
			try {
				roboClient.kick(
						mathHelp.getKickPower(p, memory.getAmountOfSpeed(), memory.getDirection(), ball.getDistance(), ball.getDirection()),
						p.t);
			} catch (UnknownHostException e) {
				System.out.println("Error in Action.kickToPoint");
				e.printStackTrace();
			}
		}
	}

	/**
	 * A Pos wrapper for the kickToPoint
	 * @param ball 
	 * @param p the Pos of the coordinate to kick the ball to
	 */
	public void kickToPoint(ObjBall ball, Pos p) {
		Pos  pt = mathHelp.vSub(p, memory.getPosition());
		Polar go = memory.getAbsPolar(pt);
		kickToPoint(ball, go);
	}

	/**
	 * This dribbles the ball in the direction of the goal until it's 18 feet outside of the 
	 * goal, when it kicks the ball with maximum power into the goal.
	 * 
	 * @param ball
	 * @throws UnknownHostException 
	 * 
	 * @pre The ball should not be null
	 * @post This will result in a dribble and a shoot
	 */
	public void dribbleToGoal(ObjBall ball) throws UnknownHostException {
		if(stayInBounds()) {
			ObjGoal goal = memory.getOppGoal();

			if((goal != null) && (memory.getPosition().x < 35.0)) {
				kickToPoint(ball, new Polar(5.0, (goal.getDirection() - ball.getDirection())));

			}
			else if((goal != null) && (memory.getPosition().x >= 35.0)) {
				System.out.println("Ready to shoot");
				kickToGoal(ball);
			}
			else if(goal == null) {
				roboClient.kick(5.0, memory.getNullGoalAngle());
			}
		}
	}

	public MathHelp getMathHelp() {
		return mathHelp;
	}

	public void setMathHelp(MathHelp mathHelp) {
		this.mathHelp = mathHelp;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public RoboClient getRoboClient() {
		return roboClient;
	}

	public void setRoboClient(RoboClient roboClient) {
		this.roboClient = roboClient;
	}

	public Polar getOppGoal() {
		return oppGoal;
	}

	public void setOppGoal(Polar oppGoal) {
		this.oppGoal = oppGoal;
	}

	public boolean isAtGoal() {
		return atGoal;
	}

	public void setAtGoal(boolean atGoal) {
		this.atGoal = atGoal;
	}

}
