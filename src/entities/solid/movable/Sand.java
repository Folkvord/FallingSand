package entities.solid.movable;

import java.awt.Color;
import java.util.Random;

public class Sand extends Movablesolid {


    public Sand(int x, int y){
        super(x, y);
        
        frictionFactor = 0.2f;
        inertialFactor = 0.1f;
        mass = 1.6f;

        // Debug
        //if(Math.random() > .75) colour = Color.decode("#a83294");
        //else if(Math.random() > .5) colour = Color.decode("#3238a8");
        //else if(Math.random() > .25) colour = Color.decode("#b30000");
        //else colour = Color.decode("#ffffff");

        colour = (new Random().nextBoolean()) ? Color.decode("#ebc036") : Color.decode("#cca72f");

    }


}
