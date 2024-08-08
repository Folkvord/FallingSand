package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class World {

    public final int PARTICLEDIMENSION;
    
    public final int PARTICLEX;
    public final int PARTICLEY;
    
    private boolean timeStopped = false;    // Om tiden står stille
    private boolean voidFloor = false;      // Sier om partikkler forsvinner når de treffer bunnen av verdnen   (IKKE FERDIG)

    private final Element[][] grid;
    private final int[] shuffeledIndexes;


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
                Element particle = get(x, y);

                if(particle == null || particle.moved) continue;

                particle.handleParticle(x, y, this); // <-- PERFEKT fysikk
                particle.moved = true;

            }
        }

        prepareParticlesForNextTick();

    }

    private void prepareParticlesForNextTick(){

        for(int y = 0; y < PARTICLEY; y++){
            for(int x = 0; x < PARTICLEX; x++){
                Element particle = get(x, y);
                
                if(particle != null) particle.moved = false;

            }
        }

    }


    public void moveFromTo(int x0, int y0, int x1, int y1){

        set(x1, y1, get(x0, y0));
        set(x0, y0, null);

    }

    public void swapParticles(int x0, int y0, int x1, int y1){

        Element temp = get(x0, y0); 

        set(x0, y0, get(x1, y1));
        set(x1, y1, temp);

    }

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
        Element particle;

        for(int y = 0; y < PARTICLEY; y++){
            for(int x = 0; x < PARTICLEX; x++){
                particle = get(x, y);

                if(particle != null){
                    
                    // Normal
                    g.setColor(particle.colour);

                    // Y-HASTIGHET STØRRE EN 0: GRØNN  -  HASTIGHET UNDER ELLER LIK 0: GRÅ
                    //ySpeedColourscheme(particle, g);
                    
                    // X-HASTIGHET STØRRE EN 0: GUL  -  HASTIGHET UNDER ELLER LIK 0: GRÅ
                    //xSpeedColourscheme(particle, g);
                    
                    // HAR HASTIGHET: GRØNN - INGEN HASTIGHET: GRÅ
                    //generalSpeedColourScheme(particle, g);

                    // FALLER: BLÅ  -  STILLE: RØD
                    //fallingColourscheme(particle, g);
                    
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

    // Returnerer true om partikkelen er på den nedre grensen
    public boolean onLowerBorder(int y){

        return y == PARTICLEY;

    }

    // Returnerer true om partikkelen er på høyre eller venstre grense
    public boolean onEastOrWestBorder(int x){

        return x == 0 || x == PARTICLEX;

    }

    public void set(int x, int y, Element element){

        grid[y][x] = element;

    }

    public Element get(int x, int y){
        
        return grid[y][x];

    }

    // -----------------------------------| Interne |----------------------------------- //

    // Brukes for å stokke om rekkefølgen x-koordinatene sjekkes (Fikser skredbugget)
    private int[] shuffleIndexes(int[] indexes){
        Random rand = new Random(9);
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

    protected void fallingColourscheme(Element particle, Graphics g){
        if(particle.isFalling()){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.RED);
        }
    }

    protected void ySpeedColourscheme(Element particle, Graphics g){
        if(particle.velocityVector.y > 0){
            g.setColor(Color.green);
        }
        else{
            g.setColor(Color.gray);
        }
    }

    protected void generalSpeedColourScheme(Element particle, Graphics g){
        if(particle.velocityVector.y != 0 || particle.velocityVector.x != 0){
            g.setColor(Color.green);
        }
        else{
            g.setColor(Color.gray);
        }
    }

    protected void xSpeedColourscheme(Element particle, Graphics g){
        if(particle.velocityVector.x != 0.0){
            g.setColor(Color.yellow);
        }
        else{
            g.setColor(Color.gray);
        }
    }

}
