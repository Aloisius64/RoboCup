package robocup;

import org.apache.log4j.BasicConfigurator;

import com.github.robocup_atan.atan.model.AbstractTeam;

import robocup.formation.FormationManager;

public class RoboCup {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        
        FormationManager.getInstance();
        
        AbstractTeam team = new Team("DropTableUsers");
        team.connectAll();
        
//        AbstractTeam opponentTeam = new OpponentTeam("Calimeri");
//        opponentTeam.connectAll();
    }

}
