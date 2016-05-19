/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team;

import team.playerSeenData.SeenFlag;
import java.util.ArrayList;
import java.util.List;
import team.playerSeenData.SeenLine;
import team.playerSeenData.SeenPlayer;

/**
 *
 * @author aloisius
 */
public class KnowledgeBase {

    private final List<SeenFlag> flags;
    private final List<SeenPlayer> otherPlayers;
    private final List<SeenPlayer> ownPlayers;
    private final List<SeenLine> lines;

    public KnowledgeBase() {
        flags = new ArrayList<>();
        otherPlayers = new ArrayList<>();
        ownPlayers = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public List<SeenFlag> getFlags() {
        return flags;
    }

    public List<SeenPlayer> getOtherPlayers() {
        return otherPlayers;
    }

    public List<SeenPlayer> getOwnPlayers() {
        return ownPlayers;
    }

    public void addFlag(SeenFlag seenFlag) {
        flags.add(seenFlag);
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
    }

}
