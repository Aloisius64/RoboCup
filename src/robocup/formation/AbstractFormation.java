/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.formation;

import java.util.HashMap;

import com.github.robocup_atan.atan.model.ActionsPlayer;

import robocup.geometry.Vector;
import robocup.player.Player;

/**
 *
 * @author aloisius
 */
public abstract class AbstractFormation {

	private String name;
	protected HashMap<Integer, Class<? extends Player> > playersMap = null;
	protected HashMap<Integer, Vector> playersPosition = null;

    public AbstractFormation() {
        setName("");
    }

    protected abstract void initMaps();
    
    public void movePlayerToItsPosition(ActionsPlayer player){
    	Vector position = playersPosition.get(player.getNumber());
    	player.move((int) position.X(), (int) position.Y());    	
    }
        
    public Player getPlayer(Integer playerNum) throws InstantiationException, IllegalAccessException{
    	assert(playerNum>=0 && playerNum<11);
    	Class<? extends Player> playerClass = playersMap.get(playerNum);
    	return playerClass.newInstance();
    }
    
    public Vector getPlayerPosition(Integer playerNum){
    	assert(playerNum>=0 && playerNum<11);
    	return playersPosition.get(playerNum);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
