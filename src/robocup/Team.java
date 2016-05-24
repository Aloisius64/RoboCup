package robocup;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

import robocup.formation.AbstarctFormation;

public class Team extends AbstractTeam {

	private static final int TEAM_SIZE = 11;
	private static final String IP_SERVER = "localhost"; 
	private static final int PORT_NUMBER = 6000;
	private final AbstarctFormation formation;

	public Team(String teamName, AbstarctFormation formation) {
		super(teamName, PORT_NUMBER, IP_SERVER, false);
		this.formation = formation;
	}

	@Override
	public ControllerPlayer getNewControllerPlayer(int i) {
		return formation.getPlayer(i);
	}

	@Override
	public ControllerCoach getNewControllerCoach() {
		return null;
	}

	@Override
	public int size() {
		return TEAM_SIZE;
	}

	public AbstarctFormation getFormation() {
		return formation;
	}

}
