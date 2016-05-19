package team;

import org.apache.log4j.BasicConfigurator;

import com.github.robocup_atan.atan.model.AbstractTeam;

public class RoboCup {

    /**
     * Start up team Simple1
     *
     * @param args No args
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        AbstractTeam team = new Team("Team");
        team.connectAll();
    }

}
