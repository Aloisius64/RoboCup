package robocup;

import java.net.SocketException;
import java.net.UnknownHostException;

import robocup.formation.AbstractFormation;
import robocup.formation.FormationDefense532;
import robocup.formation.FormationManager;

public class RoboCup {

	public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException,
			InstantiationException, IllegalAccessException {

		AbstractFormation formation = FormationManager.getFormation(FormationDefense532.class.getName());
		Team dropTableUsers = new Team("DropTableUsers", formation);
		dropTableUsers.initTeam();

//		//
		formation = FormationManager.getFormation(FormationDefense532.class.getName());
		Team fox = new Team("Fox", formation);
		fox.initTeam();

	}

}
