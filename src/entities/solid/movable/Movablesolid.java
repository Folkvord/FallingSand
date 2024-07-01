package entities.solid.movable;

import entities.Element;
import entities.World;
import entities.liquid.Liquid;
import entities.solid.Solid;

public abstract class Movablesolid extends Solid {
    
    public boolean action(int x0, int y0, int x1, int y1, World world){
        
        Element target = world.get(x1, y1);

        // Hvis plassen under er tom        
        if(target == null){
            
            //fall(x, y, world);
            world.switchParticles(x0, y0, x1, y1);
            return false;

        }

        // Hvis plassen under er en væskepartikkel
        else if(target instanceof Liquid){

            // Bytt plass

        }

        // hvis plassen under er en solid partikkel
        else if(target instanceof Solid){

            if(framesInAir > 0){

                xVelocity = 4;

            }

            if(!world.pointIsOccupied(x1-1, y1)){
                world.moveFromTo(x0, y0, x1-1, y1);
            }
            else if(!world.pointIsOccupied(x1+1, y1)){
                world.moveFromTo(x0, y0, x1+1, y1);
            }

            return false;

        }

        return true;

    }

    


    protected void goDiagonally(int x, int y, World world){

        if(!world.pointIsOccupied(x-1, y+1)){

            world.moveFromTo(x, y, x-1, y+1);

        }
        else if(!world.pointIsOccupied(x+1, y+1)){

            world.moveFromTo(x, y, x+1, y+1);

        }

    }

}
