package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.World;
import tools.Toolmanager;
import resourcemenu.Supermenu;

public class Keyhandler implements KeyListener {
    
    public Board board;
    public Toolmanager toolmanager;
    public Supermenu menu;
    public World world;


    public Keyhandler(Board board, World world){
        
        this.board = board;
        this.toolmanager = board.toolmanager;
        this.menu = board.menu;
        this.world = world;

    }


    public void keyTyped(KeyEvent e){
        char keyCode = e.getKeyChar();

        // Q : Endrer børstens partikkeltype
        if(keyCode == 'q' || keyCode == 'Q'){
            
            toolmanager.brush.incrementParticleID();
            
        }
        
        // W : Endrer børstens radius
        else if(keyCode == 'w' || keyCode == 'W'){
    
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

        // G : Pauser oppdateringen av verdenen
        else if(keyCode == 'G' || keyCode == 'g'){

            world.pauseUnpauseTime();

        }

        // F : Linjeværktøy
        if(keyCode == 'F' || keyCode == 'f'){

            board.setLineToolState();

        }

    }

    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    
}
