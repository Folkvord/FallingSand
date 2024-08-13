package window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mousehandler implements MouseListener {
    
    public boolean leftClick = false;
    public boolean rightClick = false;
    public boolean m4Click = false;
    public boolean m5Click = false;

    @Override
    public void mousePressed(MouseEvent e){

        switch(e.getButton()){

            case 1:
                leftClick = true;
                break;
            
            case 3:
                rightClick = true;
                break;

            case 4:
                m4Click = true;
                break;

            case 5:
                m5Click = true;
                break;

            default:
                System.out.println(e.getButton());
                break;

        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        
        switch(e.getButton()){

            case 1:
                leftClick = false;
                break;
            
            case 3:
                rightClick = false;
                break;

            case 4:
                m4Click = false;
                break;

            case 5:
                m5Click = false;
                break;

            default:
                System.out.println(e.getButton());
                break;

        }
        
    }
    
    
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}

}
