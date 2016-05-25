package robocup;

import org.apache.log4j.BasicConfigurator;

import com.github.robocup_atan.atan.model.AbstractTeam;

public class RoboCup {

    public static void main(String[] args) {
        BasicConfigurator.configure();
                
        AbstractTeam team = new Team("DropTableUsers");
        team.connectAll();
        
        AbstractTeam opponentTeam = new OpponentTeam("Opponent");
        opponentTeam.connectAll();
    }

}
