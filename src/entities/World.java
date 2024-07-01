package entities;

import java.awt.Graphics;
import java.util.Random;

public class World {

    public final int PARTICLEDIMENSION;
    
    public final int PARTICLEX;
    public final int PARTICLEY;
    
    private boolean timeStopped = false;    // Om tiden står stille
    private boolean voidFloor = false;      // Sier om partikkler forsvinner når de treffer bunnen av verdnen   (IKKE FERDIG)

    private Element[][] grid;
    private int[] shuffeledIndexes;


    public World(){

        
        PARTICLEDIMENSION = 8;
        
        PARTICLEX = 1000/PARTICLEDIMENSION;
        PARTICLEY = 750/PARTICLEDIMENSION;

        shuffeledIndexes = shuffleIndexes(new int[PARTICLEX]);

        grid = new Element[PARTICLEY][PARTICLEX];

    }

    public void updateWorld(){
    
        if(timeStopped){
            return;
        }

        for(int y = PARTICLEY - 1; y >= 0; y--){
            for(int x : shuffeledIndexes){

                if(get(x, y) == null){
                    continue;
                }

                try{
                    grid[y][x].handleParticle(x, y, this); // <-- PERFEKT fysikk
                } catch(ArrayIndexOutOfBoundsException e){
                    // INGENTING LOL
                }

            }
        }

    }


    public void moveFromTo(int x0, int y0, int x1, int y1){

        grid[y1][x1] = grid[y0][x0];
        grid[y0][x0] = null;

    }

    public void switchParticles(int x0, int y0, int x1, int y1){

        Element temp = grid[y0][x0]; 

        grid[y0][x0] = grid[y1][x1];
        grid[y1][x1] = temp;

    }



    // --------------------------------------------------------<| Eksterne |>-------------------------------------------------------- //

    public void pauseUnpauseTime(){
        timeStopped = !timeStopped;
    }

    public boolean isPaused(){
        return timeStopped;
    }

    public void setVoidFloor(){
        voidFloor = !voidFloor;
    }

    public boolean getVoidFloor(){
        return voidFloor;
    }

    public void purgeWorld(){

        for(int y=0; y<PARTICLEY; y++){
            for(int x=0; x<PARTICLEX; x++){

                set(x, y, null);

            }
        }

    }

    public void drawParticles(Graphics g){

        for(int y = 0; y < PARTICLEY; y++){
            for(int x = 0; x < PARTICLEX; x++){

                if(get(x, y) != null){

                    g.setColor(get(x, y).colour);
                    g.fillRect(x*PARTICLEDIMENSION, y*PARTICLEDIMENSION, PARTICLEDIMENSION, PARTICLEDIMENSION);

                }

            }
        }

    }

    public int countParticles(){
        int amount = 0;

        for(int y = 0; y < PARTICLEY; y++){
            for( int x = 0; x < PARTICLEX; x++){

                if(get(x, y) != null){
                    amount++;
                }

            }
        }

        return amount;

    }

    public boolean isWithinBounds(int x, int y){
    
        return !(x >= PARTICLEX || x < 0 || y >= PARTICLEY || y < 0); 

    }

    // Sjekker om plassen gitt i parameteret er opptatt
    // Dersom plassen er utenfor grensene, returnerer den true 
    public boolean pointIsOccupied(int x, int y){

        return (isWithinBounds(x, y)) ? (get(x, y) != null) : true;

    }

    public void set(int x, int y, Element element){

        grid[y][x] = element;

    }

    public Element get(int x, int y){
        
        return grid[y][x];

    }

    // -----------------------------------| Interne |----------------------------------- //

    private int[] shuffleIndexes(int[] indexes){
        Random rand = new Random();
        int nextIndex;

        for(int i = 0; i < indexes.length; i++){

            nextIndex = rand.nextInt(indexes.length);

            if(inArray(nextIndex, i, indexes)){
                i--;
                continue;
            }

            indexes[i] = nextIndex;
            
        }

        return indexes;

    }

    private boolean inArray(int value, int upToIndex, int[] array){

        for(int i = 0; i < upToIndex; i++){

            if(array[i] == value){
                return true;
            }

        }

        return false;

    }

}
