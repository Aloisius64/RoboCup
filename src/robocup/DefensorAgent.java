package robocup;

import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.player.AbstractPlayer;
import robocup.player.DefensivePlayer;

public class DefensorAgent {

	public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
		AbstractPlayer player = new DefensivePlayer("Team");
		player.initPlayer(-30, 0);
		player.start();
	}

}
