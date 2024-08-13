package tools;

import entities.World;
import java.awt.Graphics;
import tools.brush.Brush;
import window.Board;

public class Sprinklerbrush extends Brush {


    public Sprinklerbrush(Board board, World world){
        
        super(board, world);

    }


    // Spawner inn en kran
    @Override
    public void paintParticles(int x, int y){

        

    }

    @Override
    public void drawMarker(Graphics g, int x, int y){

        g.drawOval(x, y, world.PARTICLEDIMENSION, world.PARTICLEDIMENSION);

    }

}
