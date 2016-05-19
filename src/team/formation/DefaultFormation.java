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
public class DefaultFormation extends Formation {

    public DefaultFormation() {
        super();
    }

    @Override
    public void movePlayerToPosition(ActionsPlayer player) {
        switch (player.getNumber()) {
            case 1:
                player.move(-50, 0);
                break;
            case 2:
                player.move(-40, -30);
                break;
            case 3:
                player.move(-40, -10);
                break;
            case 4:
                player.move(-40, 10);
                break;
            case 5:
                player.move(-40, 30);
                break;
            case 6:
                player.move(-20, -30);
                break;
            case 7:
                player.move(-20, -10);
                break;
            case 8:
                player.move(-20, 10);
                break;
            case 9:
                player.move(-20, 30);
                break;
            case 10:
                player.move(-10, -10);
                break;
            case 11:
                player.move(-10, 10);
                break;
            default:
                break;
        }
    }

}
