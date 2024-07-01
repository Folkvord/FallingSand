package window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Toolsidebar extends JPanel {
    
    private final int WIDTH = 100;
    private final int HEIGHT;

    public Toolsidebar(int height){

        HEIGHT = height;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.gray);


    }



}
