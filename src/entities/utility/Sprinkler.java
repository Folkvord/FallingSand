package entities.utility;

import entities.World;
import tools.ParticleID;

public class Sprinkler extends Utility {
    
    
    public Sprinkler(int x, int y, ParticleID particleID, World world){
        
        super(x, y, world);

    }


    @Override
    public void event(){

        spawnUtilParticle(x, y - 1);

        for(int i = -1; i < 1; i++){
            spawnUtilParticle(x + i, y);
        }
    
        spawnUtilParticle(x, y - 1);
            
    }

}
