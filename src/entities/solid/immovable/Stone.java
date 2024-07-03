package entities.solid.immovable;

import java.awt.Color;
import java.util.Random;

public class Stone extends Immovablesolid {
    
    public Stone(){

        colour = getColor();

    }

    private Color getColor(){
        double darkest = 0.3;
        double middle = 0.5;
        double lightest = 0.8;
        double chance = new Random().nextDouble(2);

        return Color.decode("#636363");
/*
        if(chance < darkest){
            return Color.decode("#808080");
        }
        else if(chance < middle){
            return Color.decode("#636363");
        }
        else if(chance < lightest){
            return Color.decode("#424242");
        }

        return null;
*/
    }

}
