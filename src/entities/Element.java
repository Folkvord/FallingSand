package entities;

import java.awt.Color;

import math.Vector;

public abstract class Element {

    // Fysikkonstanter
    protected static Vector g = new Vector(0, 0.15f);

    // Farge
    public Color colour;

    // Koordinater
    private int x;
    private int y;

    // Fysikkvariabler
    public    Vector    velocityVector = new Vector(0, 0);
    protected boolean   falling = true;                             // Om partikkelen faller
    protected float     frictionFactor;                             // Friksjonsfaktoren (ganges med en annen frik.faktor og med x-hastigheten)
    protected float     inertialFactor;                             // Sannsynligheten for at en partikkel får denne til å falle igjen
    protected float     mass;



    // Skaper og traverserer vektoren til partikkelen
    // For hvert punkt på vektoren, handler partikkelen med det rundt seg
    public void handleParticle(int x0, int y0, World world){

        if(falling){
            velocityVector.add(g);      // Tyngdekraft
            velocityVector.x *= 0.9;    // Luftmotstand
        }
        else{
            if(!world.pointIsOccupied(x0, y0+1)){
                action(x0, y0, x0, y0+1, x0, y0, true, false, world);
                return; 
            }
        }


        // ----- | Skaper vektoren | ----- //
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

        // ----- | Itererer gjennom hvert punkt på vektoren | ----- //
        int shortestSideIncrease;
        int xIncrease, yIncrease;
        int lastValidX = x0;
        int lastValidY = y0;
        int targetX, targetY;
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

            targetX = x0 + (xIncrease * xMod);
            targetY = y0 + (yIncrease * yMod);

            if(world.isWithinBounds(targetX, targetY)){

                stopped = action(lastValidX, lastValidY, targetX, targetY, x0, y0, i == 1, i == longestSide, world);

                if(stopped){
                    break;
                }

                lastValidX = targetX;
                lastValidY = targetY;

            }
            else{
                falling = false;
            }

        }

    }

    // Sier hvordan en partikkel skal handle med miljøet rundt seg
    // lastValidX og -Y er koordinatene der partikkelen sist var
    // targetX og -Y er koordinatene til plassen der partikkelen har lyst å gå
    // Origin er punktet der partikkelen starta
    public abstract boolean action(int lastValidX, int lastValidY, int targetX, int targetY, int originX, int originY, boolean firstAction, boolean lastAction, World world);

    protected void midAirCollision(int x0, int y0, int x1, int y1, World world){

        Element particle1 = world.get(x0, y0);
        Element particle2 = world.get(x1, y1);

        float m1 = particle1.mass;
        float m2 = particle2.mass;
        float v11 = particle1.velocityVector.y;
        float v21 = particle2.velocityVector.y;

        if(m1 == m2){
            particle1.velocityVector.y = v21;
            particle2.velocityVector.y = v11;
            return;
        }

        float mDiff = Math.abs(m1 - m2);
        boolean p1Collides = v11 > v21;

        float v12 = p1Collides ? v11*mDiff : v11 + v11*mDiff;
        float v22 = p1Collides ? v21 + v21*mDiff : v21*mDiff;

        particle1.velocityVector.y = v22;
        particle2.velocityVector.y = v12;

    }

    // Skal sette nabopartikkelene til fallende
    // Dette unngår unaturlige tårn 
    protected void setNeighborsToFalling(int x, int y, World world){
        Element particle;

        for(int y0 = -1; y0 <= 1; y0++){
            for(int x0 = -1; x0 <= 1; x0++){        

                if(!world.isWithinBounds(x + x0, y + y0)
                || (particle = world.get(x + x0, y + y0)) == null
                || particle.falling) continue;

                particle.falling = (Math.random() > particle.inertialFactor);
                particle.velocityVector.y = 1;


            }
        }

    }


    public int x(){
        return x;
    }
    
    public int y(){
        return y;
    }

    public boolean isFalling(){
        return falling;
    }

}
