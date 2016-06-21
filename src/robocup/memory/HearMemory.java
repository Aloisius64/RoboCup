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

	public class Evaluation {
		public int evaluation;
		public int playerSender;
		public Position playerPosition;

		public Evaluation() {
			this.evaluation = Integer.MIN_VALUE;
			this.playerPosition = null;
			this.playerSender = 0;
		}

	}

	private HashMap<Integer, Double> ourMessages;
	private HashMap<Integer, Message> otherMessages;
	private Evaluation attacker;
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
		this.attacker = new Evaluation();
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
			if (msg.getTeam().equals("our")) {
				String splittedDouble = msg.getMessage().replace('p', '.').substring(1, msg.getMessage().length() - 1);
				String[] messageValue = splittedDouble.split("A");
				Evaluation attackerEvaluation = new Evaluation();
				attackerEvaluation.evaluation = Integer.valueOf(messageValue[1]);
				attackerEvaluation.playerPosition = new Position(Integer.valueOf(messageValue[1]),
						Integer.valueOf(messageValue[2]));
				attackerEvaluation.playerSender = Integer.valueOf(messageValue[0]);
				if (attacker == null) {
					attacker = attackerEvaluation;
				} else {
					attacker = getBestAttackerEvaluation(attacker, attackerEvaluation);
				}
			}
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
		// System.out.println("print here");
		for (Integer i : ourMessages.keySet()) {
			System.out.println(memory.getuNum() + ":  " + i + " " + ourMessages.get(i));
		}
	}

	public Evaluation getAttackerEvaluation() {
		return attacker;
	}

	public static Evaluation getBestAttackerEvaluation(Evaluation a, Evaluation b) {
		return a.evaluation <= b.evaluation ? b : a;
	}

}
