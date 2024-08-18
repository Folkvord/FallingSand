package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Dirt extends Movablesolid {
    

    public Dirt(int x, int y){
        super(x, y);
        
        frictionFactor = 0.2f;
        inertialFactor = 0.9f;
        mass = 0.5f;

        colour = (new Random().nextBoolean()) ? Color.decode("#4a2511") : Color.decode("#3b1d0d");

    }


}
