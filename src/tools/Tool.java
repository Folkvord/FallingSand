package tools;

import entities.World;
import entities.liquid.*;
import entities.solid.immovable.*;
import entities.solid.movable.*;
import window.Board;

public abstract class Tool {
    
    public Board board;
    public World world;

    protected ParticleID particleID = ParticleID.SAND;


    public Tool(Board board, World world){

        this.board = board;
        this.world = world;

    }


    // Spawner en ny partikkel på et punkt
    public void spawnCorrectParticleType(int x, int y, ParticleID particleID){

        if(setToErase(x, y, particleID)){
            world.set(x, y, null);
            return;
        }

        if(invalidSpace(x, y)){
            return;
        }

        switch(particleID){

            case SAND:
                world.set(x, y, new Sand(x, y));
                break;
        
            case DIRT:
                world.set(x, y, new Dirt(x, y));
                break;

            case WATER:
                world.set(x, y, new Water(x, y));
                break;
        
            case STONE:
                world.set(x, y, new Stone(x, y));
                break;
        
            default:
                System.out.println("TOOL: LOOOL");
                break;

        }

    }

    // Sier om man skal hviske ut partikkler
    private boolean setToErase(int x, int y, ParticleID particleID){

        return (world.isWithinBounds(x, y) && particleID == ParticleID.ERASE);
    
    }

    private boolean invalidSpace(int x, int y){

        return (!world.isWithinBounds(x, y) || world.get(x, y) != null);

    }


    public void changeParticleID(ParticleID particleID){
        this.particleID = particleID;
    }

    public ParticleID getParticleID(){
        return particleID;
    }
    
}
