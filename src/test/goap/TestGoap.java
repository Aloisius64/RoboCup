package test.goap;

public class TestGoap {

	public static void main(String[] args) {

		GoapAgentPlayer player = new GoapAgentPlayer();
		
		for (int i = 0; i < 7; i++) {
			player.update();
		}

	}

}
