package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Dirt extends Movablesolid {
    

    public Dirt(int x, int y){
        super(x, y);
        
        frictionFactor = 0.2f;
        inertialFactor = 0.5f;
        mass = 1.6f;

        colour = (new Random().nextBoolean()) ? Color.decode("#210c0b") : Color.decode("#26100f");

    }


}
