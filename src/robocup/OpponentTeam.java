package robocup;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationManager;
import robocup.utility.Settings;

public class OpponentTeam extends AbstractTeam {

	public OpponentTeam(String teamName, AbstractFormation formation) {
		super(teamName, Settings.PORT_NUMBER, Settings.IP_SERVER, false);
	}

	@Override
	public ControllerPlayer getNewControllerPlayer(int i) {
		return FormationManager.getFormation().getPlayer(i);
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
