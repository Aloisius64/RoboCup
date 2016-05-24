/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.playerSeenData;

import com.github.robocup_atan.atan.model.enums.Flag;

/**
 *
 * @author aloisius
 */
public class SeenFlag {

	private final double distance;
	private final double direction;
	private final double distChange;
	private final double dirChange;
	private final double bodyFacingDirection;
	private final double headFacingDirection;

	public SeenFlag(double distance, double direction, double distChange, double dirChange,
			double bodyFacingDirection, double headFacingDirection) {
		this.distance = distance;
		this.direction = direction;
		this.distChange = distChange;
		this.dirChange = dirChange;
		this.bodyFacingDirection = bodyFacingDirection;
		this.headFacingDirection = headFacingDirection;
	}

	public double getDistance() {
		return distance;
	}

	public double getDirection() {
		return direction;
	}

	public double getDistChange() {
		return distChange;
	}

	public double getDirChange() {
		return dirChange;
	}

	public double getBodyFacingDirection() {
		return bodyFacingDirection;
	}

	public double getHeadFacingDirection() {
		return headFacingDirection;
	}

}
