package entities.solid.immovable;

import entities.World;
import entities.solid.Solid;

public abstract class Immovablesolid extends Solid{
    

    public Immovablesolid(int x, int y){
        super(x, y);

        mass = 0;
        inertialFactor = 1.1f;
        falling = false;

    }

    public void handleParticle(int x, int y, World world){
        
        // Behandles foreløpig ikke 

    }

    public boolean action(int x0, int y0, int x1, int y1, boolean firstAction, boolean lastAction, World world){
        return true;
    }

}