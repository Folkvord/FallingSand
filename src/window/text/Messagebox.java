package window.text;

import java.awt.Graphics;

public class Messagebox {

    private static final int TITLESIZE = 20;
    private static final int FONTSIZE = 15;

    // Koordinatene er til boksens øverste høyre hjørne
    private int x;
    private int y;
    private String title;
    private String text;
    private int borderBufferWidth = 10;
    private int borderBufferHeight = 10;
    
    
    public Messagebox(String title, String text, int x, int y){

        this.title = title;
        this.text = text;
        this.x = x;
        this.y = y;

    }
    
    public Messagebox(String text, int x, int y){

        this.text = text;
        this.x = x;
        this.y = y;

    }


    public void renderBox(Graphics g){

        

    }

}
