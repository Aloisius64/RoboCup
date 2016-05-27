package robocup.player;

import java.net.UnknownHostException;

import robocup.ai.DefensiveAI;
import robocup.objInfo.ObjPlayer;

/** @file FullBack.java
 * Class file for FullBack class
 * @author Joel Tanzi
 * @date 5 November 2011
 * @version 1.0 
 */

/** @class FullBack
 * The FullBack class inherits from the Player class.  The FullBack is a specialized
 * type of Player that focuses on defensive behaviors such as interfering with opponent scoring.
 */
public class DefensivePlayer extends AbstractPlayer{

	public DefensivePlayer() {
		super();
		this.setAi(new DefensiveAI(this));
	}

	public DefensivePlayer(String team) {
		super(team);
		this.setAi(new DefensiveAI(this));
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

	public boolean inFullBackZone(){
		if (getMemory().getPosition().x < -10) {
			return true;
		} else {
			return false;
		}
	}

	public void runDefense() throws UnknownHostException, InterruptedException {
		if (!inFullBackZone()) {
			getAction().goHome();
		}

		getAction().FullBack_findBall();
	}

}
