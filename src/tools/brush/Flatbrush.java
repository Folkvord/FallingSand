package tools.brush;

import entities.World;
import java.awt.Color;
import java.awt.Graphics;
import tools.ParticleID;
import window.Board;

public class Flatbrush extends Brush {
    
    public Flatbrush(Board board, World world){

        super(board, world);

    }
    
    public Flatbrush(Board board, World world, int radius, ParticleID particleID){

        super(board, world, radius, particleID);
    }

    public void paintParticles(int x, int y){

        if(radius == 1){
            
            spawnCorrectParticleType(x, y, particleID);
            return;

        }

        for(int i = -radius/2; i <= radius/2; i++){

            spawnCorrectParticleType(x+i, y, particleID);
            
        }
        
    }

    public void drawMarker(Graphics g, int x, int y){
        
        if(markerDisabled){
            return;
        }
        
        int width = radius % 10 == 0 ? radius*world.PARTICLEDIMENSION + world.PARTICLEDIMENSION : radius*world.PARTICLEDIMENSION;

        x = (x-radius/2)*world.PARTICLEDIMENSION;
        y = y*world.PARTICLEDIMENSION;

        g.setColor(Color.white);
        g.drawRect(x, y, width, world.PARTICLEDIMENSION);

    }

}
