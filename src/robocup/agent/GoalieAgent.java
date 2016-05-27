package robocup.agent;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.player.GoalierPlayer;


public class GoalieAgent {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
		GoalierPlayer g = new GoalierPlayer();
		g.initPlayer(-40, 0);
		g.start();
	}

}
