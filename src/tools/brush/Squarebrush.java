package tools.brush;

import entities.World;
import java.awt.Color;
import java.awt.Graphics;
import tools.ParticleID;
import window.Board;

public class Squarebrush extends Brush {
    
    public Squarebrush(Board board, World world){

        super(board, world);

    }

    public Squarebrush(Board board, World world, int radius, ParticleID particleID){

        super(board, world, radius, particleID);

    }

    public void paintParticles(int x, int y){

        for(int i = -radius/2; i <= radius/2; i++){
            for(int j = -radius/2; j <= radius/2; j++){

    	        spawnCorrectParticleType(x+j, y+i, particleID);                

            }
        }

    }

    public void drawMarker(Graphics g, int x, int y){
        
        if(markerDisabled){
            return;
        }

        int width = radius % 10 == 0 ? (radius * world.PARTICLEDIMENSION) + world.PARTICLEDIMENSION : radius * world.PARTICLEDIMENSION;
        int height = radius % 10 == 0 ? (radius * world.PARTICLEDIMENSION) + world.PARTICLEDIMENSION : radius * world.PARTICLEDIMENSION;

        x = (x - radius/2) * world.PARTICLEDIMENSION;
        y = (y - radius/2) * world.PARTICLEDIMENSION;

        g.setColor(Color.white);
        g.drawRect(x, y, width, height);

    }

}
