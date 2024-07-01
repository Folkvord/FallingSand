package main;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import window.Board;
import entities.World;
import tools.Toolmanager;
import window.Infobanner;

public class Main {
    public static void main(String[] args) throws Exception {

        // Sentrale objekter
        World           world = new World();
        Toolmanager     toolmanager = new Toolmanager(world);
        Board           board = new Board(world, toolmanager);

        // Tillegg til root
        Infobanner info = new Infobanner(board);

        // Tråder
        new Mainthread(board, world);
        new Interactionthread(board, toolmanager);

        // Root
        prepareRoot(board, info);

    }

    private static void prepareRoot(Board board, Infobanner info){

        JFrame root = new JFrame();

        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setVisible(true);
        root.setResizable(false);

        root.setLayout(new BorderLayout());
        root.add(board, BorderLayout.CENTER);
        root.add(info, BorderLayout.NORTH);
        root.pack();

        root.setLocationRelativeTo(null);
        root.setVisible(true);

    }

}
