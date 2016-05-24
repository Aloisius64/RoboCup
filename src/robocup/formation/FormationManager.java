package robocup.formation;

public class FormationManager {
	private static FormationManager instance = null;
	private AbstractFormation formation;
	
	private FormationManager(){
		// TODO review this
		formation = new FormationDefault();
	}
	
	public static FormationManager getInstance(){
		if(instance==null){
			instance = new FormationManager();
		}
		return instance;
	}
	
	public static AbstractFormation getFormation(){
		return getInstance().formation;
	}
	
}
