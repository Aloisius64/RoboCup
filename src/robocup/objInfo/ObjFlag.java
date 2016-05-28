package robocup.objInfo;

/**
 * @class ObjBall
 * 
 * container for the flag ObjInfo,
 * 
 */
public class ObjFlag extends ObjInfo {

	public ObjFlag() {
		super("flag");
	}

	/**
	 * Constructor of flag with flag name
	 */
	public ObjFlag(String name) {
		super("flag");
		flagName = name;
	}

	/**
	 * The Flag Type getter
	 * 
	 * @return The type of flag depending on it's location:
	 * 				"b" - outer boundary
	 * 				"f" - goal post
	 * 				"p" - penalty box
	 * 				"c" - center of field
	 * 				"l" - border line
	 */
	public String getFlagType() {
		return flagType;
	}

	/**
	 * The Flag Type setter
	 */
	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	/**
	 * The Flag Name getter
	 * 
	 * @return The name of the flag, as given by the server but with no
	 * spaces (e.g. flt20 for boundary flag left, top, 20 yard line)
	 */
	public String getFlagName() {
		return flagName;
	}

	/**
	 * The Flag Name setter
	 */
	public void setFlagName(String name) {
		this.flagType = name;
	}

	/**
	 * The X position getter
	 * 
	 * @return Either "l" for left, "r" for right, or "c" for center
	 */
	public String getX_pos() {
		return x_pos;
	}

	/**
	 * The X position setter
	 */
	public void setX_pos(String x_pos) {
		this.x_pos = x_pos;
	}

	/**
	 * The Y position getter
	 * 
	 * @return Either "t" for top, "b" for bottom, or "c" for center
	 */
	public String getY_pos() {
		return y_pos;
	}

	/**
	 * The Y position setter
	 */
	public void setY_pos(String y_pos) {
		this.y_pos = y_pos;
	}

	/**
	 * The yard getter
	 * 
	 * @return the yard is a String of a number for boundaries
	 */
	public String getYard() {
		return yard;
	}

	/**
	 * The yard setter
	 */
	public void setYard(String yard) {
		this.yard = yard;
	}

	private String flagName;
	private String flagType;
	private String x_pos;
	private String y_pos;
	private String yard;
}
