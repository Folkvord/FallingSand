package entities.utility;

import entities.World;
import entities.liquid.Water;
import entities.solid.immovable.Stone;
import entities.solid.movable.Dirt;
import entities.solid.movable.Sand;
import tools.ParticleID;

public abstract class Utility {
    
    private final World world;

    protected ParticleID particleID;
    protected int x;
    protected int y;


    public Utility(int x, int y, World world){

        this.world = world;
        this.x = x;
        this.y = y;
    }


    protected void spawnUtilParticle(int x, int y){

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
                System.out.println("UTILITY: INVALID PARTICLEID");
                break;

        }

    }


    private boolean invalidSpace(int x, int y){

        return (!world.isWithinBounds(x, y) || world.get(x, y) != null);

    }

    public abstract void event();

}
