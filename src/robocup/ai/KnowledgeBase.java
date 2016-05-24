/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.robocup_atan.atan.model.enums.Flag;
import com.github.robocup_atan.atan.model.enums.PlayMode;

import robocup.FlagCategory;
import robocup.sensors.Ball;
import robocup.sensors.SeenFlag;
import robocup.sensors.SeenLine;
import robocup.sensors.SeenPlayer;
import robocup.sensors.SenseBody;

/**
 *
 * @author aloisius
 */
public class KnowledgeBase {

	private Map<FlagCategory, Map<Flag, SeenFlag>> flags;
	private final List<SeenPlayer> otherPlayers;
	private final List<SeenPlayer> ownPlayers;
	private final List<SeenLine> lines;
	private SenseBody senseBody;
	private Ball ball;
	private PlayMode playMode;

	public KnowledgeBase() {
		flags = new HashMap<FlagCategory, Map<Flag, SeenFlag>>();
		otherPlayers = new ArrayList<>();
		ownPlayers = new ArrayList<>();
		lines = new ArrayList<>();
		senseBody = null;
		this.ball = new Ball(Double.MAX_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0);
		playMode = null;

	}

	public List<SeenPlayer> getOtherPlayers() {
		return otherPlayers;
	}

	public List<SeenPlayer> getOwnPlayers() {
		return ownPlayers;
	}

	public void addFlag(FlagCategory category, Flag flag, SeenFlag seenFlag) {
		if (!flags.containsKey(category)) {
			flags.put(category, new HashMap<Flag, SeenFlag>());
		}
		flags.get(category).put(flag, seenFlag);
	}

	public void addOtherPlayer(SeenPlayer seenPlayer) {
		otherPlayers.add(seenPlayer);
	}

	public void addOwnPlayer(SeenPlayer seenPlayer) {
		ownPlayers.add(seenPlayer);
	}

	public void addLine(SeenLine seenLine) {
		lines.add(seenLine);
	}

	public void clean() {
		flags.clear();
		otherPlayers.clear();
		ownPlayers.clear();
		lines.clear();
		senseBody = null;
		ball.setSeenInLastStep(false);
	}

	public SenseBody getSenseBody() {
		return senseBody;
	}

	public void setSenseBody(SenseBody senseBody) {
		this.senseBody = senseBody;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public PlayMode getPlayMode() {
		return playMode;
	}

	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}

	public Map<FlagCategory, Map<Flag, SeenFlag>> getFlags() {
		return flags;
	}

	public void setFlags(Map<FlagCategory, Map<Flag, SeenFlag>> flags) {
		this.flags = flags;
	}

}
