/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.memory;

import java.util.HashMap;

import robocup.utility.Message;

/**
 *
 * @author aloisius
 */
public class HearMemory {

	private HashMap<Integer, Double> ourMessages;
	private HashMap<Integer, Message> otherMessages;
	private Memory memory;
	// private int lastTime = 0;
	// private int numCycles = 3;

	public HearMemory() {
		// TODO Auto-generated constructor stub
	}

	public HearMemory(Memory memory) {
		this.ourMessages = new HashMap<Integer, Double>();
		this.otherMessages = new HashMap<Integer, Message>();
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
		if (msg.getMessage().startsWith("A")) {

		} else if (msg.getMessage().startsWith("D")) {

		} else {
			String splittedDouble = msg.getMessage().replace('p', '.').substring(1, msg.getMessage().length() - 1);
			// System.out.println(splittedDouble);
			double messageDistance = Double.parseDouble(splittedDouble);
			if (msg.getTeam().equals("our")) {
				// System.out.println(memory.getuNum() + " distance :" +
				// messageDistance + " sender:" + msg.getPlayerSender());
				ourMessages.put(msg.getPlayerSender(), messageDistance);
			}
		}
		// throw new UnsupportedOperationException("Not supported yet."); //To
		// change body of generated methods, choose Tools | Templates.
	}

	public HashMap<Integer, Double> getOurMessages() {
		return ourMessages;
	}

	public void setOurMessages(HashMap<Integer, Double> ourMessages) {
		this.ourMessages = ourMessages;
	}

	public HashMap<Integer, Message> getOtherMessages() {
		return otherMessages;
	}

	public void setOtherMessages(HashMap<Integer, Message> otherMessages) {
		this.otherMessages = otherMessages;
	}

	public void ourMessagePrint() {
//		System.out.println("print                        here");
		for (Integer i : ourMessages.keySet()) {
			System.out.println(memory.getuNum() + ":  " + i + " " + ourMessages.get(i));
		}
	}

}
