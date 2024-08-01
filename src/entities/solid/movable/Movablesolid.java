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
            
            if(lastAction){
                moveElementTo(x0, y0, world);
                return true;
            }
            
            if(falling){
            //    velocityVector.x = (Math.random() > 0.5) ? velocityVector.y/2 : -velocityVector.y/2;
            }

            Vector normalizedVector = velocityVector.copy().normalize();
            int directionX = getDirection(normalizedVector.x);
            int directionY = 1;

            if(world.isWithinBounds(x0 + directionX, y0 + directionY)){
                boolean stopped = action(x0, y0, x0 + directionX, y0 + directionY, firstAction, true, world);
                if(!stopped){
                    falling = true;
                    return true;
                }
                
                // Mulig å sjekke andre siden
                
            }
            
            
            falling = false;
            return true;

            


            //if(world.isWithinBounds(x0 + directionX, y1)){
            //    boolean stopped = action(x0, y0, x0 + directionX, y0, firstAction, true, world);
            //    if(!stopped){
            //        falling = true;
            //        return true;
            //    }
            //    velocityVector.x *= -1;
            //}
            
            
        }

        return true;

    }

    protected float getAverageVelocity(float velocity, float otherVelocity){

        float averageVelocity = (velocity + otherVelocity)/2;

        return (averageVelocity > 0) ? averageVelocity : 2;

    }

    protected int getDirection(float velocity){

        if(velocity > 0.1f){
            return 1;
        }
        else if(velocity < -0.1f){
            return -1;
        }
        else{
            return (Math.random() < 0.5) ? 1 : -1;
        }

    }

}
