package robocup.formation;

import java.util.HashMap;

import robocup.geometry.Vector;
import robocup.player.AttackerPlayer;
import robocup.player.DefensorPlayer;
import robocup.player.GoalierPlayer;

public class FormationAttack433 extends AbstarctFormation {

	public FormationAttack433() {
		super();
	}
	
	@Override
	protected void initMaps() {
		playersMap = new HashMap<>();
		playersMap.put(1, new GoalierPlayer(this));
		playersMap.put(2, new DefensorPlayer(this));
		playersMap.put(3, new DefensorPlayer(this));
		playersMap.put(4, new DefensorPlayer(this));
		playersMap.put(5, new DefensorPlayer(this));
		playersMap.put(6, new DefensorPlayer(this));
		playersMap.put(7, new DefensorPlayer(this));
		playersMap.put(8, new DefensorPlayer(this));
		playersMap.put(9, new DefensorPlayer(this));
		playersMap.put(10, new AttackerPlayer(this));
		playersMap.put(11, new AttackerPlayer(this));
		
		playersPosition = new HashMap<>();
		playersPosition.put(1, new Vector(-47.5, 0));
		playersPosition.put(2, new Vector(-25, -13));
		playersPosition.put(3, new Vector(-20, -5));
		playersPosition.put(4, new Vector(-20,  5));
		playersPosition.put(5, new Vector(-25, 13));
		playersPosition.put(6, new Vector(-5, -8));
		playersPosition.put(7, new Vector(-10, 0));
		playersPosition.put(8, new Vector(-5, 8));
		playersPosition.put(9, new Vector(0, -18));
		playersPosition.put(10, new Vector(-0.204, -0.3265));
		playersPosition.put(11, new Vector(0, 18));
	}

}

