package robocup;

import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationManager;
import robocup.formation.FormationTest;

public class RoboCup {

    public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException, InstantiationException, IllegalAccessException {

    	AbstractFormation formation = FormationManager.getFormation(FormationTest.class.getName()); 
    	Team dropTableUsers = new Team("DropTableUsers", formation);
    	dropTableUsers.initTeam();
    	
//    	
    	Team fox = new Team("Fox", formation);
    	fox.initTeam();
    	
    }

}
