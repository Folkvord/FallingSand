package tools.brush;

import entities.World;
import java.awt.Graphics;
import tools.ParticleID;
import tools.Tool;
import window.Board;

public abstract class Brush extends Tool {

    public boolean markerDisabled = false;

    protected int radius = 1;


    public Brush(Board board, World world){

        super(board, world);

    }


    public Brush(Board board, World world, int radius, ParticleID particleID){

        super(board, world);

        this.radius = radius;
        this.particleID = particleID;

    }


    // Metoden som maler partikkelene til verdenen
    public abstract void paintParticles(int x, int y);

    // Metoden som lar board vise markøren på skjermen
    public abstract void drawMarker(Graphics g, int x, int y);


    // Forteller børsten hvilke koordinater den skal male mønstret sitt ved
    // Squarebrushen f.eks, tegner en firkant rundt alle koordinater vektoren traverserer 
    public void paintVector(int x0, int y0, int x1, int y1){

        if(x0 == x1 && y0 == y1){
            paintParticles(x1, y1);
            return;
        }

        int xDiff = x0 - x1;
        int yDiff = y0 - y1;

        boolean xDiffIsLarger = Math.abs(xDiff) > Math.abs(yDiff);

        int xMod = xDiff < 0 ? 1 : -1;
        int yMod = yDiff < 0 ? 1 : -1;

        int longestSide = Math.max(Math.abs(xDiff), Math.abs(yDiff));
        int shortestSide = Math.min(Math.abs(xDiff), Math.abs(yDiff));

        float a = (longestSide == 0 || shortestSide == 0) ? 0 : ((float) (shortestSide)/(longestSide));

        int shortestSideIncrease;
        int xIncrease, yIncrease;
        int x, y;
        for(int i = 1; i <= longestSide; i++){

            shortestSideIncrease = Math.round(i * a);

            if(xDiffIsLarger){
                xIncrease = i;
                yIncrease = shortestSideIncrease;
            }
            else{
                xIncrease = shortestSideIncrease;
                yIncrease = i;
            }

            x = x0 + (xIncrease * xMod);
            y = y0 + (yIncrease * yMod);

            if(world.isWithinBounds(x, y)){
                paintParticles(x, y);
            }

        }

    }


    // Radius
    public int getRadius(){
        return radius;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void incrementRadiusByFive(){

        if(radius == 1){
            radius = 5;
            return;
        }

        radius += 5;

        if(radius > 25){
            radius = 1;
        }


    }


  

}
