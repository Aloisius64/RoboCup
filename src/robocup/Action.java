package robocup;

import java.net.UnknownHostException;
import java.util.List;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationManager;
import robocup.objInfo.ObjBall;
import robocup.objInfo.ObjFlag;
import robocup.objInfo.ObjGoal;
import robocup.objInfo.ObjInfo;
import robocup.objInfo.ObjPlayer;
import robocup.player.AbstractPlayer;
import robocup.player.OffensivePlayer;
import robocup.utility.MathHelp;
import robocup.utility.Polar;
import robocup.utility.Position;
import robocup.utility.kick.BoundType;
import robocup.utility.kick.GoalView;
import robocup.utility.kick.KickMathUtility;
import robocup.utility.kick.ViewInterval;

public class Action {

	private final AbstractPlayer player;

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

	public void goToPoint(Polar go) throws Exception {
		if ((go.t > 5.0) || (go.t < -5.0)) {
			player.getRoboClient().turn(go.t * (1 + (5 * player.getMemory().getAmountOfSpeed())));
		}
		player.getRoboClient().dash(MathHelp.getDashPower(MathHelp.getPos(go), player.getMemory().getAmountOfSpeed(),
				player.getMemory().getDirection(), player.getMemory().getEffort(), player.getMemory().getStamina()));
	}

	public void goToSidePoint(Position p) throws Exception {
		Polar go = player.getMemory().getAbsPolar(p);
		if (go.r >= 0.5) {
			player.getRoboClient()
					.dash((MathHelp.getDashPower(MathHelp.getPos(go), player.getMemory().getAmountOfSpeed(),
							player.getMemory().getDirection(), player.getMemory().getEffort(),
							player.getMemory().getStamina())), go.t);
		}
	}

	public void goToPoint(Position p) throws Exception {
		Polar go = player.getMemory().getAbsPolar(p);
		if (go.r >= 0.5) {
			if ((go.t) > 5.0 || (go.t) < -5.0) {
				player.getRoboClient().turn(go.t * (1 + (5 * player.getMemory().getAmountOfSpeed())));
			}

			player.getRoboClient()
					.dash(MathHelp.getDashPower(MathHelp.getPos(go), player.getMemory().getAmountOfSpeed(),
							player.getMemory().getDirection(), player.getMemory().getEffort(),
							player.getMemory().getStamina()));
		}
	}

	public void goHome() throws Exception {
		if (!player.getMemory().isHome()) {
			goToPoint(player.getMemory().getHome());
		}
	}

	public boolean isHome() {
		return player.getMemory().isHome();
	}

	public void smartKick(ObjFlag flagLeft, ObjFlag flagRight) throws UnknownHostException, InterruptedException {
		// se right null set to 45° se left null set to -45°

		double leftFlagDirection = flagLeft.getDirection();
		double rightFlagDirection = flagRight.getDirection();

		List<ObjPlayer> otherPlayers = player.getMemory().getOpponents(player.getRoboClient().getTeam());
		double playerSize = 0.7;

		GoalView goalView = KickMathUtility.getGoalView(leftFlagDirection, rightFlagDirection, otherPlayers,
				playerSize);

		ViewInterval largerInterval = goalView.getLargerInterval();
//		System.out.println(
//				"intervallo tra" + largerInterval.getStart().getAngle() + " - " + largerInterval.getStart().getAngle());

		if (largerInterval.getStart().getBoundType() == BoundType.POST) {
			kick(100, largerInterval.getStart().getAngle() + 5);
		} else if (largerInterval.getEnd().getBoundType() == BoundType.POST) {
			kick(100, largerInterval.getEnd().getAngle() - 5);
		} else {
			kick(100, largerInterval.getMidAngle());
		}
	}

	public boolean isTeammatesNear() {
		// TODO Auto-generated method stub
		// player.getMemory().getHearMemory().ourMessagePrint();
		for (Double d : player.getMemory().getHearMemory().getBallDistances().values()) {
			if (d < player.getMemory().getBall().getDistance()) {
				return true;
			}
		}
		return false;
	}

	public boolean inFieldBall(ObjBall ball) {

		Polar p = MathHelp.getNextBallPoint(ball);
		Position p2 = MathHelp.getPos(p);
		return !((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36));
	}

	public boolean inFieldPlayer() {
		// Polar p =
		// MathHelp.getNextPlayerPoint(player.getMemory().getPlayer());
		Position p2 = player.getPosition();
		return !((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36));
	}

	public boolean isBallVisible() {
		if (player.getMemory().isObjVisible("ball")) {
			return player.getMemory().getBall() != null;
		}
		return false;
	}

	public void forwardToGoal() throws Exception {
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			if (ball.getDistance() <= 0.7) {// ball in kickable margin
				ObjGoal goal = player.getMemory().getOppGoal();
				if (goal != null) {// vediamo la porta?
					if (goal.getDistance() <= 17) {// dentro l'area
						// in caso di opponent vicini tira senza girarti a
						// cercare i pali
						if (player.getMemory().getLeftPost() == null) {
//							System.out.println(goal.getDirection());
							turn(30);
							// Thread.sleep(50);
						} else if (player.getMemory().getRightPost() == null) {
							turn(-30);
						}
						Thread.sleep(100);
						ObjFlag flagLeft = player.getMemory().getLeftPost();
						ObjFlag flagRight = player.getMemory().getRightPost();
						smartKick(flagLeft, flagRight);
					} else {
						kick(5, goal.getDirection());
					}
				} else {// goal null
					if (ball.getDistance() < 0.7) {// gira la palla
						// muovere la palla verso la porta
						kick(5, -player.getDirection());

					} else {// mi avvicino alla palla
						turn(ball.getDirection());
						Thread.sleep(50);
						dash(100);
					}
				}
			} else {// ball distante da me
				turn(ball.getDirection());
				Thread.sleep(50);
				dash(50);

			}
		} else {
			player.getRoboClient().turn(30.0);
		}

	}

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
				goToSidePoint(new Position(player.getMemory().getBallPos(ball).x, player.getMemory().getHome().y));
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

	public boolean interceptBall(ObjBall ball) throws Exception {
		Polar p = MathHelp.getNextBallPoint(ball);
		Position p2 = MathHelp.getPos(p);
		if ((Math.abs(p2.x) >= 52.5) || (Math.abs(p2.y) >= 36))
			return false;
		else if (stayInBounds()) {
			goToPoint(p);
		}
		return true;
	}

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

	public void kickToGoal(ObjBall ball) throws UnknownHostException {
		ObjGoal goal = player.getMemory().getOppGoal();
		if (goal != null) {
			player.getRoboClient().kick(100, goal.getDirection() - player.getMemory().getDirection());
//			System.out.println("I see the goal");
		} else {
			player.getRoboClient().kick(100, player.getMemory().getDirection());
//			System.out.println("Null Goal");
		}
	}

	public void kickToPoint(ObjBall ball, Polar p) {
		if (ball.getDistance() <= 0.7) {
			try {
				player.getRoboClient().kick(MathHelp.getKickPower(p, player.getMemory().getAmountOfSpeed(),
						player.getMemory().getDirection(), ball.getDistance(), ball.getDirection()), p.t);
			} catch (UnknownHostException e) {
				System.out.println("Error in Action.kickToPoint");
				e.printStackTrace();
			}
		}
	}

	public void kickToPoint(ObjBall ball, Position p) {
		Position pt = MathHelp.vSub(p, player.getMemory().getPosition());
		Polar go = player.getMemory().getAbsPolar(pt);
		kickToPoint(ball, go);
	}

	public void dribbleToGoal(ObjBall ball) throws UnknownHostException, InterruptedException {
		if (stayInBounds()) {
			ObjGoal goal = player.getMemory().getOppGoal();

			if ((goal != null) && (player.getMemory().getPosition().x < 35.0)) {
				kickToPoint(ball, new Polar(5.0, (goal.getDirection() - ball.getDirection())));

			} else if ((goal != null) && (player.getMemory().getPosition().x >= 35.0)) {
//				System.out.println("Ready to shoot");
				kickToGoal(ball);

			} else if (goal == null) {
				player.getRoboClient().kick(5.0, player.getMemory().getNullGoalAngle());
			}
		}
	}

	public void move(double x, double y) throws UnknownHostException, InterruptedException {
		player.getRoboClient().move(x, y);
	}

	public void kick(double power, double dir) throws UnknownHostException, InterruptedException {
		player.getRoboClient().kick(power, dir);
	}

	public void dash(double power) throws Exception {
		player.getRoboClient().dash(power);
	}

	public void dash(double power, double direction) throws Exception {
		player.getRoboClient().dash(power, direction);
	}

	public void turn(double moment) throws UnknownHostException, InterruptedException {
		player.getRoboClient().turn(moment);
	}

	public void turn_neck(double moment) throws UnknownHostException, InterruptedException {
		player.getRoboClient().turn_neck(moment);
	}

	public void receivePass(ObjBall ball, ObjPlayer p) throws UnknownHostException, InterruptedException {
		// Turn toward direction ball is coming from
		turn(p.getDirection());
		Thread.sleep(100);

		// Halt ball to allow for dribbling
		if (ball.getDistance() < 0.5) {
			kick(5, 0);
		}
	}

	public void say(String message) throws UnknownHostException, InterruptedException {
		player.getRoboClient().say(message);
	}

	public void catchball(double d) throws UnknownHostException {
		player.getRoboClient().catchball(d);
	}

	public boolean catchable() {
		if (player.getMemory().isObjVisible("ball") && ballInGoalzone(player.getMemory().getBall())) {
			// Test for moment range
			if (player.getMemory().getBall().getDirection() > -180
					&& player.getMemory().getBall().getDirection() < 180) {
				// Test for catchable distance
				if (player.getMemory().getBall().getDistance() < 2.0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean ballInGoalzone(ObjBall ball) {
		if (ball != null) {
			Position ballPos = MathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

			boolean yCheck = (-20.16 <= ballPos.y) && (ballPos.y <= 20.16);
			boolean xCheckLeft = (ballPos.x <= -36) && (ballPos.x >= -52.5);
			boolean xCheckRight = (ballPos.x >= 36) && (ballPos.x <= 52.5);

			return yCheck && (xCheckLeft || xCheckRight);
		}

		return false;
	}

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
			goToPoint(newPos);
			between = true;
		}
	}

	public void kickToPlayer(ObjPlayer objPlayer) {
		if (player.getMemory().isObjVisible("ball")) {
			ObjBall ball = player.getMemory().getBall();
			kickToPoint(ball, MathHelp.getPos(new Polar(objPlayer.getDistance(), objPlayer.getDirection())));
		}
	}

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

	public ObjPlayer closestOpponent() throws UnknownHostException, InterruptedException {
		ObjPlayer closestPlayer = null;
		double distance = 100;
		String playerTeam = "\"" + player.getRoboClient().getTeam() + "\"";

		for (int i = 0; i < player.getMemory().getPlayers().size(); ++i) {
			ObjPlayer currentPlayer = player.getMemory().getPlayers().get(i);
			if (currentPlayer.getuNum() != 0 && currentPlayer.getTeam() != null) {
				if (!currentPlayer.getTeam().equals(playerTeam)) {
					if (currentPlayer.getDistance() < distance) {
						closestPlayer = currentPlayer;
						distance = currentPlayer.getDistance();
					}
				}
			}
		}

		return closestPlayer;
	}

	public ObjPlayer closestTeammate() throws UnknownHostException, InterruptedException {
		if (player.getMemory().getPlayers() == null)
			return null;

		ObjPlayer closestPlayer = null;
		double distance = 100;
		String playerTeam = "\"" + player.getRoboClient().getTeam() + "\"";

//		System.out.println("Teammates " + player.getMemory().getPlayers().size());

		for (int i = 0; i < player.getMemory().getPlayers().size(); ++i) {
			ObjPlayer currentPlayer = player.getMemory().getPlayers().get(i);
			if (currentPlayer.getuNum() != 0 && currentPlayer.getTeam() != null) {
				if (currentPlayer.getTeam().equals(playerTeam)) {
					if (currentPlayer.getDistance() < distance) {
						closestPlayer = currentPlayer;
						distance = currentPlayer.getDistance();
					}
				}
			}
		}

		return closestPlayer;
	}

	public Boolean isBallInOurField() {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall() != null) {
			ObjBall objBall = player.getMemory().getBall();
			Position ballPos = MathHelp.getPos(objBall.getDistance(), player.getDirection() + objBall.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

			// if (player.getMemory().getSide().equals("l")) {
			// return ballPos.x <= 0;
			// }
			//
			// return ballPos.x > 0;
			return ballPos.x <= 0;
		}

		return false;
	}

	public Boolean isBallInRangeOf(double range) {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall() != null) {
			ObjBall objBall = player.getMemory().getBall();

			return objBall.getDistance() <= range;
		}

		return false;
	}

	public boolean isBallNearTeammateAttacker() {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall() != null) {
			ObjBall objBall = player.getMemory().getBall();
			Position ballPos = MathHelp.getPos(objBall.getDistance(), player.getDirection() + objBall.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);

			AbstractFormation formation = FormationManager.getFormation(player.getFormationName());

			for (ObjPlayer objPlayer : player.getMemory().getTeammates(player.getRoboClient().getTeam())) {
				int playerNumber = objPlayer.getuNum() + 1;

				if (playerNumber > 0 && playerNumber <= 11
						&& formation.getPlayerClass(playerNumber).equals(OffensivePlayer.class.getSimpleName())) {
					Position objPos = MathHelp.getPos(objPlayer.getDistance(),
							player.getDirection() + objPlayer.getDirection());
					objPos = MathHelp.vAdd(player.getPosition(), objPos);

					if (MathHelp.mag(objPos) < 4.0) {
						System.out.println("FOUND PLAYER " + playerNumber);
						return true;
					}
				}
			}
		}

		return false;
	}

	public Boolean isBallNearTeammate() {
		if (player.getMemory().isObjVisible("ball") && player.getMemory().getBall() != null) {
			ObjBall objBall = player.getMemory().getBall();
			Position ballPos = MathHelp.getPos(objBall.getDistance(), player.getDirection() + objBall.getDirection());
			ballPos = MathHelp.vAdd(player.getPosition(), ballPos);
			if (isTeammatesNear()) {
				return true;
			}
			// for (ObjPlayer objPlayer :
			// player.getMemory().getTeammates(player.getRoboClient().getTeam()))
			// {
			// int playerNumber = objPlayer.getuNum() + 1;
			//
			// if (playerNumber > 0 && playerNumber <= 11) {
			// Position objPos = MathHelp.getPos(objPlayer.getDistance(),
			// player.getDirection() + objPlayer.getDirection());
			// objPos = MathHelp.vAdd(player.getPosition(), objPos);
			//
			// if (MathHelp.mag(objPos) < 4.0) {
			// System.out.println("Found teammate " + playerNumber);
			// return true;
			// }
			// }
			// }
		}

		return false;
	}

	public Boolean isBehindBall() {
		if (player.getMemory().getBall() == null)
			return false;
		double x = MathHelp.getPos(
				new Polar(player.getMemory().getBall().getDistance(), player.getMemory().getBall().getDirection())).x;

		return player.getPosition().x < x;
	}

	
	public boolean isPlayMode(String playMode) {
		return player.getMemory().getPlayMode().equals(playMode);
	}

	/**************************************************************/
	/* THIS CODE CAN BE REMOVED *******************************/
	/**************************************************************/

	// public boolean passBall(ObjBall ball, ObjPlayer p) throws
	// UnknownHostException {
	// if (p != null) {
	// // player.getRoboClient().say("pass" + p.getuNum());
	// kickToPoint(ball, MathHelp.getNextPlayerPoint(p));
	// return true;
	// }
	// return false;
	// }

	// public void FullBack_findBall() throws Exception {
	// if (player.getMemory().isObjVisible("ball")) {
	// ObjBall ball = player.getMemory().getBall();
	// if ((ball.getDirection() > 5.0 || ball.getDirection() < -5.0)) {
	// player.getRoboClient().turn(ball.getDirection());
	// Thread.sleep(100);
	// }
	//
	// if ((ball.getDistance() > 15) && (player.getMemory().isHome() == false))
	// {
	// goHome();
	// } else if ((ball.getDistance() <= 15.0) && (ball.getDistance() > 0.7)) {
	// interceptBall(ball);
	// } else if (ball.getDistance() <= 0.7) {
	// // kickToPoint(ball, new Pos(0,0));
	// passBall(ball, closestTeammate());
	// }
	// } else
	// player.getRoboClient().turn(30);
	// }

	// public void defendGoal(ObjBall ball) throws Exception {
	// Position ridBallPoint = new Position(0, 0);
	//
	// // Move to catchable range of ball
	// if (ball.getDistance() > 1.0) {
	// gotoPoint(MathHelp.getNextBallPoint(ball));
	// } else {
	// if ((player.getMemory().getSide().compareTo("l") == 0)
	// && ((player.getMemory().getPlayMode().compareTo("goalie catch ball_l") ==
	// 0)
	// || (player.getMemory().getPlayMode().compareTo("free_kick_l") == 0))) {
	// Thread.sleep(500);
	// turn(-player.getMemory().getDirection());
	// Thread.sleep(200);
	// kick(100, 0);
	// Thread.sleep(100);
	// } else if ((player.getMemory().getSide().compareTo("r") == 0)
	// && ((player.getMemory().getPlayMode().compareTo("goalie catch ball_r") ==
	// 0)
	// || (player.getMemory().getPlayMode().compareTo("free_kick_r") == 0))) {
	// Thread.sleep(500);
	// turn(-player.getMemory().getDirection());
	// Thread.sleep(200);
	// kick(100, 0);
	// Thread.sleep(100);
	// } else {
	// catchball(player.getMemory().getBall().getDirection());
	// }
	//
	// // If ball is in catchable area, catch it
	// System.out.println("catchable");
	// if (!ballCaught) {
	// catchball(player.getMemory().getBall().getDirection());
	// Thread.sleep(100);
	// ballCaught = true;
	// }
	// // kickToPlayer(closestPlayer());
	// kickToPoint(ball, ridBallPoint);
	// Thread.sleep(100);
	// }
	// }

	// public void positionGoalie(ObjBall ball) throws Exception {
	// Position ballPos = MathHelp.getPos(ball.getDistance(),
	// player.getDirection() + ball.getDirection());
	// ballPos = MathHelp.vAdd(player.getPosition(), ballPos);
	// Position upper = new Position(-49, -6);
	// Position middle = new Position(-49, 0);
	// Position lower = new Position(-49, 6);
	//
	// if (!ballInGoalzone(ball)) {
	// if (ballPos.y < -18) { // If ball is in upper portion of field
	// // System.out.println("flag1");
	// gotoSidePoint(upper);
	// Thread.sleep(100);
	// } else if (ballPos.y > -18 && ballPos.y < 18) { // If ball is
	// // midfield
	// // vertically
	// // System.out.println("flag2");
	// gotoSidePoint(middle);
	// Thread.sleep(100);
	// } else { // If ball is in lower portion of field
	// // System.out.println("flag3");
	// gotoSidePoint(lower);
	// Thread.sleep(100);
	// }
	// }
	// }

	// public void followBall() throws Exception {
	// try {
	// if (!player.getMemory().isObjVisible("ball")) {
	// turn(45);
	// return;
	// }
	// if (player.getMemory().isObjVisible("ball")) {
	// ObjBall ball = player.getMemory().getBall();
	//
	// if ((ball.getDirection() > 5.0) || (ball.getDirection() < -5.0)) {
	// turn(ball.getDirection() * (1 + (5 *
	// player.getMemory().getAmountOfSpeed())));
	// }
	// if (ballInGoalzone(ball)) {
	// defendGoal(ball);
	// } else {
	// positionGoalie(ball);
	// }
	// }
	// } catch (UnknownHostException | InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
}
