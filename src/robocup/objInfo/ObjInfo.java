package robocup.objInfo;

/**
 * @file ObjInfo.java
 * 
 * The ObjInfo container
 * 
 * @author Grant Hays
 * @date 09/01/11
 * @version 1
 */

/**
 * @class ObjInfo
 * 
 * A container for items in the Player's vision
 */
public class ObjInfo {

	/**
	 * The Default constructor
	 */
	public ObjInfo() {
		
	}

	/**
	 * The ObjInfo constructor
	 * 
	 * This initializes all the variables to 0.0 and sets the name
	 * 
	 * @param name The type of ObjInfo, either ball, player, goal, line, or flag
	 */
	public ObjInfo(String name) {
		setObjName(name);
		setDistance(0.0);
		setDirection(0.0);
		setDistChng(0.0);
		setDirChng(0.0);
	}

	/**
	 * The ObjName getter
	 */
	public String getObjName() {
		return ObjName;
	}

	/**
	 * The ObjName setter
	 */
	public void setObjName(String name) {
		ObjName = name;
	}

	/**
	 * The side getter
	 */
	public String getSide() {
		return side;
	}

	/**
	 * The side setter
	 */
	public void setSide(String objSide) {
		this.side = objSide;
	}

	/**
	 * The distance getter
	 * 
	 * @return the approximate distance to the object
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * The distance setter
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * The direction getter
	 * 
	 * @return the approximate direction of ObjInfo 
	 */
	public double getDirection() {
		return direction;
	}

	/**
	 * The direction setter
	 */
	public void setDirection(double direction) {
		this.direction = direction;
	}

	/**
	 * The distance change getter
	 * 
	 * @return the approximate distance change (magnitude of
	 * velocity) of ObjInfo 
	 */
	public double getDistChng() {
		return distChng;
	}

	/**
	 * The distance change setter 
	 */
	public void setDistChng(double distChng) {
		this.distChng = distChng;
	}

	/**
	 * The direction change getter
	 * 
	 * @return the approximate direction change (direction of
	 * velocity) of ObjInfo 
	 */
	public double getDirChng() {
		return dirChng;
	}

	/**
	 * The distance change setter 
	 */
	public void setDirChng(double dirChng) {
		this.dirChng = dirChng;
	}


	private String ObjName;
	private String side;
	private double distance;
	private double direction;
	private double distChng;
	private double dirChng;

}
