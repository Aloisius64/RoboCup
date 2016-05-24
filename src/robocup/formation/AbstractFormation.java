/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.formation;

import java.util.HashMap;

import robocup.geometry.Vector;
import robocup.player.AbstractPlayer;

/**
 *
 * @author aloisius
 */
public abstract class AbstractFormation {

	protected HashMap<Integer, AbstractPlayer> playersMap = null;
	protected HashMap<Integer, Vector> playersPosition = null;

    public AbstractFormation() {
    	initMaps();
    }

    protected abstract void initMaps();
    
    public void movePlayerToItsPosition(Integer playerNum){
    	AbstractPlayer player = playersMap.get(playerNum);
    	Vector position = playersPosition.get(playerNum);
    	player.getPlayer().move((int) position.X(), (int) position.Y());    	
    }
        
    public AbstractPlayer getPlayer(Integer playerNum){
    	return playersMap.get(playerNum);
    }
}
