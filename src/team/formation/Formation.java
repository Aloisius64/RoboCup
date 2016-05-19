/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.formation;

import com.github.robocup_atan.atan.model.ActionsPlayer;

/**
 *
 * @author aloisius
 */
public abstract class Formation {

    public Formation() {
    }
    
    public abstract void movePlayerToPosition(ActionsPlayer player);
}
