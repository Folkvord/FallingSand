package tools;

import window.Board;
import entities.World;
import entities.liquid.*;
import entities.solid.movable.*;
import entities.solid.immovable.*;

public abstract class Tool {
    
    public Board board;
    public World world;

    protected int particleID = 1;


    public Tool(Board board, World world){

        this.board = board;
        this.world = world;

    }


    // Spawner en ny partikkel på et punkt
    public void spawnCorrectParticleType(int x, int y, int particleID){

        if(setToErase(x, y, particleID)){
            world.set(x, y, null);
            return;
        }

        if(invalidSpace(x, y)){
            return;
        }

        switch(particleID){

            case 1:
                world.set(x, y, new Sand());
                break;
        
            case 2:
                world.set(x, y, new Water());
                break;
        
            case 3:
                world.set(x, y, new Stone());
                break;
        
            default:
                System.out.println("TOOL: LOOOL");
                break;

        }

    }

    // Sier om man skal hviske ut partikkler
    private boolean setToErase(int x, int y, int particleID){

        return (world.isWithinBounds(x, y) && particleID == 0);
    
    }

    private boolean invalidSpace(int x, int y){

        return (!world.isWithinBounds(x, y) || world.get(x, y) != null);

    }


    public void changeParticleID(int particleID){
        this.particleID = particleID;
    }

    public int getParticleID(){
        return particleID;
    }
    
}
