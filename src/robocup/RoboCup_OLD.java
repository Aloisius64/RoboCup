package robocup;

import robocup.player.AbstractPlayer;
import robocup.player.OffensivePlayer;

public class RoboCup_OLD {
	public static void main(String args[]) throws Exception
	{
		//Instantiate each player client
		AbstractPlayer p1 = new OffensivePlayer();
		AbstractPlayer p2 = new OffensivePlayer();
		AbstractPlayer p3 = new OffensivePlayer();
		AbstractPlayer p4 = new OffensivePlayer();
		AbstractPlayer p5 = new OffensivePlayer();
		//		FullBack p6 = new FullBack();
		//		FullBack p7 = new FullBack();
		//		FullBack p8 = new FullBack();
		//		Goalie g9 = new Goalie();

		p1.initPlayer(-5, -25, "far_left_fwd");
		Thread.sleep(100);

		p2.initPlayer(-5, -10, "left_fwd");
		Thread.sleep(100);

		p3.initPlayer(-5, 10, "right_fwd");
		Thread.sleep(100);

		p4.initPlayer(-5, 25, "far_right_fwd");
		Thread.sleep(100);

		p5.initPlayer(-15, 0, "center_fwd");
		Thread.sleep(100);

		//		p6.initFullBack(-30, -25, "left_fb");
		//		Thread.sleep(100);
		//
		//		p7.initFullBack(-30, 0, "center_fb");
		//		Thread.sleep(100);
		//
		//		p8.initFullBack(-30, 25, "right_fb");
		//		Thread.sleep(100);
		//
		//		g9.initGoalie(-40, 0);
		//		Thread.sleep(100);	

		//Begin soccer match behaviors
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		//		p6.start();
		//		p7.start();
		//		p8.start();
		//		g9.start();
		//p10.start();
		//p11.start();
	}
}
