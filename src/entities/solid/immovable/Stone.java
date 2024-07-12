package entities.solid.immovable;

import java.awt.Color;

public class Stone extends Immovablesolid {
    
    public Stone(int x, int y){
        super(x, y);
        
        frictionFactor = 0.5f;
        colour = getColor();

    }

    private Color getColor(){
        return Color.decode("#636363");
    }

}
