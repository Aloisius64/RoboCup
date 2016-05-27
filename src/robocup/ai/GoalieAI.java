package robocup.ai;

import java.net.UnknownHostException;

import robocup.memory.Mode;
import robocup.player.GoalierPlayer;
import robocup.utility.Polar;
import robocup.utility.Pos;

/**@class Brain
 * The brain serves as a place to store the Player modes, marked players for
 * various functions, and a set of strategies for player actions.
 */
public class GoalieAI extends AbstractAI {

	public GoalieAI(GoalierPlayer goalierPlayer) {
		super(goalierPlayer);
	}

	public GoalieAI(Mode currentMode) {
		super();
		this.setCurrentMode(currentMode);
	}

	/**
	 * The Brain thread run method. It causes the Goalie to exhibit soccer behaviors.
	 * 
	 * @post Goalie will perform Goalie functions during match.
	 */
	@Override
	public void run() {

		Pos uppergoalkick = new Pos(-47.36, -8.74);
		Pos lowergoalkick = new Pos(-47.36, 8.74);

		while (true) {			

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if(getPlayer().getMemory().timeCheck(getPlayer().getTime())) {
				getPlayer().setTime(getPlayer().getMemTime());
				((GoalierPlayer)getPlayer()).followBall();


				//Defining playmode behaviors for left side team
				if (getPlayer().getMemory().getSide().compareTo("l") == 0) {				

					if(getPlayer().getMemory().getPlayMode().compareTo("play_on") == 0) {
						((GoalierPlayer)getPlayer()).followBall();
					}
					else if (getPlayer().getMemory().getPlayMode().compareTo("goal_kick_l") == 0){
						Polar upper = getPlayer().getMemory().getAbsPolar(getPlayer().getMemory().getFlagPos("fplt"));
						try {
							getPlayer().turn(getPlayer().getAction().getTurn(upper));
						} catch (UnknownHostException | InterruptedException e) {
							e.printStackTrace();
						}

						if (getPlayer().getMemory().isObjVisible("ball")) {
							getPlayer().getAction().gotoPoint(uppergoalkick);
							try {
								getPlayer().kick(60, 0);
							} catch (UnknownHostException | InterruptedException e) {
								e.printStackTrace();
							}
						}
						else {
							getPlayer().getAction().gotoPoint(lowergoalkick);
							try {
								getPlayer().kick(60, 0);
							} catch (UnknownHostException | InterruptedException e) {
								e.printStackTrace();
							}
						}
					}					
				}// end if

				//Defining playmode behaviors for right side team
				try {
					if (getPlayer().getMemory().getSide().compareTo("r") == 0) {
						if((getPlayer().getMemory().getPlayMode().compareTo("before_kick_off") == 0) && getPlayer().getTime() > 0) {
							getPlayer().move(getPlayer().getHome().x, getPlayer().getHome().y);
						}
						else if(getPlayer().getMemory().getPlayMode().compareTo("play_on") == 0) {
							getPlayer().getAction().findBall();
						}
						else if(getPlayer().getMemory().getPlayMode().compareTo("kick_off_r") == 0) {
							if (getPlayer().getStringPosition().compareTo("center_fwd") == 0) {
								getPlayer().getAction().kickOff();
							}
						}
						else if (getPlayer().getMemory().getPlayMode().compareTo("goal_kick_r") == 0){
							getPlayer().getAction().goHome();
						}						
						else if (getPlayer().getMemory().getPlayMode().compareTo("free_kick_l") == 0) {
							getPlayer().getAction().goHome();
						}
					} //end if
				} catch (UnknownHostException | InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
	}

}

