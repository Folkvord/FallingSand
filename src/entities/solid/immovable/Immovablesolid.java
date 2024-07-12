package entities.solid.immovable;

import entities.World;
import entities.solid.Solid;

public abstract class Immovablesolid extends Solid{
    

    public Immovablesolid(){
        mass = 0;
        inertialFactor = 1.1f;
    }

    

    public boolean action(int x0, int y0, int x1, int y1, int originX, int originY, boolean firstAction, boolean lastAction, World world){
        return true;
    }

}