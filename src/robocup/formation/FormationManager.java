package robocup.formation;

import java.util.HashMap;

public class FormationManager {
	private static FormationManager instance = null;
	private AbstractFormation defaultFormation;
	private HashMap<String, AbstractFormation> formations;
	
	private FormationManager(){
		defaultFormation = new FormationDefault();
		formations = new HashMap<>();
		
		// Load all possible formations
		formations.put(FormationAttack433.class.getName(), new FormationAttack433());
		formations.put(FormationAttack442.class.getName(), new FormationAttack442());
		formations.put(FormationDefense433.class.getName(), new FormationDefense433());
		formations.put(FormationDefense442.class.getName(), new FormationDefense442());
	}
	
	public static FormationManager getInstance(){
		if(instance==null){
			instance = new FormationManager();
		}
		return instance;
	}
	
	public static AbstractFormation getFormation(){
		return getInstance().defaultFormation;
	}	

	public static AbstractFormation getFormation(String name){
		AbstractFormation formation = getInstance().formations.get(name);
		if(formation == null)
			return getInstance().defaultFormation;
		return formation;
	}
	
}
