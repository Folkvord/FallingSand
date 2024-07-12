package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Sand extends Movablesolid {


    public Sand(){

        frictionFactor = 0.2f;
        inertialFactor = 0.1f;
        mass = 1.6f;

        colour = (new Random().nextBoolean()) ? Color.decode("#ebc036") : Color.decode("#cca72f");

    }


}
