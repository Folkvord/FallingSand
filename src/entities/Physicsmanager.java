package entities;

import java.util.Random;

import entities.liquid.Liquid;
import entities.solid.Solid;
import entities.solid.immovable.Immovablesolid;

/*
 *  Solides fysikk:
 * Faller ned med tyngdekraften
 * Kan skli ned bakker
 * 
 *  Væskenes fysikk:
 * Faller ned med tyngdekraften
 * Kan skli ned bakker
 * Kan også flyte til siden (aktivt)
 * 
 *  Gassens fysikk:
 * Fungerer som en kombinasjon mellom solide og væske partikkler
 * Stiger oppover istedenfor
 * 
 *  Flyting:
 * Vi skal anta at alle partikler er solide og at areal == 1.
 * Derfor kan vi si: massetetthet/areal = massen --> massetetthet = massen
 * Derfor trenger vi ikke en tetthet variabel, vi bruker bare massen
 * 
 */


import window.Board;

public class Physicsmanager {
    
    public Board board;

    // Konstanter
    public static double g = 0.8;
    public static int terminalVelocity = 10;

    // Tilfeldighetsgenerator
    public Random rand;

    // Verdensmatrisen
    public Element[][] grid;



    public Physicsmanager(Board board){

        this.board = board;
        this.rand = new Random();

    }

    public void initWorld(int x, int y){

        grid = new Element[y][x];

    }



    public void determineParticleType(int x, int y){

        Element entity = grid[y][x];

        if(entity instanceof Solid){
            determineSolidParticleAction(x, y);
            return;
        }
        else if(entity instanceof Liquid){
            determineLiquidParticleAction(x, y);
            return;
        }

    }

    // VÆSKE FYSIKK
    public void determineLiquidParticleAction(int x, int y){
        Liquid entity = (Liquid) grid[y][x];

        // Kalkulerer hastighet
        int velocity = calculateNewVelocity(entity.framesInAir);
        if(velocity > terminalVelocity){
            velocity = terminalVelocity;
        }

        // Sjekker om det er en partikkel under
        if(grid[y+1][x] == null && (y+1) <= board.grainY){
            
            entity.framesInAir++;
            letParticleFall(x, y, velocity);

            return;
        }

        // Sjekk om partikkelen kan skli
        if(grid[y+1][x-1] == null){
            letParticleSlideLeft(x, y, velocity);
            entity.framesInAir = 0; 
            return;
        }
        else if(grid[y+1][x+1] == null){
            letParticleSlideRight(x, y, velocity);
            entity.framesInAir = 0; 
            return;
        }


        // La partikkelen flyte
        if(rand.nextBoolean()){
            flowToLeft(x, y);
        }
        else{
            flowToRight(x, y);
        }

        entity.framesInAir = 0; 

    }


    // SOLID FYSIKK
    public void determineSolidParticleAction(int x, int y){
        Element entity = grid[y][x];

        // Hvis partikkelen ikke påvirkes av tyngekraft (Ingenting å gjøre)
        if(entity instanceof Immovablesolid){
            return;
        }
        
        // Kalkulerer hastighet
        int velocity = calculateNewVelocity(entity.framesInAir);
        if(velocity > terminalVelocity){
            velocity = terminalVelocity;
        }

        // Sjekker om det er en partikkel under
        if(grid[y+1][x] == null && (y+1) <= board.grainY){
            
            entity.framesInAir++;
            letParticleFall(x, y, velocity);

            return;
        }

        //slideParticle(x, y, velocity);        // Legacy

        // Bestem hvilken vei partikkelen skal skli
        if(grid[y+1][x-1] == null){
            letParticleSlideLeft(x, y, velocity);
        }
        else if(grid[y+1][x+1] == null){
            letParticleSlideRight(x, y, velocity);
        }

        entity.framesInAir = 0; 

    }


    // Lar partikkelen falle
    // Sjekker en hver posisjon mellom der partikkelen er og hvor den har lyst å gå
    // Partikkelen går nærmest den ønskede posisjonen
    public void letParticleFall(int x, int y, int velocity){
        int closestToWantedPosition = 1;

        for(int i=2; i<velocity; i++){

            if((y+i) >= board.grainY){
                break;
            }

            if(grid[y+i][x] == null){
                closestToWantedPosition = i;
            }
            else{
                break;
            }

        }

        moveParticleToEmptySpace(x, y, x, y+closestToWantedPosition);

    }

    // Får partikkelen til å skli ned en bakke
    // Sjekker en hver posisjon mellom der partikkelen er og hvor den har lyst å gå
    // Partikkelen går nærmest den ønskede posisjonen
    // MULIG BUG: kan skli utenfor et stup. Det kan se rart ut i at vi ikke har 2D hastighet i fritt fall
    public void slideParticle(int x, int y, int velocity){      // Denne versjonen fungerer litt rart men kult, lag en ny, men behold denne

        // Sklir til høyre
        for(int i=velocity; i>=1; i--){

            if(grid[y+i][x+i] == null){
                moveParticleToEmptySpace(x, y, x+i, y+i);   // Fant ledig plass
            }
        }

        // Sklir til venstre
        for(int i=velocity; i>=1; i--){

            if(grid[y+i][x-i] == null){
                moveParticleToEmptySpace(x, y, x-i, y+i);   // Fant ledig plass
            }
        }

    }

    // pibli
    // Denne og dens motpart dytter en partikkel til siden (IKKE ned) :)
    public void letParticleSlideLeft(int x, int y, int velocity){
        int closestToWantedPosition = 1;

        for(int i=2; i<velocity; i++){

            if((y+i) > board.grainY || (x-i) < 0){
                break;
            }

            if(grid[y+i][x-i] == null && grid[y+i+1][x-i] == null){
                closestToWantedPosition = i;
                break;
            }
            else if(grid[y+i][x-i] == null){
                closestToWantedPosition = i;
            }
            else{
                break;
            }

        }

        moveParticleToEmptySpace(x, y, x-closestToWantedPosition, y);

    }

    public void letParticleSlideRight(int x, int y, int velocity){
        int closestToWantedPosition = 1;

        for(int i=2; i<velocity; i++){

            if((y+i) > board.grainY || (x+i) < 0){
                break;
            }

            if(grid[y+i][x+i] == null && grid[y+i+1][x+i] == null){
                closestToWantedPosition = i;
                break;
            }
            else if(grid[y+i][x+i] == null){
                closestToWantedPosition = i;
            }
            else{
                break;
            }

        }

        moveParticleToEmptySpace(x, y, x+closestToWantedPosition, y);
    
    }

//  |--------------------------<| Væskefysikk metoder |>--------------------------|   //

    public void flowToRight(int x, int y){
        int closestToWantedPosition = 1;
        int viscosity = ((Liquid) grid[y][x]).viscosity;

        for(int i=2; i<viscosity; i++){

            if((x+i) > board.grainX){
                break;
            }

            if(grid[y][x+i] == null && grid[y+1][x+i] != null){
                closestToWantedPosition = i;
                break;
            }
            else if(grid[y][x+1] == null){
                closestToWantedPosition = i;
            }
            else{
                break;
            }

        }

        moveParticleToEmptySpace(x, y, x+closestToWantedPosition, y);

    }


    public void flowToLeft(int x, int y){
        int closestToWantedPosition = 1;
        int viscosity = ((Liquid) grid[y][x]).viscosity;

        for(int i=2; i<viscosity; i++){

            if((x-i) < 0){
                break;
            }

            if(grid[y][x-i] == null && grid[y+1][x-i] != null){
                closestToWantedPosition = i;
                break;
            }
            else if(grid[y][x-1] == null){
                closestToWantedPosition = i;
            }
            else{
                break;
            }

        }

        moveParticleToEmptySpace(x, y, x+closestToWantedPosition, y);

    }




//  |--------------------------<| Små hjelpe metoder |>--------------------------|   //

    public int calculateNewVelocity(int framesInAir){
        
        return (int) (g*framesInAir);

    }

    public void moveParticleToEmptySpace(int currentX, int currentY, int newX, int newY){

        // Skal forsikre at plassen er tom
        // Føles ut som en tullete måte å gjøre dette på; skal endres 
        if(grid[newY][newX] != null){
            return;
        }

        grid[newY][newX] = grid[currentY][currentX];
        grid[currentY][currentX] = null;

    }


//    |--------------------------<| Andre metoder |>--------------------------|    //

    // Sletter alle partikkler fra verdenen
    public void purgeWorld(){

        for(int y=0; y<grid.length; y++){
            for( int x=0; x<grid[0].length; x++){

                if(grid[y][x] != null){
                    grid[y][x] = null;
                }

            }
        }

    }


}
