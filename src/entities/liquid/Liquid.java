package entities.liquid;

import entities.Element;
import entities.World;

public abstract class Liquid extends Element {

    protected int viscosity;


    public Liquid(int x, int y){
        super(x, y);
    }

    
    public boolean action(int x0, int y0, int x1, int y1, boolean firstAction, boolean lastAction, World world){
        
        return true;
        
    }


}