package team;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

public class Team extends AbstractTeam {

	public Team(String teamName) {
		super(teamName, 6000, "localhost", false);
	}

	@Override
	public ControllerPlayer getNewControllerPlayer(int i) {
		i++;
		if (i == 11) {
			return new Attacker();
		}
		// Defense
		// Attack
		// Other roles
		return new Player();
	}

	@Override
	public ControllerCoach getNewControllerCoach() {
		return null;
	}

	@Override
	public int size() {
		return 11;
	}

}
