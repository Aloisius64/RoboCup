package robocup.ai;

import robocup.Action;
import robocup.memory.Memory;
import robocup.memory.Mode;
import robocup.player.AbstractPlayer;
import robocup.utility.MathHelp;

/**@class Brain
 * The brain serves as a place to store the Player modes, marked players for
 * various functions, and a set of strategies for player actions.
 */
public abstract class AbstractAI extends Thread {

	private Mode currentMode;
	private Action actions;
	private String marked_team;
	private String marked_unum;
	private AbstractPlayer player;
	private Memory memory;
	private MathHelp mathHelp;

	public AbstractAI(AbstractPlayer p) {
		this.currentMode = new Mode();
		this.actions = p.getAction();
		this.player = p;
	}

	/*
	 * Defines Player soccer behaviors.
	 * @pre RoboCup server is active.
	 * @post Player will exhibit soccer behaviors.
	 */
	@Override
	public void run() {
		while (true) {	

			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(player.getMemory().timeCheck(player.getTime())) {
				player.setTime(player.getMemory().getObjMemory().getTime());
				getPlayer().getAgent().update();
			}
		}
	}

	//	@Override
	//	public void run() {
	//
	//		ObjBall ball = player.getMemory().getBall();
	//		Position ballPos = new Position();
	//		Position uppercorner = new Position(53, -35);
	//		Position lowercorner = new Position(53, 35);
	//		Position uppercornerkickpoint = new Position(40, -9);
	//		Position lowercornerkickpoint = new Position(40, 9);
	//		Position cornerkickreceive1 = new Position(32, -10);
	//		Position cornerkickreceive2 = new Position(38, -10);
	//		Position cornerkickreceive3 = new Position(35, -10);
	//		Position cornerkickreceive4 = new Position(35, -5);
	//
	//		while(true) {
	//
	//			try {
	//				Thread.sleep(10);
	//			} catch (InterruptedException e1) {
	//				e1.printStackTrace();
	//			}
	//			
	//			if(player.getMemory().timeCheck(player.getTime())) {
	//				player.setTime(player.getMemory().getObjMemory().getTime());
	//
	//				try {
	//					//Defining playmode behaviors for left side team
	//					if (player.getMemory().getSide().compareTo("l") == 0) {
	//
	//						if((player.getMemory().getPlayMode().compareTo("before_kick_off") == 0) && player.getTime() > 0) {
	//							player.getAction().move(player.getHome().x, player.getHome().y);
	//						}						
	//						else if(player.getMemory().getPlayMode().compareTo("play_on") == 0) {
	//							player.getAction().findBall();
	//						}						
	//						else if(player.getMemory().getPlayMode().compareTo("kick_off_l") == 0) {
	//							if (player.getStringPosition().compareTo("center_fwd") == 0) {
	//								player.getAction().kickOff();
	//							}
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("corner_kick_l") == 0){
	//							while (!player.getMemory().isObjVisible("ball")) {
	//								player.getAction().turn(15);
	//							}
	//
	//							ballPos = mathHelp.getPos(ball.getDistance(), player.getDirection() + ball.getDirection());
	//
	//							if (ballPos.y < 0){
	//								if (player.getStringPosition().compareTo("far_left_fwd") == 0){
	//									player.getAction().gotoPoint(uppercorner);
	//									Polar k = player.getMemory().getAbsPolar(player.getMemory().getFlagPos("fplb"));
	//									player.getAction().getTurn(k);
	//									Thread.sleep(500);
	//									player.getAction().kickToPoint(ball, uppercornerkickpoint);
	//								} else {
	//									if (player.getStringPosition().compareTo("left_fwd") == 0) {
	//										player.getAction().gotoPoint(cornerkickreceive1);
	//									}
	//									if (player.getStringPosition().compareTo("right_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive2);
	//									}
	//									if (player.getStringPosition().compareTo("center_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive3);
	//									}
	//									if (player.getStringPosition().compareTo("far_right_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive4);
	//									}
	//								}
	//							} else {
	//								if (player.getStringPosition().compareTo("far_right_fwd") == 0){
	//									player.getAction().gotoPoint(lowercorner);
	//									Polar k = player.getMemory().getAbsPolar(player.getMemory().getFlagPos("fplb"));
	//									player.getAction().getTurn(k);
	//									Thread.sleep(500);
	//									player.getAction().kickToPoint(ball, lowercornerkickpoint);
	//								} else {
	//									if (player.getStringPosition().compareTo("left_fwd") == 0) {
	//										player.getAction().gotoPoint(cornerkickreceive1);
	//									}
	//									if (player.getStringPosition().compareTo("right_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive2);
	//									}
	//									if (player.getStringPosition().compareTo("center_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive3);
	//									}
	//									if (player.getStringPosition().compareTo("far_left_fwd") == 0){
	//										player.getAction().gotoPoint(cornerkickreceive4);
	//									}
	//								}
	//							}
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("goal_kick_l") == 0){
	//							player.getAction().goHome();
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("goal_kick_r") == 0){
	//							player.getAction().goHome();
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("free_kick_r") == 0) {
	//							player.getAction().goHome();
	//						}
	//					} // end if
	//
	//					//Defining playmode behaviors for left side team
	//					if (player.getMemory().getSide().compareTo("r") == 0) {
	//
	//						if((player.getMemory().getPlayMode().compareTo("before_kick_off") == 0) && player.getTime() > 0) {
	//							player.getAction().move(player.getHome().x, player.getHome().y);
	//						}
	//						else if(player.getMemory().getPlayMode().compareTo("play_on") == 0) {
	//							player.getAction().findBall();
	//						}
	//						else if(player.getMemory().getPlayMode().compareTo("kick_off_r") == 0) {
	//							if (player.getStringPosition().compareTo("center_fwd") == 0) {
	//								player.getAction().kickOff();
	//							}
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("corner_kick_r") == 0){
	//							//TO DO
	//						}
	//						else if (player.getMemory().getPlayMode().compareTo("goal_kick_r") == 0){
	//							player.getAction().goHome();
	//						}						
	//						else if (player.getMemory().getPlayMode().compareTo("free_kick_l") == 0) {
	//							player.getAction().goHome();
	//						}
	//					} //end if
	//
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		}
	//	}

	public Mode getCurrentMode() {
		return currentMode;
	}

	/**
	 * Sets the player mode to defensive
	 */
	public void setDefensive() {
		this.currentMode.setModename("D");
	}

	/**
	 * Sets the player mode to be offensive
	 */
	public void setOffensive() {
		this.currentMode.setModename("O");
	}

	public String getMarked_team() {
		return marked_team;
	}

	public void setMarked_team(String marked_team) {
		this.marked_team = marked_team;
	}

	public String getMarked_unum() {
		return marked_unum;
	}

	public void setMarked_unum(String marked_unum) {
		this.marked_unum = marked_unum;
	}

	public Action getActions() {
		return actions;
	}

	public void setActions(Action actions) {
		this.actions = actions;
	}

	public AbstractPlayer getPlayer() {
		return player;
	}

	public void setPlayer(AbstractPlayer player) {
		this.player = player;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public MathHelp getMathHelp() {
		return mathHelp;
	}

	public void setMathHelp(MathHelp mathHelp) {
		this.mathHelp = mathHelp;
	}

	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}

}
