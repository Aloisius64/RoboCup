package robocup.objInfo;

/**
 * @class ObjPlayer
 * 
 *        container for player ObjInfo
 *
 */
public class ObjPlayer extends ObjInfo {

	public ObjPlayer() {
		super("player");
	}

	/**
	 * The Team Name getter
	 * 
	 * @return the name of the team the player is on, if they're close enough to
	 *         see the team
	 * 
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * The Team Name setter
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * The Uniform Number getter
	 * 
	 * @return the Uniform Number on the player's shirt, if they're close enough
	 *         to see it
	 * 
	 */
	public int getuNum() {
		return uNum;
	}

	/**
	 * The Uniform Number getter
	 */
	public void setuNum(int uNum) {
		this.uNum = uNum;
	}

	/**
	 * A check to see if the player is a goalie or field player
	 * 
	 * @return true if the player is the goalie, false if s/he is not
	 */
	public boolean isGoalie() {
		return goalie;
	}

	/**
	 * The goalie check setter
	 */
	public void setGoalie(boolean goalie) {
		this.goalie = goalie;
	}

	/**
	 * A getter for the player's head direction
	 * 
	 * @return a double of the angle, in degrees, of the direction of the
	 *         player's head relative to your own. The angle is 0 if they are
	 *         both facing each other.
	 */
	public double getHeadDir() {
		return headDir;
	}

	/**
	 * The head direction setter
	 */
	public void setHeadDir(double headDir) {
		this.headDir = headDir;
	}

	/**
	 * A getter for the player's body direction
	 * 
	 * @return a double of the angle, in degrees, of the direction of the
	 *         player's body relative to your own. The angle is 0 if their
	 *         bodies are both facing each other.
	 */
	public double getBodyDir() {
		return bodyDir;
	}

	/**
	 * The body direction setter
	 */
	public void setBodyDir(double bodyDir) {
		this.bodyDir = bodyDir;
	}

	
	private String team;
	private int uNum;
	private boolean goalie;
	private double headDir;
	private double bodyDir;

}
