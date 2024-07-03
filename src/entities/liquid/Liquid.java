package entities.liquid;

import entities.Element;
import entities.World;

public abstract class Liquid extends Element {

    protected int viscosity;

    public boolean action(int x0, int y0, int x1, int y1, World world){
        return true;
    }


}