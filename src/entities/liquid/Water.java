package entities.liquid;

import java.awt.Color;

public class Water extends Liquid {
    
    public Water(int x, int y){
        super(x, y);

        colour = Color.decode("#1f53a1");
        viscosity = 3;

    }

}
