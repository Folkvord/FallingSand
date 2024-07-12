package tools;

import java.awt.Color;
import java.awt.Graphics;

import window.Board;
import entities.World;

public class Rectangletool extends Tool {

    public boolean settingRectangle = false;

    private int x0, y0;
    private int x1, y1;

    private Color colour = Color.red;

    
    public Rectangletool(Board board, World world){

        super(board, world);

    }
    

    // Setter startspunktet til rektangelet
    // Skal kjøres en gang
    public void startArea(int x, int y){

        x0 = x;
        y0 = y;

        settingRectangle = true;

    }

    // Setter sluttpunktet til rektangelet
    // Sjekker om rektanglet er for lite og kansellerer det om så
    public void setArea(int x, int y){

        if(areaTooSmall(x, y)){
            cancel();
            return;
        }

        x1 = x;
        y1 = y;

        executeTask();
                
    }

    // Sjekker om arealet er lite nok for å kanselleres
    private boolean areaTooSmall(int x, int y){
        int greaterLength = Math.max(x0, x);
        int smallerLength = Math.min(x0, x);
        int greaterHeight = Math.max(y0, y);
        int smallerHeight = Math.min(y0, y);
        
        if((greaterLength - smallerLength < 5) || greaterHeight - smallerHeight < 5){
            return true;
        }
        else{
            return false;
        }

    }

    // Lukker rektanglet
    public void cancel(){

        settingRectangle = false;

    }

    // Tegner grensene til rektangelet
    // Kommentarene antar at musa er i fjerde kvadrant
    public void drawRectangletool(Graphics g, int x1, int y1){
        int ps = world.PARTICLEDIMENSION;

        if(!settingRectangle){
            return;
        }

        g.setColor(colour);
        g.drawLine(x0*ps, y0*ps, x1*ps, y0*ps); // Topp
        g.drawLine(x0*ps, y1*ps, x1*ps, y1*ps); // Bunn
        g.drawLine(x0*ps, y0*ps, x0*ps, y1*ps); // Venstre
        g.drawLine(x1*ps, y0*ps, x1*ps, y1*ps); // Høyre

    }

    // Omdanner alle partikkler i arealet
    public void executeTask(){

        int greaterLength = Math.max(x0, x1);
        int smallerLength = Math.min(x0, x1);
        int greaterHeight = Math.max(y0, y1);
        int smallerHeight = Math.min(y0, y1);

        for(int y = smallerHeight; y < greaterHeight; y++){
            for(int x = smallerLength; x < greaterLength; x++){
         
                spawnCorrectParticleType(x, y, particleID);

            }
        }

        cancel();

    }

}
