package main;

import entities.World;
import window.Board;

public class Mainthread implements Runnable {

    public Board board;
    public World world;

    public Mainthread(Board board, World world){

        this.board = board;
        this.world = world;

        new Thread(this).start();

    }

    public void run(){
        double remainingTime;
        double timeInterval = 1000000000/60;
        double nextIterationTime = System.nanoTime() + timeInterval;

        long currentTime;
        long lastTime = System.nanoTime();
        int frameCount = 0;
        long timer = 0;

        while(true){

            currentTime = System.nanoTime();

            world.updateWorld();
            board.repaint();

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

            timer += (currentTime - lastTime);
            lastTime = currentTime;
            frameCount++;

            if(timer >= 1000000000){

                board.importFramerate(frameCount);

                timer = 0;
                frameCount = 0;
            }

        }
    
    }

}
