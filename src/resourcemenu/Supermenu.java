package resourcemenu;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import window.Board;

public class Supermenu extends Resourcemenu {
    
    private Submenu currentlyOpenSubmenu;

    private final int SUBMENUAMOUNT = 3;
    private Submenu[] submenus;


    public Supermenu(Board board, int buttonAmount){

        super(board, buttonAmount);

        // Lager submenyene
        submenus = new Submenu[SUBMENUAMOUNT];

        submenus[0] = new Elementmenu(board, 4);
        submenus[1] = new Brushmenu(board, 3);
        submenus[2] = new Toolsmenu(board, 1);

        initSubmenus();

    }


    public void initButtons(){
        
        buttons[0] = new JButton("Elements");
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                openSubmenuRoutine(0, x, y);

            }
        });

        buttons[1] = new JButton("Brushes");
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                openSubmenuRoutine(1, x, y);

            }
        });

        buttons[2] = new JButton("Tools");
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                openSubmenuRoutine(2, x, y);

            }
        });

    }

    public void closeAll(){

        closeOpenSubmenu();
        currentlyOpenSubmenu = null;
        close();

    }

    // Setter supermenyen og legger submenyene til board
    private void initSubmenus(){

        for(int i = 0; i < SUBMENUAMOUNT; i++){

            submenus[i].setSupermenu(this);
            board.add(submenus[i]);

        }

    }

    // Rutinen hvert knappetrykk skal følge ( Bare i supermenyen )
    private void openSubmenuRoutine(int index, int x, int y){
        Submenu submenu = submenus[index];

        if(thereIsAnOpenSubmenu()){

            closeOpenSubmenu();
            submenu.open(x, y);
            currentlyOpenSubmenu = submenu;

        }
        else{

            submenu.open(x, y);
            currentlyOpenSubmenu = submenu;

        }

    }

    // Returnerer true om det er en åpen submeny, false om ikke
    public boolean thereIsAnOpenSubmenu(){
        return (currentlyOpenSubmenu != null);
    }

    // Lukker den åpne submenyen
    private void closeOpenSubmenu(){

        currentlyOpenSubmenu.close();
        currentlyOpenSubmenu = null;

    }



}
