package entities.solid.movable;

import math.Vector;
import entities.World;
import entities.Element;
import entities.solid.Solid;
import entities.liquid.Liquid;

public abstract class Movablesolid extends Solid {
    
    
    public Movablesolid(int x, int y){
        super(x, y);
    }


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
            
            if(lastAction){     // Kan trengs en fiks idk, kan hende det fikser seg selv senere LOL
                moveElementTo(x0, y0, world);
                return true;
            }

            // Konserver energi
            
            velocityVector.y = target.velocityVector.y;
            velocityVector.x *= frictionFactor * target.frictionFactor;
            
            Vector normalizedVector = velocityVector.copy().normalize();
            int directionX = getDirectionOrRandom(normalizedVector.x);
            int directionY = getDirection(normalizedVector.y);

            if(world.isWithinBounds(x0 + directionX, y0 + directionY)){
                boolean stopped = action(x0, y0, x0 + directionX, y0 + directionY, firstAction, true, world);
                if(!stopped){
                    falling = true;
                    return true;
                }
                
            }

            if(world.isWithinBounds(x0 + directionX, y1)){
                boolean stopped = action(x0, y0, x0 + directionX, y0, firstAction, true, world);
                if(!stopped){
                    falling = true;
                    return true;
                }
            }

            falling = false;
            return true;
        }

        return true;

    }

    // Om partikkelen faller, kan den gi en tilfeldig rettning
    // Om den glir gir den en bestemt rettning
    private int getDirectionOrRandom(float velocity){

        if(falling){

            if(velocity == 0){
                return (Math.random() > 0.5) ? 1 : -1;
            }
            else{
                return (velocity > 0) ? 1 : -1;
            }


        }

        if(velocity > 0) return 1;
        else if(velocity < 0) return -1;
        else return 0;

    }

    private int getDirection(float velocity){

        if(velocity > 0) return 1;
        else if(velocity < 0) return -1;
        else return -1;

    }

}
