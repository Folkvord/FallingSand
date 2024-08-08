package window.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Caption {
    
    private static final int FONTSIZE = 20;
    private static final Font GLOBALFONT = new Font(null, Font.PLAIN, FONTSIZE);
    private static final Color GLOBALCOLOUR = Color.white;

    private String message;
    private int x;
    private int y;


    public Caption(String message, int x, int y){

        this.message = message;
        this.x = x;
        this.y = y;

    }


    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void changeMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    protected void show(Graphics g){

        g.setFont(GLOBALFONT);
        g.setColor(GLOBALCOLOUR);
        g.drawString(message, x, y);

    }


}
