/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.geometry;

/**
 *
 * @author aloisius
 */
public class SinCosT {

    private double sin;
    private double cos;
    
    public SinCosT(double angle) {        
        this.sin = GU.sin(angle);
        this.cos = GU.cos(angle);
    }
    
    public double getSin() {
        return sin;
    }
    
    public void setSin(double sin) {
        this.sin = sin;
    }
    
    public double getCos() {
        return cos;
    }
    
    public void setCos(double cos) {
        this.cos = cos;
    }
}
