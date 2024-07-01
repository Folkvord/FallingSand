package resourcemenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import window.Board;

public class Toolsmenu extends Submenu {
    

    public Toolsmenu(Board board, int buttonAmount){
        
        super(board, buttonAmount);

    }

    
    public void initButtons(){
        
        buttons[0] = new JButton("Rect. tool");
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                toolmanager.changeActiveTool("rectangle");
                supermenu.closeAll();

            }
        });

    }

}
