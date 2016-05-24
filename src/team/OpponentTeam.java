package team;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

public class OpponentTeam extends AbstractTeam {

	public  OpponentTeam(String teamName) {
		super(teamName, 6000, "localhost", false);
	}

	@Override
	public ControllerPlayer getNewControllerPlayer(int i) {
		return new Player();
	}

	@Override
	public ControllerCoach getNewControllerCoach() {
		return null;
	}

	@Override
	public int size() {
		return 1;
	}
}
