package entities.solid.movable;

import entities.Element;
import entities.World;
import entities.liquid.Liquid;
import entities.solid.Solid;
import math.Vector;

public abstract class Movablesolid extends Solid {
    
    
    public Movablesolid(int x, int y){
        super(x, y);
    }

    @Override
    public boolean action(int x0, int y0, int x1, int y1, boolean firstAction, boolean lastAction, World world){
        Element target = world.get(x1, y1);

        // Hvis plassen er tom
        if(target == null){

            setNeighborsToFalling(x1, y1, world);
            if(lastAction){
                moveElementTo(x1, y1, world); 
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
            
            if(lastAction){
                moveElementTo(x0, y0, world);
                return true;
            }

            if(falling){    // Primitivt; oppdater
                int direction = (velocityVector.x > 0) ? 1 : -1;
                velocityVector.x = (velocityVector.y / 2) * direction;
            }

            Vector normalizedVector = velocityVector.copy().normalize();
            int directionX = getDirection(normalizedVector.x);
            int directionY = getDirection(normalizedVector.y);

            velocityVector.x *= (this.frictionFactor * target.frictionFactor);

            if(world.isWithinBounds(x0 + directionX, y0 + directionY)){
                boolean stopped = action(x0, y0, x0 + directionX, y0 + directionY, firstAction, true, world);
                if(!stopped){
                    falling = true;
                    return true;
                }

            }

            if(world.isWithinBounds(x0 + directionX, y0)){
                boolean stopped = action(x0, y0, x0 + directionX, y0, firstAction, true, world);
                if(stopped){
                    velocityVector.x = -velocityVector.x;
                }
                else{
                    falling = false;
                    return true;
                }

            }

            

            falling = false;
            moveElementTo(x0, y0, world);
            return true;

        }

        return true;

    }

    private int getDirection(float velocity){

        if(velocity > 0.1f){
            return 1;
        }
        else if(velocity < -0.1f){
            return -1;
        }
        else{
            return 0;
        }

    }

}
