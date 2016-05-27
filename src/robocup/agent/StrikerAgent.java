package robocup.agent;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.player.Player;
import robocup.utility.Pos;


public class StrikerAgent {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws UnknownHostException, InterruptedException, SocketException {
		Player p = new Player();
		Pos origin = new Pos(0,0);
		
		p.initPlayer(-5, -10);
		
		while (true) {
			p.receiveInput();
			
			if (p.getMemory().timeCheck(p.getTime())) {
				p.setTime(p.getMemory().getObjMemory().getTime());
				
			p.getAction().findBall();
			
			}
		}
	}
}
