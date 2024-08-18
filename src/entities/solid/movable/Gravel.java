package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Gravel extends Movablesolid {
    

    public Gravel(int x, int y){
        super(x, y);

        frictionFactor = 0.1f;
        inertialFactor = 0.3f;
        mass = 1.6f;

        colour = (new Random().nextBoolean()) ? Color.decode("#4f4f4f") : Color.decode("#424242");

    }


}
