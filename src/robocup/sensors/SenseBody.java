/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robocup.sensors;

import com.github.robocup_atan.atan.model.enums.ViewAngle;
import com.github.robocup_atan.atan.model.enums.ViewQuality;

/**
 *
 * @author aloisius
 */
public class SenseBody {

    private final ViewQuality viewQuality;
    private final ViewAngle viewAngle;
    private final double stamina;
    private final double unknown;
    private final double effort;
    private final double speedAmount;
    private final double speedDirection;
    private final double headAngle;
    private final int kickCount;
    private final int dashCount;
    private final int turnCount;
    private final int sayCount;
    private final int turnNeckCount;
    private final int catchCount;
    private final int moveCount;
    private final int changeViewCount;

    public SenseBody(ViewQuality viewQuality, ViewAngle viewAngle, double stamina, double unknown, double effort, double speedAmount, double speedDirection, double headAngle, int kickCount, int dashCount, int turnCount, int sayCount, int turnNeckCount, int catchCount, int moveCount, int changeViewCount) {
        this.viewQuality = viewQuality;
        this.viewAngle = viewAngle;
        this.stamina = stamina;
        this.unknown = unknown;
        this.effort = effort;
        this.speedAmount = speedAmount;
        this.speedDirection = speedDirection;
        this.headAngle = headAngle;
        this.kickCount = kickCount;
        this.dashCount = dashCount;
        this.turnCount = turnCount;
        this.sayCount = sayCount;
        this.turnNeckCount = turnNeckCount;
        this.catchCount = catchCount;
        this.moveCount = moveCount;
        this.changeViewCount = changeViewCount;
    }

    public ViewQuality getViewQuality() {
        return viewQuality;
    }

    public ViewAngle getViewAngle() {
        return viewAngle;
    }

    public double getStamina() {
        return stamina;
    }

    public double getUnknown() {
        return unknown;
    }

    public double getEffort() {
        return effort;
    }

    public double getSpeedAmount() {
        return speedAmount;
    }

    public double getSpeedDirection() {
        return speedDirection;
    }

    public double getHeadAngle() {
        return headAngle;
    }

    public int getKickCount() {
        return kickCount;
    }

    public int getDashCount() {
        return dashCount;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public int getSayCount() {
        return sayCount;
    }

    public int getTurnNeckCount() {
        return turnNeckCount;
    }

    public int getCatchCount() {
        return catchCount;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getChangeViewCount() {
        return changeViewCount;
    }

}
