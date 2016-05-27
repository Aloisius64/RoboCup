package robocup.formation;

import java.util.HashMap;

import robocup.geometry.Vector;
import robocup.player.DefensivePlayer;
import robocup.player.GoalierPlayer;
import robocup.player.OffensivePlayer;

public class FormationAttack433 extends AbstractFormation {

	public FormationAttack433() {
		super();
    	setName(this.getClass().getName());
    	initMaps();
	}
	
	@Override
	protected void initMaps() {
		playersMap = new HashMap<>();
		playersMap.put(0, GoalierPlayer.class);
		playersMap.put(1, DefensivePlayer.class);
		playersMap.put(2, DefensivePlayer.class);
		playersMap.put(3, DefensivePlayer.class);
		playersMap.put(4, DefensivePlayer.class);
		playersMap.put(5, DefensivePlayer.class);
		playersMap.put(6, DefensivePlayer.class);
		playersMap.put(7, OffensivePlayer.class);
		playersMap.put(8, OffensivePlayer.class);
		playersMap.put(9, OffensivePlayer.class);
		playersMap.put(10, OffensivePlayer.class);

		playersPosition = new HashMap<>();
		playersPosition.put(0, new Vector(-47.5, 0));
		playersPosition.put(1, new Vector(-25, -13));
		playersPosition.put(2, new Vector(-20, -5));
		playersPosition.put(3, new Vector(-20,  5));
		playersPosition.put(4, new Vector(-25, 13));
		playersPosition.put(5, new Vector(-5, -8));
		playersPosition.put(6, new Vector(-10, 0));
		playersPosition.put(7, new Vector(-5, 8));
		playersPosition.put(8, new Vector(0, -18));
		playersPosition.put(9, new Vector(-0.204, -0.3265));
		playersPosition.put(10, new Vector(0, 18));
	}

}

