package window;

import entities.World;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import tools.Toolmanager;
import window.text.Captionmanager;

public class Keyhandler implements KeyListener {
    
    private Board board;
    private World world;
    private Toolmanager toolmanager;
    private Captionmanager captionmanager;

    private boolean changePalletMode = false;
        

    public Keyhandler(Board board, World world){
        
        this.board = board;
        this.toolmanager = board.toolmanager;
        this.captionmanager = board.captionmanager;
        this.world = world;

    }


    @Override
    public void keyTyped(KeyEvent e){
        char keyCode = e.getKeyChar();
        int  index = getKeyIntValue(keyCode);


        // Et tall : Endrer partikkeltypen til den i pallettet
        if(index != -1){

            if(changePalletMode){
                toolmanager.changeCurrentPallet(index);
                changePalletMode = false;
            }
            else{
                toolmanager.changeParticleID(index);
            }
            
        }

        // Q : Endrer børstens partikkeltype
        if(keyCode == 'q' || keyCode == 'Q'){
            
            if(changePalletMode){
                captionmanager.updateIndividualDefaultCaption(3, "PALLET: " + toolmanager.getCurrentPallet());
                changePalletMode = false;
            }
            else{
                captionmanager.updateIndividualDefaultCaption(3, "PALLET: selecting");
                changePalletMode = true;
            }

        }

        // W : Endrer børstens radius
        if(keyCode == 'w' || keyCode == 'W'){
    
            toolmanager.brush.incrementRadiusByFive();
    
        }

        // R : Sletter alle partikklene i verdenen
        else if(keyCode == 'r' || keyCode == 'R'){

            world.purgeWorld();

        }

        // M : Åpner menyen
        else if(keyCode == 'm' || keyCode == 'M'){

            // Om menyen er lukket
            if(!board.menu.isVisible()){
                board.menu.open(board.getCurrentMouseX(), board.getCurrentMouseY());
                return;
            }
            
            // Om menyen er åpen: vi ser om det er en submeny åpen og lukker alle eller 
            if(board.menu.thereIsAnOpenSubmenu()){
                board.menu.closeAll();
            }
            else{
                board.menu.close();
            }

        }

        // ESC : Lukker menyen om den er åpen
        else if(keyCode == KeyEvent.VK_ESCAPE && board.menu.isVisible()){

            if(board.menu.thereIsAnOpenSubmenu()){
                board.menu.closeAll();
            }
            else{
                board.menu.close();
            }

        }

        //   : Pauser oppdateringen av verdenen
        else if(keyCode == ' ' || keyCode == ' '){

            world.pauseUnpauseTime();

        }

        // F : Linjeværktøy
        if(keyCode == 'F' || keyCode == 'f'){

            board.setLineToolState();

        }

    }


    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}

    
    // Returnerer tastet som en int-verdi
    // Om tastet ikke var et nummer, returneres -1
    private int getKeyIntValue(char keyCode){
        
        try{

            int intValue = Integer.parseInt(keyCode+"");
            return intValue;

        } catch (Exception e) {

            return -1;

        }

    }

}