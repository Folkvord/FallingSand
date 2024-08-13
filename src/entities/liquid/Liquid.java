package entities.liquid;

import entities.Element;
import entities.World;
import entities.solid.Solid;

public abstract class Liquid extends Element {

    protected int viscosity;


    public Liquid(int x, int y){
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

            

        }

        // hvis plassen har en solid partikkel
        else if(target instanceof Solid){          

            falling = false;
            return true;
       
        }

        return true;

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