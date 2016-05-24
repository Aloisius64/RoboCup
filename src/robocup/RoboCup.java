package robocup;

import org.apache.log4j.BasicConfigurator;

import com.github.robocup_atan.atan.model.AbstractTeam;

import robocup.formation.AbstarctFormation;
import robocup.formation.FormationDefault;

public class RoboCup {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        
        AbstarctFormation formation = new FormationDefault();
        
        AbstractTeam team = new Team("DropTableUsers", formation);
        team.connectAll();
        
        AbstractTeam opponentTeam = new OpponentTeam("Calimeri", formation);
        opponentTeam.connectAll();
    }

}
