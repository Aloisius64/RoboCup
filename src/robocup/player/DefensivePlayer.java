package robocup.player;

import java.net.UnknownHostException;

import robocup.brain.FullBackBrain;
import robocup.memory.Memory;
import robocup.network.RoboClient;
import robocup.objInfo.ObjInfo;
import robocup.objInfo.ObjPlayer;
import robocup.utility.Parser;
import robocup.utility.Pos;

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
public class DefensivePlayer extends Player{
	
	public DefensivePlayer() {
		super();
		this.setBrain(new FullBackBrain(this));
	}

	public DefensivePlayer(RoboClient rc, Memory m, ObjInfo i, Parser p, int time) {
		super(rc, m, i, p, time);
		this.setBrain(new FullBackBrain(this));
	}

	public DefensivePlayer(String team) {
		super(team);
		this.setBrain(new FullBackBrain(this));
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

			if (!getMemory().getPlayers().isEmpty()) {  
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
			else {  //No players in Fullback's sight, so turn to another point to check again
				turn(30);

				if (!getMemory().getPlayers().isEmpty()) {  
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
		}		
		return closestPlayer;
	}

	/*
	 * Checks to see if the FullBack is on his own side of the field.
	 * @return boolean true if he is, false if he is not.
	 */
	public boolean inFullBackZone(){
		if (getMemory().getPosition().x < -10) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * Defines Fullback defensive behavior.
	 * @pre Playmode is play_on.
	 */
	public void runDefense() throws UnknownHostException, InterruptedException {
		if (!inFullBackZone()) {
			getAction().goHome();
		}

		getAction().FullBack_findBall();
	}

	/*
	 * Defines ongoing FullBack soccer behaviors for his own thread.
	 */
	public void run() {
		while(true) {
			try {
				receiveInput();
			} catch (InterruptedException e) {
				System.out.println("Interrupt error in FullBack.run");
				e.printStackTrace();
			}

			if(getMemory().getCurrent() != null) {
				Pos pt = mathHelp.vSub(getMemory().getCurrent(), getMemory().getHome());

				if(mathHelp.mag(pt) > 0.5) {
					getMemory().setHome(false);
				}
				else
					getMemory().setHome(true);
			}
			else 
				System.out.println("Current is null");		
		}
	}	
}
