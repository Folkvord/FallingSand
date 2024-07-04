package entities.solid.immovable;

import java.awt.Color;

public class Stone extends Immovablesolid {
    
    public Stone(){

        colour = getColor();

    }

    private Color getColor(){
        return Color.decode("#636363");
    }

}
