package entities;

import java.awt.Color;

import math.Vector;

public abstract class Element {

    // Fysikkonstanter
    protected static Vector g = new Vector(0, 0.15f);

    public Color colour;
    public boolean moved = false;

    // Koordinater
    protected int x;    // Disse koordinatene endrer seg BARE i siste action
    protected int y;    // De brukes som en kommunikasjonslinje mellom action() og handleParticle() (retter opp om elementet dirvergerer fra vektoren)

    // Fysikkvariabler
    public    Vector    velocityVector = new Vector(0, 0);
    public    float     frictionFactor;                             // Friksjonsfaktoren (ganges med en annen frik.faktor og med x-hastigheten)
    public    float     inertialFactor;                             // Sannsynligheten for at en partikkel får denne til å falle igjen
    protected boolean   falling = true;                             // Om partikkelen faller
    protected float     mass;


    public Element(int x, int y){
        
        this.x = x;
        this.y = y;

    }


    // Skaper og traverserer vektoren til partikkelen
    // For hvert punkt på vektoren, handler partikkelen med det rundt seg
    public void handleParticle(int x0, int y0, World world){

        if(falling){
            velocityVector.add(g);      // Tyngdekraft
            velocityVector.x *= 0.9;    // Luftmotstand
        }
        else if(!world.pointIsOccupied(x0, y0 + 1)){
            falling = true;
            return;
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

            targetX = this.x + (xIncrease * xMod);
            targetY = this.y + (yIncrease * yMod);

            if(world.isWithinBounds(targetX, targetY)){

                stopped = action(lastValidX, lastValidY, targetX, targetY, i == 1, i == longestSide, world);

                if(stopped){
                    break;
                }
                
                lastValidX = targetX;
                lastValidY = targetY;

            }
            else{
                falling = false;
                moveElementTo(lastValidX, lastValidY, world);
                velocityVector.y = 0;
            }

        }

    }

    // Sier hvordan en partikkel skal handle med miljøet rundt seg
    // lastValidX og -Y er koordinatene der partikkelen sist var
    // targetX og -Y er koordinatene til plassen der partikkelen har lyst å gå
    // Origin er punktet der partikkelen starta
    public abstract boolean action(int lastValidX, int lastValidY, int targetX, int targetY, boolean firstAction, boolean lastAction, World world);

    protected void midAirCollision(Element collidingElement, World world){

        Element particle1 = this;
        Element particle2 = collidingElement;

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

            }
        }

    }

// ---------------------| ELEMENTKOORDINATHANDLING |--------------------- //

    public int x(){
        return x;
    }
    
    public int y(){
        return y;
    }

    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void swapCoordiantes(Element toSwap, World world){

        if(this == toSwap) return;
        
        int toSwapX = toSwap.x;
        int toSwapY = toSwap.y;

        toSwap.setCoordinates(this.x, this.y);
        this.setCoordinates(toSwapX, toSwapY);

        world.swapParticles(this.x, this.y, toSwap.x, toSwap.y);

    }

    public void moveElementTo(int x, int y, World world){

        if(this.x == x && this.y == y) return;

        world.moveFromTo(this.x, this.y, x, y);
        setCoordinates(x, y);

    }

    public boolean isFalling(){
        return falling;
    }

    public String toString(){
        return "("+x+", "+y+")";
    }

}
