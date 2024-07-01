package window;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;


public class Infobanner extends JPanel {
    
    public Board board;

    private final int LABELAMOUNT = 4;
    private JLabel[] labels;


    public Infobanner(Board board){

        this.board = board;

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(board.BOARDWIDTH, 25));
        setBackground(Color.gray);

        labels = new JLabel[LABELAMOUNT];

        writeLabels();
        initLabels();

    }


    private void writeLabels(){

        labels[0] = new JLabel("M = Menu");
        labels[1] = new JLabel("W = Change brush radius");
        labels[2] = new JLabel("R = Reset world");
        labels[3] = new JLabel("F = Line tool");

    }

    private void initLabels(){

        for(int i = 0; i < LABELAMOUNT; i++){
            
            labels[i].setFont(new Font(null, Font.PLAIN, 15));
            labels[i].setForeground(Color.white);
            add(labels[i]);

        }

    }

}
