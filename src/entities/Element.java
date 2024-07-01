package entities;

import java.awt.Color;
import java.util.Random;

public abstract class Element {

    // Fysikkonstanter
    public static double g = 0.4;
    public static int terminalVelocity = 10005;

    public Color colour;

    // Fysikk
    protected int xVelocity = 0;
    protected int yVelocity = 1;

    protected int framesInAir = 1;


    // Skaper og traverserer vektoren til partikkelen
    // For hvert punkt på vektoren, handler partikkelen med det rundt seg
    public void handleParticle(int x, int y, World world){
        framesInAir++;
        
        int x0 = x; 
        int y0 = y; 
        
        int x1 = x + 0;
        int y1 = y + 4;

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
        int lastValidX = x;
        int lastValidY = y;
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

                boolean stopped = action(lastValidX, lastValidY, x, y, world);
                
                if(stopped){
                    framesInAir = 0;
                    break;
                }

            }


        }

        

    }

    // Sier hvordan en partikkel skal handle med miljøet rundt seg
    // particleX og -Y er koordinatene til partikkelen i fokus
    // neighborX og -Y er koordinatene til naboen partikkelen skal handle med
    public abstract boolean action(int particleX, int particleY, int neighborX, int neighborY, World world);


    public int calculateFallingVelocity(){
        
        int velocity = (int) (g*framesInAir);

        return (velocity > terminalVelocity) ? terminalVelocity : velocity;

    }


    // Scanner fartsvektoren til en partikkel for å finne nærmeste åpne plass
    // Skal kunne støtte 2D-bevegelse
    protected int[] findFurthestOpenPointOnVector(World world, int x0, int y0, int x1, int y1){
        int[] coordinates = new int[2];

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
        int x = x0, y = y0; 
        int lastX = x0, lastY = y0;
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

            lastX = x;
            lastY = y;
            x = x0 + (xIncrease * xMod);
            y = y0 + (yIncrease * yMod);

            if(world.pointIsOccupied(x, y)){        // Om det er en partikkel i veien

                coordinates[0] = lastX;
                coordinates[1] = lastY;         // Returnerer de siste koordinatene før kollisjonen
                
                return coordinates;
            
            }

        }

        coordinates[0] = x;         // Når koden hit, var det ingen partikkeler i veien:
        coordinates[1] = y;         // De siste koordinatene lengst unna returneres

        return coordinates;

    }

    // ---------------------------------------------------------------------------------------------------------------- //
    
    // Ser etter den nærmeste ledige plassen partikkelen kan falle til
    // Brukes når vi bare har 1D forflyttning
    protected void fall(int x, int y, World world){

        yVelocity = calculateFallingVelocity();

        int coordinatesDown = 1;
        for(int i = 2; i < yVelocity; i++){

            if(world.pointIsOccupied(x, y + i)){
                
               break;
            
            }
            else{
                coordinatesDown = i;
            }

        }

        world.switchParticles(x, y, x, y + coordinatesDown);
        framesInAir++;

    }

    protected void conserveKineticEnergy(int x, int y, World world){

        boolean direction = new Random().nextBoolean();
        
        int yVel = yVelocity;
        int xVel = direction ? yVel : -yVel;

        

        world.switchParticles(x, y, x + xVel, y);

    }
  

    // ---------------------------------------------------------------------------------------------------------------- //

}
