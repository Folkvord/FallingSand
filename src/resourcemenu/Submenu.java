package resourcemenu;

import tools.Toolmanager;
import window.Board;

public abstract class Submenu extends Resourcemenu {
    
    protected Supermenu supermenu;
    protected Toolmanager toolmanager;

    public Submenu(Board board, int buttonAmount){

        super(board, buttonAmount);

        this.toolmanager = board.toolmanager;

    }


    // X-koordinatet kan aldri være mindre enn null siden vi har alltid lyst på submenyen til høyre om mulig
    // Y-koordinatet kan heller aldri være mindre enn null når submenyen skal ha supermenyens Y-koordinat om mulig
    protected int[] adjustCoordinates(int x, int y){
        int[] coordinates = new int[2];

        x = (x + BUTTONWIDTH*2) > board.BOARDWIDTH ? x - BUTTONWIDTH : x + BUTTONWIDTH;

        y = (y + menuHeight) > board.BOARDHEIGHT ? y - ((y + menuHeight) - board.BOARDHEIGHT) : y;

        coordinates[0] = x;
        coordinates[1] = y;

        return coordinates;

    }

    protected void setSupermenu(Supermenu supermenu){
        this.supermenu = supermenu;
    }


}