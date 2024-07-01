package main;

import tools.Rectangletool;
import tools.Toolmanager;
import window.Board;
import window.Mousehandler;

public class Interactionthread implements Runnable {
    
    public Board board;
    public Mousehandler mousehandler;
    public Rectangletool rectangletool;
    public Toolmanager toolmanager;

    public Interactionthread(Board board, Toolmanager toolmanager){

        this.board = board;
        this.mousehandler = board.mousehandler;
        this.rectangletool = board.rectangletool;
        this.toolmanager = toolmanager;

        new Thread(this).start();

    }

    public void run(){
        double remainingTime;
        double timeInterval = 1000000000/100000;
        double nextIterationTime = System.nanoTime() + timeInterval;

        while(true){

            board.updateMousePosition();

            toolmanager.useCorrectTool();

            try{
                remainingTime = nextIterationTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                nextIterationTime = System.nanoTime() + timeInterval;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextIterationTime += timeInterval;

            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }

        }

    }

}
