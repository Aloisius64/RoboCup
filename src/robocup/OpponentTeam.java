package robocup;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

import robocup.formation.AbstractFormation;
import robocup.utility.Settings;

public class OpponentTeam extends AbstractTeam {

	private final AbstractFormation formation;

	public OpponentTeam(String teamName, AbstractFormation formation) {
		super(teamName, Settings.PORT_NUMBER, Settings.IP_SERVER, false);
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
		return Settings.TEAM_SIZE;
	}
	
}
