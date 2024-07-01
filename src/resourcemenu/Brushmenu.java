package resourcemenu;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import window.Board;

public class Brushmenu extends Submenu {
    

    public Brushmenu(Board board, int buttonAmount){
     
        super(board, buttonAmount);

    }


    public void initButtons(){

        buttons[0] = new JButton("Square");
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(1);

            }
        });

        buttons[1] = new JButton("Flat");
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(2);

            }
        });

        buttons[2] = new JButton("Circle");
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(3);

            }
        });

    }

    // Rutinen alle knappene i denne menyen har
    private void routine(int brushID){

        toolmanager.changeBrush(brushID);
        toolmanager.changeActiveTool("brush");
        supermenu.closeAll();

    }

}
