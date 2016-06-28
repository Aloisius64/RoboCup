/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.memory;

import java.util.HashMap;

import robocup.utility.Message;
import robocup.utility.Position;

/**
 *
 * @author aloisius
 */
public class HearMemory {

	private HashMap<Integer, Double> ballDistances;
	private HashMap<Integer, Integer> evaluations;
	private HashMap<Integer, Position> positions;
	private Memory memory;
	// private int lastTime = 0;
	// private int numCycles = 3;

	public HearMemory() {
		// TODO Auto-generated constructor stub
	}

	public HearMemory(Memory memory) {
		this.ballDistances = new HashMap<Integer, Double>();
		this.evaluations = new HashMap<Integer, Integer>();
		this.positions = new HashMap<Integer, Position>();
		this.memory = memory;
	}

	// public void clean() {
	// ourMessages.clear();
	// otherMessages.clear();
	// }

	public void onMessageReceived(Message msg) {

		// if(msg.getTeam().equals(player.getTeam())){
		// ourMessages.put(msg.getPlayerSender(), msg);
		// }
		// msg tipo 23.5 ---> 23p5
		if (msg.getTeam().equals("our")) {
			String message = msg.getMessage().substring(1, msg.getMessage().length() - 1);
			if (message.contains("A")) {
				String[] splitMessage = message.split("A");
				evaluations.put(Integer.parseInt(splitMessage[0]), Integer.parseInt(splitMessage[1]));
			} else if (message.contains("X")) {
				String[] splitMessage = message.replace("p", ".").replace("m", "-").split("X");
				int player = Integer.parseInt(splitMessage[0]);
				if (!positions.containsKey(player)) {
					positions.put(player, new Position(Double.parseDouble(splitMessage[1]), Double.MIN_VALUE));
				} else {
					positions.get(player).x = Double.parseDouble(splitMessage[1]);
				}
			} else if (message.contains("Y")) {
				String[] splitMessage = message.replace("p", ".").replace("m", "-").split("Y");
				int player = Integer.parseInt(splitMessage[0]);
				if (!positions.containsKey(player)) {
					positions.put(player, new Position(Double.MIN_VALUE, Double.parseDouble(splitMessage[1])));
				} else {
					positions.get(player).y = Double.parseDouble(splitMessage[1]);
				}
			} else if (message.contains("B")) {
				String[] splitMessage = message.replace("p", ".").split("B");
				ballDistances.put(Integer.parseInt(splitMessage[0]), Double.parseDouble(splitMessage[1]));
			}
		}

		// throw new UnsupportedOperationException("Not supported yet."); //To
		// change body of generated methods, choose Tools | Templates.
	}

	public void ourMessagePrint() {

	}

	public HashMap<Integer, Double> getBallDistances() {
		return ballDistances;
	}

	public void setBallDistances(HashMap<Integer, Double> ballDistances) {
		this.ballDistances = ballDistances;
	}

}
