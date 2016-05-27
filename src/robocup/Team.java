package robocup;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import robocup.formation.AbstractFormation;
import robocup.geometry.Vector;
import robocup.player.Player;
import robocup.utility.Settings;

public class Team {
	private List<Player> players;
	private String teamName;
	private AbstractFormation teamFormation;

	public Team(String teamName, AbstractFormation teamFormation) {
		super();
		this.teamName = teamName;
		this.teamFormation = teamFormation;
		this.players = new ArrayList<>();
	}

	public void initTeam() throws SocketException, UnknownHostException, InterruptedException, InstantiationException, IllegalAccessException{
		for (int i = 0; i < Settings.TEAM_SIZE; i++) {
			Player player = teamFormation.getPlayer(i);
			player.getRoboClient().setTeam(teamName); 
			players.add(player);			
		}		

		for (int i = 0; i < Settings.TEAM_SIZE; i++) {
			Player player = players.get(i);
			Vector position = teamFormation.getPlayerPosition(i);
			player.initPlayer(position.X(), position.Y());
			Thread.sleep(100);	
		}
		
		for (Player player : players) {
			player.start();
		}
	}

	public List<Player> getPlayers() {
		return players;
	}

	public String getTeamName() {
		return teamName;
	}

	public AbstractFormation getTeamFormation() {
		return teamFormation;
	}

	//	@Override
	//	public ControllerPlayer getNewControllerPlayer(int i) {
	//		if(formation!=null){
	//			AbstractPlayer_OLD player = FormationManager.getFormation(formation).getPlayer(i);
	//			player.setFormationName(formation);
	//			return player;
	//		}
	//		return new AttackerPlayer_OLD();
	//	}
	//
	//	@Override
	//	public ControllerCoach getNewControllerCoach() {
	//		return null;
	//	}
	//
	//	@Override
	//	public int size() {
	//		return 1;
	////		return Settings.TEAM_SIZE;
	//	}

}
