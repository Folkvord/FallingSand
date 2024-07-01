package resourcemenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import window.Board;

/*
 * En poppopp-meny som inneholder knapper for å velge
 * elementer, børster og børstestørrelser, med
 * evt mer i fremtiden
 * 
 * Panelet skal være usynelig med knappene synelige
 * sortert i en stack. 
 * 
 * Den første menyen skal kunne åpne flere menyer
 * for elementer, børster, osv
 * 
 * OBS: 
 * Det er ikke støtte for sub-supermenyer
 * 
 */

public abstract class Resourcemenu extends JPanel {

    public Board board;
    public int PARTICLEDIMENSION;

    public int BUTTONWIDTH = 100;           // Bredden til knappene
    public int BUTTONHEIGHT = 18;           // Høyden til knappene

    protected int BUTTONBORDERHEIGHT = 4;   // Mellomrommet mellom knappene delt på to ( når 4: mellomrom = 8 ) 

    protected int menuWidth;                // Bredden til menyen
    protected int menuHeight;               // Høyden til menyen

    protected int x;                        // X-koordinatet øverst til venstre av menyen
    protected int y;                        // Y-koordinatet ------------ .. ------------

    protected JButton[] buttons;            // Samlingen av alle knappene i menyen
    protected final int BUTTONAMOUNT;       // Antall knapper

    protected Dimension buttonDimension;    // Dimensjonene til knappene i et Dimension-objekt

    
    public Resourcemenu(Board board, int buttonAmount){

        this.board = board;
        PARTICLEDIMENSION = board.world.PARTICLEDIMENSION;
        this.BUTTONAMOUNT = buttonAmount;

        // Setter størrelser
        menuHeight = BUTTONAMOUNT * BUTTONHEIGHT;
        menuWidth = BUTTONWIDTH;

        // Sett felles estetikk
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setLayout(new GridLayout(BUTTONAMOUNT, 1, 0, BUTTONBORDERHEIGHT));
        setBackground(Color.black);
        setVisible(false);

        // Initier knappene
        buttons = new JButton[BUTTONAMOUNT];
        buttonDimension = new Dimension(BUTTONWIDTH, BUTTONHEIGHT);
        initButtons();
        addButtons();
    }


    // Tar inn koordinater og endrer på dem slik at menyen holder seg innenfor board
    protected int[] adjustCoordinates(int x, int y){
        int[] coordinates = new int[2];

        x = x*PARTICLEDIMENSION;
        y = y*PARTICLEDIMENSION;

        x = x < 0 ? 0 : x;
        x = (x + BUTTONWIDTH) > board.BOARDWIDTH ? x - ((x + menuWidth) - board.BOARDWIDTH) : x;

        y = y < 0 ? 0 : y;
        y = (y + menuHeight) > board.BOARDHEIGHT ? y - ((y + menuHeight) - board.BOARDHEIGHT) : y;

        coordinates[0] = x;
        coordinates[1] = y;

        return coordinates;

    }

    // Når menyen vises
    public void open(int x, int y){
        int[] coordinates = adjustCoordinates(x, y);

        this.x = coordinates[0];
        this.y = coordinates[1];

        setBounds(this.x, this.y, menuWidth, menuHeight);
        setVisible(true);

    }
    
    // Når menyen legges ned
    public void close(){
        
        x = -1;
        y = -1;
        setVisible(false);
        
    }
    
    // Kan kaste en unntak om alle knappene ikke er initierte
    public void addButtons(){

        for(int i = 0; i < BUTTONAMOUNT; i++){

            buttons[i].setPreferredSize(buttonDimension);
            buttons[i].setFocusable(false);
            add(buttons[i]);

        }

    }

    // Hvor knappene initialiseres
    public abstract void initButtons();


}
