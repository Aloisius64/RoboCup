package robocup.utility;

import java.util.ArrayList;

/**@class Field
 * 
 * This creates an ArrayList that holds all the coordinates for the fixed 
 * points on the field. As the orientation of the axes depends on the side 
 * of the field the starts on, there are two sets of coordinates, each with 
 * opposite signs. 
 * 
 * @author Grant Hays
 *
 */
public class Field {

	public static final int NO_RIGHT_POST = 20;
	public static final int NO_LEFT_POST = 21;
	public static final int BOTH_VISIBLE = 22;
	
	// The Array list that contains all the positions
	public ArrayList<Position> posList = new ArrayList<Position>();

	/**
	 * Field constructor
	 * @param side	The side of the field the player's team starts on
	 * @pre The side needs to be parsed from the server's (init) message and 
	 * passed as the argument
	 * @post A new Field will be created with access to an array list of all 
	 * the field's fixed points
	 * 
	 */
	public Field(String side) {

		// The coordinates for the team starting on the left side
		if(side.compareTo("l") == 0) {
			posList.add(new Position("ftl50", -50, -39));
			posList.add(new Position("ftl40", -40, -39));
			posList.add(new Position("ftl30",-30, -39));
			posList.add(new Position("ftl20", -20, -39));
			posList.add(new Position("ftl10", -10, -39));
			posList.add(new Position("ft0", 0, -39));
			posList.add(new Position("ftr10", 10, -39));
			posList.add(new Position("ftr20", 20, -39));
			posList.add(new Position("ftr30", 30, -39));
			posList.add(new Position("ftr40", 40, -39));
			posList.add(new Position("ftr50", 50, -39));

			posList.add(new Position("frt30", 57.5, -30));
			posList.add(new Position("frt20", 57.5, -20));
			posList.add(new Position("frt10", 57.5, -10));
			posList.add(new Position("fr0", 57.5, 0));
			posList.add(new Position("frb10", 57.5, 10));
			posList.add(new Position("frb20", 57.5, 20));
			posList.add(new Position("frb30", 57.5, 30));

			posList.add(new Position("fbl50", -50, 39));
			posList.add(new Position("fbl40", -40, 39));
			posList.add(new Position("fbl30", -30, 39));
			posList.add(new Position("fbl20", -20, 39));
			posList.add(new Position("fbl10", -10, 39));
			posList.add(new Position("fb0", 0, 39));
			posList.add(new Position("fbr10", 10, 39));
			posList.add(new Position("fbr20", 20, 39));
			posList.add(new Position("fbr30", 30, 39));
			posList.add(new Position("fbr40", 40, 39));
			posList.add(new Position("fbr50", 50, 39));

			posList.add(new Position("flt30", -57.5, -30));
			posList.add(new Position("flt20", -57.5, -20));//fixed to negative
			posList.add(new Position("flt10", -57.5, -10));
			posList.add(new Position("fl0", -57.5, 0));
			posList.add(new Position("flb10", -57.5, 10));
			posList.add(new Position("flb20", -57.5, 20));
			posList.add(new Position("flb30", -57.5, 30));


			posList.add(new Position("flt", -52.5, -34));
			posList.add(new Position("fct", 0, -34));
			posList.add(new Position("frt", 52.5, -34));
			posList.add(new Position("flb", -52.5, 34));
			posList.add(new Position("fcb", 0, 34));
			posList.add(new Position("frb", 52.5, 34));

			posList.add(new Position("fplt", -36, -20.16));
			posList.add(new Position("fplc", -36, 0));
			posList.add(new Position("fplb", -36, 20.16));
			posList.add(new Position("fglt", -52.5, -7.01));
			posList.add(new Position("fglb", -52.5, 7.01));

			posList.add(new Position("fprt", 36, -20.16));
			posList.add(new Position("fprc", 36, 0));
			posList.add(new Position("fprb", 36, 20.16));
			posList.add(new Position("fgrt", 52.5, -7.01));
			posList.add(new Position("fgrb", 52.5, 7.01));

			posList.add(new Position("fc", 0, 0));

			posList.add(new Position("gl", -52.5, 0));
			posList.add(new Position("gr", 52.5, 0));

		} else {
			posList.add(new Position("ftl50", 50, 39));
			posList.add(new Position("ftl40", 40, 39));
			posList.add(new Position("ftl30", 30, 39));
			posList.add(new Position("ftl20", 20, 39));
			posList.add(new Position("ftl10", 10, 39));
			posList.add(new Position("ft0", 0, 39));
			posList.add(new Position("ftr10", -10, 39));
			posList.add(new Position("ftr20", -20, 39));
			posList.add(new Position("ftr30", -30, 39));
			posList.add(new Position("ftr40", -40, 39));
			posList.add(new Position("ftr50", -50, 39));

			posList.add(new Position("frt30", -57.5, 30));
			posList.add(new Position("frt20", -57.5, 20));
			posList.add(new Position("frt10", -57.5, 10));
			posList.add(new Position("fr0", -57.5, 0));
			posList.add(new Position("frb10", -57.5, -10));
			posList.add(new Position("frb20", -57.5, -20));
			posList.add(new Position("frb30", -57.5, -30));

			posList.add(new Position("fbl50", 50, -39));
			posList.add(new Position("fbl40", 40, -39));
			posList.add(new Position("fbl30", 30, -39));
			posList.add(new Position("fbl20", 20, -39));
			posList.add(new Position("fbl10", 10, -39));
			posList.add(new Position("fb0", 0, -39));
			posList.add(new Position("fbr10", -10, -39));
			posList.add(new Position("fbr20", -20, -39));
			posList.add(new Position("fbr30", -30, -39));
			posList.add(new Position("fbr40", -40, -39));
			posList.add(new Position("fbr50", -50, -39));

			posList.add(new Position("flt30", 57.5, 30));
			posList.add(new Position("flt20", 57.5, 20));//fixed to negative
			posList.add(new Position("flt10", 57.5, 10));
			posList.add(new Position("fl0", 57.5, 0));
			posList.add(new Position("flb10", 57.5, -10));
			posList.add(new Position("flb20", 57.5, -20));
			posList.add(new Position("flb30", 57.5, -30));


			posList.add(new Position("flt", 52.5, 34));
			posList.add(new Position("fct", 0, 34));
			posList.add(new Position("frt", -52.5, 34));
			posList.add(new Position("flb", 52.5, -34));
			posList.add(new Position("fcb", 0, -34));
			posList.add(new Position("frb", -52.5, -34));

			posList.add(new Position("fplt", 36, 20.16));
			posList.add(new Position("fplc", 36, 0));
			posList.add(new Position("fplb", 36, -20.16));
			posList.add(new Position("fglt", 52.5, 7.01));
			posList.add(new Position("fglb", 52.5, -7.01));

			posList.add(new Position("fprt", -36, 20.16));
			posList.add(new Position("fprc", -36, 0));
			posList.add(new Position("fprb", -36, -20.16));
			posList.add(new Position("fgrt", -52.5, 7.01));
			posList.add(new Position("fgrb", -52.5, -7.01));

			posList.add(new Position("fc", 0, 0));

			posList.add(new Position("gl", 52.5, 0));
			posList.add(new Position("gr", -52.5, 0));
		}

	}

}
