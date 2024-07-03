package entities;

import java.awt.Color;

import math.Vector;

public abstract class Element {

    // Fysikkonstanter
    protected static Vector g = new Vector(0, 0.4);

    // Farge
    public Color colour;

    // Fysikkvariabler
    protected Vector velocityVector = new Vector(0, 0);
    protected boolean falling = true;
    protected int displacementFromVector = 0;   // Sier i hvilken rettning fra vektoren partikkelen har dirvergert


    // Skaper og traverserer vektoren til partikkelen
    // For hvert punkt på vektoren, handler partikkelen med det rundt seg
    public void handleParticle(int x0, int y0, World world){

        if(falling){
            velocityVector.add(g);
        }

        int x1 = (int) (x0 + velocityVector.x);
        int y1 = (int) (y0 + velocityVector.y);

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
        int lastValidX;
        int lastValidY;
        int x = x0, y = y0;
        boolean stopped = true;
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

            lastValidX = x + displacementFromVector;
            lastValidY = y;

            x = x0 + (xIncrease * xMod);
            y = y0 + (yIncrease * yMod);

            if(world.isWithinBounds(x, y)){

                stopped = action(lastValidX, lastValidY, x, y, world);
                
                if(stopped){
                    falling = false;
                    velocityVector.set(0, 0);

                    break;
                }

            }

        }

        displacementFromVector = 0;

    }

    // Sier hvordan en partikkel skal handle med miljøet rundt seg
    // particleX og -Y er koordinatene til partikkelen i fokus
    // neighborX og -Y er koordinatene til naboen partikkelen skal handle med
    public abstract boolean action(int particleX, int particleY, int neighborX, int neighborY, World world);


    // ---------------------------------------------------------------------------------------------------------------- //
    
    // Ser etter den nærmeste ledige plassen partikkelen kan falle til
    // Brukes når vi bare har 1D forflyttning
    protected void fall(int x0, int y0, int x1, int y1, World world){

        world.moveFromTo(x0, y0, x1, y1);

    }


  

    // ---------------------------------------------------------------------------------------------------------------- //

}
