/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.formation;

import java.util.HashMap;

import robocup.geometry.Vector;
import robocup.player.DefensivePlayer;
import robocup.player.GoalierPlayer;
import robocup.player.OffensivePlayer;

/**
 *
 * @author aloisius
 */
public class FormationDefault extends AbstractFormation {

	public FormationDefault() {
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
		playersPosition.put(0, new Vector(-40, 0));
		playersPosition.put(1, new Vector(-30, 25));
		playersPosition.put(2, new Vector(-30, 0));
		playersPosition.put(3, new Vector(-30, -25));
		playersPosition.put(4, new Vector(-15, 0));		
		playersPosition.put(5, new Vector(-5, -10));
		playersPosition.put(6, new Vector(-15, 25));		
		playersPosition.put(7, new Vector(-15, -25));
		playersPosition.put(8, new Vector(-5, 10));
		playersPosition.put(9, new Vector(-5, 25));
		playersPosition.put(10, new Vector(-5, -25));

		playersStringPosition = new HashMap<>();
		playersStringPosition.put(0, "");
		playersStringPosition.put(1, "right_fb");
		playersStringPosition.put(2, "center_fb");
		playersStringPosition.put(3, "left_fb");
		playersStringPosition.put(4, "center_fwd");
		playersStringPosition.put(5, "left_fwd");
		playersStringPosition.put(6, "");
		playersStringPosition.put(7, "");
		playersStringPosition.put(8, "right_fwd");
		playersStringPosition.put(9, "far_right_fwd");
		playersStringPosition.put(10, "far_left_fwd");
	}
	
}
