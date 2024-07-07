package entities.solid.movable;

import entities.World;
import entities.Element;
import entities.solid.Solid;
import math.Vector;
import entities.liquid.Liquid;

public abstract class Movablesolid extends Solid {
    
    public boolean action(int x0, int y0, int x1, int y1, World world){
        Element target = world.get(x1, y1);

        // Hvis plassen er tom        
        if(target == null){

            world.moveFromTo(x0, y0, x1, y1);
            falling = true;

            return false;
            
        }

        // Hvis plassen er en væskepartikkel
        else if(target instanceof Liquid){

            // Bytt plass

        }

        // hvis plassen er en solid partikkel
        else if(target instanceof Solid){
            
            if(falling){
                
                // Konserverer noe verikal hastighet til horisontal
                velocityVector.set(getPostCollisionVector());

                // Sjekk den diagonale naboen
                Vector normalizedVector = velocityVector.normalize();       // Normaliser vektoren
                int diagonalX = (int) Math.round(normalizedVector.x);       // Finner rettningen x & y
                int diagonalY = (int) Math.round(normalizedVector.y);       // peker til for å finne naboen
            
                if(!world.isWithinBounds(x1 + diagonalX, y1 + diagonalY)) return true;

                boolean stopped = action(x0, y0, x1 + diagonalX, y1 + diagonalY, world);
                if(!stopped){
                    return false;
                }

                return true;

            }

        }

        return true;

    }

    // Konverterer vertikal hastighet til horisontal
    // Returnerer en ny vektor  
    private Vector getPostCollisionVector(){

        float newVerticalVelocity = velocityVector.y * (float) Math.random();
        
        int direction = getDirection(velocityVector.x);
        float conservedVelocity = (velocityVector.y - newVerticalVelocity) * direction;

        return new Vector(conservedVelocity, newVerticalVelocity/2);

    }

    // Returnerer en rettningsfaktor
    private int getDirection(float velocity){

        if(velocity > 0) return 1;
        else if(velocity < 0) return -1;
        else return (Math.random() > 0.5) ? 1 : -1;

    }

}
