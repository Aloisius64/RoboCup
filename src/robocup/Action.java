package robocup;

import java.net.UnknownHostException;
import java.util.List;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationManager;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjGoal;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.player.OffensivePlayer;
import robocup.utility.Field;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;
import robocup.utility.kick.BoundType;
import robocup.utility.kick.GoalView;
import robocup.utility.kick.KickMathUtility;
import robocup.utility.kick.ViewInterval;

/**
 * This class holds basic actions for the player to perform, such as ball
 * searching and intercepting, dashing to points, finding the ball and
 * points and getting their coordinates.
 */
public class Action {

	private final AbstractPlayer player;
	private Polar oppGoal;
	private boolean atGoal;
	protected boolean ballTurn = false;
	protected boolean ballCaught = false;

	public Action(AbstractPlayer player) {
		this.player = player;
	}

	public double getTurn(Polar go) {
		double angle = go.t - player.getMemory().getDirection();
		if (angle > 180)
			angle -= 360;
		else if (angle < -180)
			angle += 360;
		else if (Math.abs(angle) == 180)
			angle = 180;
		return (angle * (1 + (5 * player.getMemory().getAmountOfSpeed())));
	}

	/**
	 * This tells the player to turn and run to a point
	 *
	 * @param go
	 *            The Polar coordinates of the final position, with the player's
	 *            position as an origin
	 * @throws Exception
	 *
	 * @pre The player must have a valid position on the field passed in
	 * @post If the player is not facing the direction of the final position,
	 *       s/he will first turn toward it. If the player is approximately
	 *       facing the position, s/he will dash toward the direction of the
	 *       position.
	 */
	public void gotoPoint(Polar go) throws Exception {
		if ((go.t > 5.0) || (go.t < -5.0)) {
			player.getRoboClient().turn(go.t * (1 + (5 * player.getMemory().getAmountOfSpeed())));
		}
		player.getRoboClient().dash(
				MathHelp.getDashPower(MathHelp.getPos(go), 
						player.getMemory().getAmountOfSpeed(), player.getMemory().getDirection(),
						player.getMemory().getEffort(), player.getMemory().getStamina()));
	}

	public void gotoSidePoint(Position p) throws Exception {
		Polar go = player.getMemory().getAbsPolar(p);
		if (go.r >= 0.5) {
			player.getRoboClient().dash((MathHelp.getDashPower(MathHelp.getPos(go), player.getMemory().getAmountOfSpeed(),
					player.getMemory().getDirection(), player.getMemory().getEffort(), player.getMemory().getStamina())), go.t);
		}
	}

	/**
	 * A cartesian wrapper for the gotoPoint with Polar coordinate
	 *
	 * @param p
	 *            The Cartesian Pos of position to go to
	 * @throws Exception
	 * @pre The player must have a valid position on the field passed in
	 *
	 * @post First, the Pos will be converted to a Polar coordinateIf the player
	 *       is not facing the direction of the final position, s/he will turn
	 *       toward it. If the player is approximately facing the position, s/he
	 *       will dash toward the direction of the position.
	 */
	public void gotoPoint(Position p) throws Exception {
		Polar go = player.getMemory().getAbsPolar(p);
		if (go.r >= 0.5) {
			if ((go.t) > 5.0 || (go.t) < -5.0) {
				player.getRoboClient().turn(go.t * (1 + (5 * player.getMemory().getAmountOfSpeed())));
			}
			player.getRoboClient().dash(MathHelp.getDashPower(MathHelp.getPos(go), player.getMemory().getAmountOfSpeed(), player.getMemory().getDirection(),
					player.getMemory().getEffort(), player.getMemory().getStamina()));
		}
	}

	/**
	 * Take the Player back to his home
	 * 
	 * @pre The player's home should be set at initialization
	 * @post The player will be at his home point
	 * 
	 * @return true if the player is in the near vicinity of his home, false if
	 *         he's not there yet
	 * @throws Exception
	 */

	public void goHome() throws Exception {
		if (!player.getMemory().isHome()) {
			gotoPoint(player.getMemory().getHome());
			//			gotoSidePoint(player.getMemory().getHome());
		}
	}

	public boolean isHome() {
		return player.getMemory().isHome();
	}

	public void forwardToGoal() throws Exception {
		if (player.getMemory().getPlayMode().equals("play_on")) {
			if (player.getMemory().isObjVisible("ball")) {
				ObjBall ball = player.getMemory().getBall();
				Position ballPos = player.getMemory().getBallPos(ball);
				System.out.println("step " + player.getMemory().getObjMemory().getTime() + " " + ballPos.x + " " + ballPos.x);
				if (player.getMemory().getOppGoal() != null && player.getMemory().getOppGoal().getDistance() < 20.0) {
					if (player.getMemory().seeOpponentPost() == Field.NO_LEFT_POST) {
						turn(-30.0);
					} else if (player.getMemory().seeOpponentPost() == Field.NO_RIGHT_POST) {
						turn(30.0);

					} else {
						if (player.getMemory().getSide().equals("l")) {
							String flagLeft = "fgrt";
							String flagRight = "fgrb";
							smartKick(flagLeft, flagRight);
						} else {
							String flagLeft = "fglb";
							String flagRight = "fglt";
							smartKick(flagLeft, flagRight);
						}
					}
				} else {
					if (ball.getDistance() < 0.7) {
						if (player.getMemory().getOppGoal() != null)
							kick(10, player.getMemory().getOppGoal().getDirection());
						else {
							turn(30.0);
						}
					} else {
						turn(ball.getDirection());
						dash(100);
					}
				}
			} else {
				player.getRoboClient().turn(30.0);
			}
		}
	}

	/**
	 * A method to find the ball on the field. If it's not in view, the player
	 * turns until he finds it. If the ball is too far, he dashes to get to it.
	 * If the ball is within 20 distance, he intercepts the ball.
	 * 
	 * @throws Exception
	 */
	public void findBall() throws Exception {
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
				player.getRoboClient().turn(ball.getDirection() * (1 + (5 * player.getMemory().getAmountOfSpeed())));
				Thread.sleep(100);
			}

			Position ballPos = player.getMemory().getBallPos(ball);

			if ((ballPos.x > 52) || (ballPos.x < -20) || (ballPos.y > (player.getMemory().getHome().y + 6.4))
					|| (ballPos.y < (player.getMemory().getHome().y - 6.4))) {
				gotoSidePoint(new Position(player.getMemory().getBallPos(ball).x, player.getMemory().getHome().y));
			} else if ((ballPos.x <= 52) && (ballPos.x >= -20) && (ballPos.y <= (player.getMemory().getHome().y + 6.4))
					&& (ballPos.y >= (player.getMemory().getHome().y - 6.4)) && (ball.getDistance() > 0.7)) {
				interceptBall(ball);
			} else if (ball.getDistance() <= 0.7) {
				dribbleToGoal(ball);
			}
			// Receive pass if hear call from other player
			// TODO
		} else
			player.getRoboClient().turn(30);
	}

	/**
	 * Defines kickoff behavior
	 * 
	 * @throws Exception
	 */
	public void kickOff() throws Exception {
		ObjBall ball = player.getMemory().getBall();
		if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
			player.getRoboClient().turn(ball.getDirection() * (1 + (5 * player.getMemory().getAmountOfSpeed())));
			Thread.sleep(100);
		}
		interceptBall(ball);
		Thread.sleep(100);
		if (ball.getDistance() <= 0.7) {
			kickToGoal(ball);
		}
	}

	/*
	 * Passes the ball to the nearest Forward (currently Player).
	 * 
	 * @param ball An ObjBall for the ball in play.
	 * 
	 * @param fwd The player to pass the ball to.
	 * 
	 * @pre The FullBack has control of the ball.
	 * 
	 * @post The ball has been kicked to the forward.
	 */
	public boolean passBall(ObjBall ball, ObjPlayer p) throws UnknownHostException {
		if(p!=null){
			//player.getRoboClient().say("pass" + p.getuNum());
			kickToPoint(ball, MathHelp.getNextPlayerPoint(p));
			return true;
		}
		return false;
	}

	/**
	 * A method to find the ball on the field for FullBacks. If it's not in
	 * view, the FullBack turns until he finds it. If the ball is out of
	 * kickable range, he dashes to get to it. If the ball is within 15
	 * distance, he intercepts the ball, and kicks it away.
	 * 
	 * @throws Exception
	 */
	public void FullBack_findBall() throws Exception {
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
				player.getRoboClient().turn(ball.getDirection());
				Thread.sleep(100);
			}

			if ((ball.getDistance() > 15) && (player.getMemory().isHome() == false)) {
				goHome();
			} else if ((ball.getDistance() <= 15.0) && (ball.getDistance() > 0.7)) {
				interceptBall(ball);
			} else if (ball.getDistance() <= 0.7) {
				// kickToPoint(ball, new Pos(0,0));
				passBall(ball, closestTeammate());
			}
		} else
			player.getRoboClient().turn(30);
	}

	/**
	 * This method goes to the position that the ball will be in at time t+1 and
	 * kicks it if it is within 0.5 distance.
	 * 
	 * @param ball
	 * @throws Exception
	 * @pre A ball must be present and passed
	 * @post The player (should) go to the point where the ball is and kick it
	 */
	public boolean interceptBall(ObjBall ball) throws Exception {
		Polar p = MathHelp.getNextBallPoint(ball);
		Position p2 = MathHelp.getPos(p);
		if ((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36))
			return false;
		else if (stayInBounds()) {
			gotoPoint(p);
			//			player.getRoboClient().dash(MathHelp.getDashPower(MathHelp.getPos(p), player.getMemory().getAmountOfSpeed(), player.getMemory().getDirection(),
			//					player.getMemory().getEffort(), player.getMemory().getStamina()));
		}
		return true;
	}

	/**
	 * This will attempt to keep the player from making any sudden moves while
	 * the play mode is one in which he can't get to the ball. This keeps them
	 * from going out of bounds for a ball.
	 * 
	 * @pre The Memory.side should not be null
	 * @return True if the player is able to play, false if he is not
	 */
	public boolean stayInBounds() {
		if (player.getMemory().getSide().compareTo("l") == 0) {
			if ((player.getMemory().getPlayMode().compareTo("play_on") != 0)
					&& (player.getMemory().getPlayMode().compareTo("kick_off_l") != 0)) {
				return false;
			} else
				return true;
		} else {
			if ((player.getMemory().getPlayMode().compareTo("play_on") != 0)
					&& (player.getMemory().getPlayMode().compareTo("kick_off_r") != 0)) {
				return false;
			} else
				return true;
		}
	}

	/**
	 * If the goal is within sight, this will kick the ball to it with maximum
	 * power. If the goal is not in sight, this will kick towards the direction
	 * of the goal with maximum power.
	 * 
	 * @param ball
	 * @throws UnknownHostException
	 * 
	 * @pre The ball passed in should not be null
	 * @post The ball will be kicked in the direction of the goal
	 * 
	 */
	public void kickToGoal(ObjBall ball) throws UnknownHostException {
		ObjGoal goal = player.getMemory().getOppGoal();
		if (goal != null) {
			player.getRoboClient().kick(100, goal.getDirection() - player.getMemory().getDirection());
			System.out.println("I see the goal");
		} else {
			player.getRoboClient().kick(100, player.getMemory().getDirection());
			System.out.println("Null Goal");
		}
	}

	/**
	 * Kicks ball to a certain Polar point
	 * 
	 * @param ball
	 * @param p
	 *            The Polar coordinate to kick the ball to
	 * 
	 * @pre The ball passed in should not be null and p should be within the
	 *      field from the player
	 * @post The ball will be kicked to the vicinity of the point
	 * 
	 */
	public void kickToPoint(ObjBall ball, Polar p) {
		if (ball.getDistance() <= 0.7) {
			try {
				player.getRoboClient().kick(
						MathHelp.getKickPower(
								p, 
								player.getMemory().getAmountOfSpeed(), 
								player.getMemory().getDirection(),
								ball.getDistance(), 
								ball.getDirection()), 
						p.t);
			} catch (UnknownHostException e) {
				System.out.println("Error in Action.kickToPoint");
				e.printStackTrace();
			}
		}
	}

	/**
	 * A Pos wrapper for the kickToPoint
	 * 
	 * @param ball
	 * @param p
	 *            the Pos of the coordinate to kick the ball to
	 */
	public void kickToPoint(ObjBall ball, Position p) {
		Position pt = MathHelp.vSub(p, player.getMemory().getPosition());
		Polar go = player.getMemory().getAbsPolar(pt);
		kickToPoint(ball, go);
	}

	/**
	 * This dribbles the ball in the direction of the goal until it's 18 feet
	 * outside of the goal, when it kicks the ball with maximum power into the
	 * goal.
	 * 
	 * @param ball
	 * @throws UnknownHostException
	 * 
	 * @pre The ball should not be null
	 * @post This will result in a dribble and a shoot
	 */
	public void dribbleToGoal(ObjBall ball) throws UnknownHostException {
		if (stayInBounds()) {
			ObjGoal goal = player.getMemory().getOppGoal();

			if ((goal != null) && (player.getMemory().getPosition().x < 35.0)) {
				kickToPoint(ball, new Polar(5.0, (goal.getDirection() - ball.getDirection())));

			} else if ((goal != null) && (player.getMemory().getPosition().x >= 35.0)) {
				System.out.println("Ready to shoot");
				kickToGoal(ball);
			} else if (goal == null) {
				player.getRoboClient().kick(5.0, player.getMemory().getNullGoalAngle());
			}
		}
	}

	/**
	 * Teleports the Player to the specified coordinates.
	 * 
	 * @param x
	 *            x-coordinate of the point to move the player to.
	 * @param y
	 *            y-coordinate of the point to move the player to.
	 * @throws InterruptedException
	 * @pre Playmode is before-kickoff, goal-scored, free-kick.
	 * @post The Player has been moved to the correct position.
	 */
	public void move(double x, double y) throws UnknownHostException, InterruptedException {
		player.getRoboClient().move(x, y);
	}

	/**
	 * Causes Player to kick the ball.
	 * 
	 * @param dir
	 *            The direction in which to kick the ball in the form of a
	 *            decimal value. representing the angle in degrees in relation
	 *            go the player.
	 * @param power
	 *            The power with which to kick the ball in the form of a decimal
	 *            value.
	 * @throws InterruptedException
	 * @pre Playmode is play_on, ball is in kickable range.
	 * @post The ball has been kicked in the specified direction and power.
	 */
	public void kick(double power, double dir) throws UnknownHostException, InterruptedException {
		player.getRoboClient().kick(power, dir);
	}

	/**
	 * Causes Player to dash.
	 * 
	 * @param power
	 *            The power with which to dash in the form of a decimal value.
	 * @throws Exception
	 * @pre Play mode is play_on.
	 * @post The player has dashed at the given power.
	 */
	public void dash(double power) throws Exception {
		player.getRoboClient().dash(power);
	}

	/**
	 * Causes Player to dash.
	 * 
	 * @param power
	 *            The power with which to dash in the form of a decimal value.
	 * @param direction:
	 *            The direction to dash in.
	 * @throws Exception
	 * @pre Play mode is play_on.
	 * @post The player has dashed at the given power.
	 */
	public void dash(double power, double direction) throws Exception {
		player.getRoboClient().dash(power, direction);
	}

	/**
	 * Causes Player to turn according to a specified turn moment.
	 * 
	 * @param moment
	 *            The turn angle in degrees.
	 * @throws InterruptedException
	 * @pre Playmode is play_on, ball is in kickable range.
	 * @post The ball has been kicked in the specified direction and power.
	 */
	public void turn(double moment) throws UnknownHostException, InterruptedException {
		player.getRoboClient().turn(moment);
	}

	public void turn_neck(double moment) throws UnknownHostException, InterruptedException {
		player.getRoboClient().turn_neck(moment);
	}

	/**
	 * Instructs the player to prepare to receive a pass from another teammate.
	 * 
	 * @param ball
	 *            The ball in play.
	 * @param p
	 *            The player to receive the ball from.
	 * @pre Playmode is play_on, ball is being passed to player.
	 * @post The player has possession of the ball.
	 */
	public void receivePass(ObjBall ball, ObjPlayer p) throws UnknownHostException, InterruptedException {
		// Turn toward direction ball is coming from
		turn(p.getDirection());
		Thread.sleep(100);

		// Halt ball to allow for dribbling
		if (ball.getDistance() < 0.5) {
			kick(5, 0);
		}
	}

	/**
	 * Causes Player to say the given message. It has a limitation of 512
	 * characters by default.
	 * 
	 * @param message
	 *            The string to be spoken by the player.
	 * @throws InterruptedException
	 * @pre None
	 * @post The player has spoken the message.
	 */
	public void say(String message) throws UnknownHostException, InterruptedException {
		player.getRoboClient().say(message);
	}

	/**
	 * Marks opposing players for defense
	 */
	public void markOpponent(String team, String number) {
		// get.setMarked_team(team);
		// b.setMarked_unum(number);
	}

	public boolean inFullBackZone() {
		if (player.getMemory().getPosition().x < -10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Causes the Goalie to catch the ball.
	 * 
	 * @pre Playmode is play-on, ball is within goalkeeper zone and in the
	 *      catchable area.
	 * @post The Goalie has caught the ball.
	 */
	public void catchball(double d) throws UnknownHostException {
		player.getRoboClient().catchball(d);
		ballCaught = true;
	}

	/**
	 * Turns goalie toward the ball
	 * 
	 * @throws Exception
	 * @post The goalie will turn in the direction of the ball
	 */
	public void followBall() throws Exception {
		try {
			if (!player.getMemory().isObjVisible("ball")) {
				turn(45);
				return;
			}
			if (player.getMemory().isObjVisible("ball")) {
				ObjBall ball = player.getMemory().getBall();

				if ((ball.getDirection() > 5.0) || (ball.getDirection() < -5.0)) {
					turn(ball.getDirection() * (1 + (5 * player.getMemory().getAmountOfSpeed())));
				}
				if (ballInGoalzone(ball)) {
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
	 * Returns true or false depending on whether the ball is within the
	 * catchable range of the goalie.
	 * 
	 * @pre The ball is visible to the goalie
	 * @post The ball is determined to catchable or not.
	 * @return boolean True if catchable, false if not.
	 */
	public boolean catchable() {
		boolean catchable = false;

		// Test for visibility
		if (player.getMemory().isObjVisible("ball") && ballInGoalzone(player.getMemory().getBall())) {
			// Test for moment range
			if (player.getMemory().getBall().getDirection() > -180 && player.getMemory().getBall().getDirection() < 180) {
				// Test for catchable distance
				if (player.getMemory().getBall().getDistance() < 2.0) {
					catchable = true;
				}
			}
		}
		return catchable;
	}

	/**
	 * A method to determine whether the ball is in the penalty box
	 * 
	 * @param ball
	 *            the ObjBall to follow
	 * @pre this must be called with an ObjBall
	 * @post true if ball is in penalty box, false if it's not
	 * @return boolean
	 */
	public boolean ballInGoalzone(ObjBall ball) {
		if (ball == null)
			return false;

		Position ballPos = MathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
		ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

		if (((ballPos.x <= -36) && (ballPos.x >= -52.5)) && ((-20.16 <= ballPos.y) && (ballPos.y <= 20.16)))
			return true;
		else
			return false;
	}

	/**
	 * Causes the goalie to act to intercept the ball as it approaches the goal.
	 * 
	 * @param ObjBall
	 *            representing the ball in play.
	 * @throws Exception
	 * @pre The ball has entered the goal zone.
	 * @post The ball has been caught by the goalie, or the goalie has missed
	 *       the ball.
	 */
	public void defendGoal(ObjBall ball) throws Exception {
		Position ridBallPoint = new Position(0, 0);

		// Move to catchable range of ball
		if (ball.getDistance() > 1.0) {
			gotoPoint(MathHelp.getNextBallPoint(ball));
		} else {
			if ((player.getMemory().getSide().compareTo("l") == 0)
					&& ((player.getMemory().getPlayMode().compareTo("goalie catch ball_l") == 0)
							|| (player.getMemory().getPlayMode().compareTo("free_kick_l") == 0))) {
				Thread.sleep(500);
				turn(-player.getMemory().getDirection());
				Thread.sleep(200);
				kick(100, 0);
				Thread.sleep(100);
			} else if ((player.getMemory().getSide().compareTo("r") == 0)
					&& ((player.getMemory().getPlayMode().compareTo("goalie catch ball_r") == 0)
							|| (player.getMemory().getPlayMode().compareTo("free_kick_r") == 0))) {
				Thread.sleep(500);
				turn(-player.getMemory().getDirection());
				Thread.sleep(200);
				kick(100, 0);
				Thread.sleep(100);
			} else {
				catchball(player.getMemory().getBall().getDirection());
			}

			// If ball is in catchable area, catch it
			System.out.println("catchable");
			if (!ballCaught) {
				catchball(player.getMemory().getBall().getDirection());
				Thread.sleep(100);
				ballCaught = true;
			}
			// kickToPlayer(closestPlayer());
			kickToPoint(ball, ridBallPoint);
			Thread.sleep(100);
		}
	} // end method

	/**
	 * Moves goalie to specific points within the goalbox dependent upon where
	 * the ball is on the field.
	 * 
	 * @param ball
	 *            An ObjBall representing the ball in play.
	 * @throws Exception
	 * @pre The ball is visible.
	 * @post The goalie has moved to a strategic position to get between the
	 *       ball and the goal.
	 */
	public void positionGoalie(ObjBall ball) throws Exception {
		Position ballPos = MathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
		ballPos = MathHelp.vAdd(player.getPosition(), ballPos);
		Position upper = new Position(-49, -6);
		Position middle = new Position(-49, 0);
		Position lower = new Position(-49, 6);

		if (!ballInGoalzone(ball)) {
			if (ballPos.y < -18) { // If ball is in upper portion of field
				// System.out.println("flag1");
				gotoSidePoint(upper);
				Thread.sleep(100);
			} else if (ballPos.y > -18 && ballPos.y < 18) { // If ball is
				// midfield
				// vertically
				// System.out.println("flag2");
				gotoSidePoint(middle);
				Thread.sleep(100);
			} else { // If ball is in lower portion of field
				// System.out.println("flag3");
				gotoSidePoint(lower);
				Thread.sleep(100);
			}
		}
	}

	/**
	 * Moves goalie between the ball and the goal (under construction)
	 * 
	 * @param ball
	 *            An ObjBall.
	 * @throws Exception
	 * @pre Ball is visible to the goalie.
	 * @post The goalie has moved to a point on the line between the ball and
	 *       the goal.
	 */
	public void getBtwBallAndGoal(ObjBall ball) throws Exception {
		Position ballPos = MathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
		ballPos = MathHelp.vAdd(player.getPosition(), ballPos);
		Position goalPos = player.getMemory().getOwnGoalPos();
		Position newPos = new Position();
		boolean between = false;

		double slope = (goalPos.y - ballPos.y) / (goalPos.x - ballPos.x);
		double x_p = 0.66 * (goalPos.x - ballPos.x) + ballPos.x;
		double y_int = ballPos.y - ballPos.x * slope;
		double y_p = slope * x_p + y_int;
		newPos.x = x_p;
		newPos.y = y_p;

		if (ball.getDirChng() > 20 & !between) {
			gotoPoint(newPos);
			between = true;
		}
	}

	/**
	 * Causes goalie to kick the ball to a specific player. (Currently unused.)
	 * 
	 * @pre A player is in sight of the goalie.
	 * @post The goalie has kicked the ball to the player passed to the
	 *       function.
	 * @param player
	 *            An ObjPlayer representing the player to receive the ball.
	 */
	public void kickToPlayer(ObjPlayer objPlayer) {
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			kickToPoint(ball, MathHelp.getPos(new Polar(objPlayer.getDistance(), objPlayer.getDirection())));
			ballCaught = false;
		}
	}

	/**
	 * Causes the goalie to kick the ball out of bounds (Currently unused.)
	 * 
	 * @pre Goalie has control of the ball
	 * @post Ball has been kicked out of bounds
	 */
	public void kickBallOutOfBounds() {
		try {
			// Locate closest flag on a boundary line
			ObjFlag kickFlag = new ObjFlag();
			kickFlag = player.getMemory().getClosestBoundary();
			// System.out.println("Flag name: " + kickFlag.getFlagName());

			// Test to ensure the flag is within a kickable range, and
			// is not dangerously close to the goal, and kick it if allowable
			if (kickFlag.getDistance() < 25 && kickFlag.getFlagName() != "flt10" && kickFlag.getFlagName() != "fl0"
					&& kickFlag.getFlagName() != "flb10" && kickFlag.getFlagName() != "frt10"
					&& kickFlag.getFlagName() != "fr0" && kickFlag.getFlagName() != "frb10") {
				kick(90, kickFlag.getDirection());
				// ballCaught = false;
				Thread.sleep(100);
			} else { // Turn to a new position and check flag again
				turn(-30);
				Thread.sleep(100);

				// Kick if the boundary flag is now reachable
				kickFlag = player.getMemory().getClosestBoundary();
				if (kickFlag.getDistance() < 25 && kickFlag.getFlagName() != "flt10" && kickFlag.getFlagName() != "fl0"
						&& kickFlag.getFlagName() != "flb10" && kickFlag.getFlagName() != "frt10"
						&& kickFlag.getFlagName() != "fr0" && kickFlag.getFlagName() != "frb10") {
					kick(90, kickFlag.getDirection());
					ballCaught = false;
					Thread.sleep(100);
				}
			}
		} catch (UnknownHostException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void smartKick(String flagLeft, String flagRight) throws UnknownHostException, InterruptedException {
		double leftFlagDirection = player.getMemory().getFlag(flagLeft).getDirection();
		double rightFlagDirection = player.getMemory().getFlag(flagRight).getDirection();

		List<ObjPlayer> otherPlayers = player.getMemory().getOpponents(player.getRoboClient().getTeam());
		double playerSize = 0.7;

		GoalView goalView = KickMathUtility.getGoalView(leftFlagDirection, rightFlagDirection, otherPlayers,
				playerSize);

		ViewInterval largerInterval = goalView.getLargerInterval();
		if (largerInterval.getStart().getBoundType() == BoundType.POST) {
			kick(9001, largerInterval.getStart().getAngle() + 5);
		} else if (largerInterval.getEnd().getBoundType() == BoundType.POST) {
			kick(9001, largerInterval.getEnd().getAngle() - 5);
		} else {
			kick(9001, largerInterval.getMidAngle());
		}
	}

	/**
	 * Returns the closest opponent to the player
	 * 
	 * @pre Players are in sight of the goalie.
	 * @post The closest opponent to the player has been determined.
	 * @return ObjPlayer
	 * @throws InterruptedException
	 * @throws UnknownHostException
	 */
	public ObjPlayer closestOpponent() throws UnknownHostException, InterruptedException {
		ObjPlayer closestOpponent = new ObjPlayer();
		double distance = 0;

		// Loop through arraylist of ObjPlayers
		for (int i = 0; i < player.getMemory().getPlayers().size(); ++i) {

			if (player.getMemory().getPlayers().isEmpty()) { // No other players in
				// player's sight, so
				// turn to another point
				// to check again
				turn(15);
			}

			if (!player.getMemory().getPlayers().isEmpty()) {
				if (distance == 0 && player.getMemory().getPlayers().get(i).getTeam() != player.getRoboClient().getTeam()) {
					distance = player.getMemory().getPlayers().get(i).getDistance();
					closestOpponent = player.getMemory().getPlayers().get(i);
				} else {
					// Test if this player is closer than the previous one
					if (distance > player.getMemory().getPlayers().get(i).getDistance()
							&& player.getMemory().getPlayers().get(i).getTeam() != player.getRoboClient().getTeam()) {
						distance = player.getMemory().getPlayers().get(i).getDistance();
						closestOpponent = player.getMemory().getPlayers().get(i);
					}
				}
			}
		}

		return closestOpponent;
	}

	public ObjPlayer closestTeammate() throws UnknownHostException, InterruptedException {

		if(player.getMemory().getPlayers() == null)
			return null;

		ObjPlayer closestPlayer = null;
		double distance = 100;
		String playerTeam = "\""+player.getRoboClient().getTeam()+"\"";

		//		System.out.println("Teammates "+player.getMemory().getPlayers().size());

		for (int i = 0; i < player.getMemory().getPlayers().size(); ++i) {
			ObjPlayer currentPlayer = player.getMemory().getPlayers().get(i);
			if (currentPlayer.getuNum() != 0 && currentPlayer.getTeam() != null ) {
				if (currentPlayer.getTeam().equals(playerTeam) ) {
					if (currentPlayer.getDistance() < distance ) {
						closestPlayer = currentPlayer;
						distance = currentPlayer.getDistance();
					}
				}
			}
		}		

		return closestPlayer;
	}

	public Boolean isBallInOurField() {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall()!=null) {
			ObjBall objBall = player.getMemory().getBall();
			Position ballPos = MathHelp.getPos(objBall.getDistance(), player.getDirection() + objBall.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

			if(player.getMemory().getSide().equals("l")){
				return ballPos.x <= 0;
			} 

			return ballPos.x > 0;
		}

		return false;
	}

	public Boolean isBallInRangeOf(double range){
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall()!=null) {
			ObjBall objBall = player.getMemory().getBall();
			return objBall.getDistance() <= range;
		}
		return false;
	}

	public Boolean isBallNearTeammateAttacker() {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall()!=null) {
			ObjBall objBall = player.getMemory().getBall();
			List<ObjPlayer> teammates = player.getMemory().getTeammates(player.getRoboClient().getTeam());

			Position ballPos = MathHelp.getPos(objBall.getDistance(), player.getDirection() + objBall.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

			AbstractFormation formation = FormationManager.getFormation(player.getFormationName());

			for(ObjPlayer objPlayer : teammates){
				int playerNumber = objPlayer.getuNum()+1;
				
				if(playerNumber>0 
						&& playerNumber<=11 
						&& formation.getPlayerClass(playerNumber).equals(OffensivePlayer.class.getSimpleName())){
					Position objPlayerPosition = MathHelp.getPos(objPlayer.getDistance(), player.getDirection() + objPlayer.getDirection());
					objPlayerPosition = MathHelp.vAdd(player.getPosition(), objPlayerPosition);

					Position vSub = MathHelp.vSub(ballPos, objPlayerPosition);

					if(MathHelp.mag(vSub)<=10.0){
						return true;
					}
				}
			}
		}

		return false;
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
