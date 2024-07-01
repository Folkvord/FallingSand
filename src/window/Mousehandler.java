package window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mousehandler implements MouseListener {
    
    public boolean leftClick = false;
    public boolean rightClick = false;

    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1){
            leftClick = true;
        }

        if(e.getButton() == 3){
            rightClick = true;
        }
    }

    public void mouseReleased(MouseEvent e){
        if(e.getButton() == 1){
            leftClick = false;
        }

        if(e.getButton() == 3){
            rightClick = false;
        }

    }
    
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}

}
