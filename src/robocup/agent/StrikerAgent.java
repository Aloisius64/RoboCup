package robocup.agent;
import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.player.OffensivePlayer;
import robocup.player.AbstractPlayer;


public class StrikerAgent {

	public static void main(String[] args) throws UnknownHostException, InterruptedException, SocketException {
		AbstractPlayer p = new OffensivePlayer();
		
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
