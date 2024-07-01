package resourcemenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import window.Board;

public class Elementmenu extends Submenu {
    
    public Elementmenu(Board board, int buttonAmount){

        super(board, buttonAmount);

        addButtons();

    }

    public void initButtons(){
        
        buttons[0] = new JButton("Eraser");
        buttons[0].setBackground(Color.decode("#f5675d"));
        buttons[0].setForeground(Color.black);
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(0);

            }
        });

        buttons[1] = new JButton("Sand");
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(1);

            }
        });

        buttons[2] = new JButton("Water");
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(2);

            }
        });
        
        buttons[3] = new JButton("Stone");
        buttons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                routine(3);
            }
        });

    }

    private void routine(int particleID){

        toolmanager.changeParticleID(particleID);
        supermenu.closeAll();

    }

}
