package entities.solid.immovable;

import entities.Element;
import entities.World;
import entities.solid.Solid;

public abstract class Immovablesolid extends Solid{
    
    public void determineParticleAction(int x, int y, Element[][] grid){

        // Gjør ingenting

    }

    public boolean action(int x0, int y0, int x1, int y1, World world){
        return true;
    }

}