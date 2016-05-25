package robocup;

import com.github.robocup_atan.atan.model.AbstractTeam;
import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;

import robocup.formation.FormationManager;
import robocup.player.AttackerPlayer;
import robocup.utility.Settings;

public class Team extends AbstractTeam {

	private String formation = null;	// Team formation name

	public Team(String teamName) {
		super(teamName, Settings.PORT_NUMBER, Settings.IP_SERVER, false);
	}
	
	public Team(String teamName, String formation) {
		super(teamName, Settings.PORT_NUMBER, Settings.IP_SERVER, false);
		this.formation  = formation;
	}

	@Override
	public ControllerPlayer getNewControllerPlayer(int i) {
		if(formation!=null){
			return FormationManager.getFormation(formation).getPlayer(i);
		}
		return new AttackerPlayer();
	}

	@Override
	public ControllerCoach getNewControllerCoach() {
		return null;
	}

	@Override
	public int size() {
		return 1;
//		return Settings.TEAM_SIZE;
	}

}
