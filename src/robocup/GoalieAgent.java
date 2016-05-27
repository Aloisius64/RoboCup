package robocup;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.player.GoalierPlayer;


public class GoalieAgent {

	public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
		GoalierPlayer g = new GoalierPlayer("Team");
		g.initPlayer(-50, 0);
		g.start();
	}

}
