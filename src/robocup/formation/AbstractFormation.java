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

	private String name;
	protected HashMap<Integer, Class<? extends AbstractPlayer> > playersMap = null;
	protected HashMap<Integer, Vector> playersPosition = null;
	protected HashMap<Integer, String> playersStringPosition = null;

    public AbstractFormation() {
        setName("");
    }

    protected abstract void initMaps();
            
    public AbstractPlayer getPlayer(Integer playerNum) throws InstantiationException, IllegalAccessException{
    	assert(playerNum>=0 && playerNum<11);
    	Class<? extends AbstractPlayer> playerClass = playersMap.get(playerNum);
    	AbstractPlayer newInstance = playerClass.newInstance();
    	newInstance.setFormationName(this.getClass().getName());
    	return newInstance;
    }
    
    public String getPlayerClass(Integer playerNum){
    	Class<? extends AbstractPlayer> playerClass = playersMap.get(playerNum);
    	return playerClass.getSimpleName();
    }
    
    public Vector getPlayerPosition(Integer playerNum){
    	assert(playerNum>=0 && playerNum<11);
    	return playersPosition.get(playerNum);
    }
    
    public String getPlayerStringPosition(Integer playerNum){
    	assert(playerNum>=0 && playerNum<11);
    	return playersStringPosition.get(playerNum);    	
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
