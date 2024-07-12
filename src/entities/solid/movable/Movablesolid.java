package entities.solid.movable;

import entities.World;
import entities.Element;
import entities.solid.Solid;
import math.Vector;
import entities.liquid.Liquid;

public abstract class Movablesolid extends Solid {
    
    
    public Movablesolid(int x, int y){
        super(x, y);
    }


    public boolean action(int x0, int y0, int x1, int y1, int originX, int originY, boolean firstAction, boolean lastAction, World world){
        Element target = world.get(x1, y1);

        // Hvis plassen er tom
        if(target == null){
                
           if(lastAction){
                moveElementTo(x1, y1, world);
                setNeighborsToFalling(x1, y1, world);
            }

            falling = true;

            return false;
            
        }

        // Hvis plassen har en væskepartikkel
        else if(target instanceof Liquid){

            // Bytt plass

        }

        // hvis plassen har en solid partikkel
        else if(target instanceof Solid){

                        

            return true;
        }

        return true;

    }

}
