package robocup.formation;

import java.util.HashMap;

import robocup.geometry.Vector;
import robocup.player.DefensivePlayer;
import robocup.player.GoalierPlayer;
import robocup.player.OffensivePlayer;

public class FormationDefense532 extends AbstractFormation {

	public FormationDefense532() {
		super();
		setName(this.getClass().getName());
		initMaps();

	}

	@Override
	protected void initMaps() {
		playersMap = new HashMap<>();
		playersMap.put(1, GoalierPlayer.class);
		playersMap.put(2, DefensivePlayer.class);
		playersMap.put(3, DefensivePlayer.class);
		playersMap.put(4, DefensivePlayer.class);
		playersMap.put(5, DefensivePlayer.class);
		playersMap.put(6, DefensivePlayer.class);
		playersMap.put(7, OffensivePlayer.class);
		playersMap.put(8, OffensivePlayer.class);
		playersMap.put(9, OffensivePlayer.class);
		playersMap.put(10, OffensivePlayer.class);
		playersMap.put(11, OffensivePlayer.class);

		playersPosition = new HashMap<>();
		playersPosition.put(1, new Vector(-45, 0));
		playersPosition.put(2, new Vector(-30, -23));
		playersPosition.put(3, new Vector(-30, -12));
		playersPosition.put(4, new Vector(-30, 0));
		playersPosition.put(5, new Vector(-30, 12));
		playersPosition.put(6, new Vector(-30, 23));
		playersPosition.put(7, new Vector(-15, -25));
		playersPosition.put(8, new Vector(-15, 0));
		playersPosition.put(9, new Vector(-15, 25));
		playersPosition.put(10, new Vector(-5, -10));
		playersPosition.put(11, new Vector(-5, 10));

		playersStringPosition = new HashMap<>();
		playersStringPosition.put(1, "");
		playersStringPosition.put(2, "right_fb");
		playersStringPosition.put(3, "center_fb");
		playersStringPosition.put(4, "left_fb");
		playersStringPosition.put(5, "center_fwd");
		playersStringPosition.put(6, "left_fwd");
		playersStringPosition.put(7, "");
		playersStringPosition.put(8, "");
		playersStringPosition.put(9, "right_fwd");
		playersStringPosition.put(10, "far_right_fwd");
		playersStringPosition.put(11, "far_left_fwd");
	}

}
