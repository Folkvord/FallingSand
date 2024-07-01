package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Sand extends Movablesolid {
    
    public Sand(){

        colour = (new Random().nextBoolean()) ? Color.decode("#ebc036") : Color.decode("#cca72f");

    }

}
