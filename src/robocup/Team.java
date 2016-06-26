package robocup;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import robocup.formation.AbstractFormation;
import robocup.geometry.Vector;
import robocup.player.AbstractPlayer;
import robocup.utility.Settings;

public class Team {
	private List<AbstractPlayer> players;
	private String teamName;
	private AbstractFormation teamFormation;

	public Team(String teamName, AbstractFormation teamFormation) {
		super();
		this.teamName = teamName;
		this.teamFormation = teamFormation;
		this.players = new ArrayList<>();
	}

	public void initTeam() throws SocketException, UnknownHostException, InterruptedException, InstantiationException,
			IllegalAccessException {
		for (int i = 1; i <= Settings.TEAM_SIZE; i++) {
			AbstractPlayer player = teamFormation.getPlayer(i);
			player.getRoboClient().setTeam(teamName);
			player.setStringPosition(teamFormation.getPlayerStringPosition(i));
			players.add(player);
		}

		for (int i = 0; i < Settings.TEAM_SIZE; i++) {
			AbstractPlayer player = players.get(i);
			Vector position = teamFormation.getPlayerPosition(i + 1);
			if (i == 0)
				player.initGoalie(position.X(), position.Y());
			else
				player.initPlayer(position.X(), position.Y());

			Thread.sleep(100);
		}

		for (AbstractPlayer player : players) {
			player.start();
		}
	}

	public List<AbstractPlayer> getPlayers() {
		return players;
	}

	public String getTeamName() {
		return teamName;
	}

	public AbstractFormation getTeamFormation() {
		return teamFormation;
	}

}
