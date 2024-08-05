package tools;

public class ParticlePallet {

    // TODO: PrintParticles metode som printer ut partikkeltypene med indeks til skjerm 
    
    private final ParticleID[] pallet = new ParticleID[10];
    private final String palletName; 
    
    
    public ParticlePallet(String name, ParticleID[] pallet){

        // Sjekker om palletet er gyldig
        if(pallet.length > 9){
            System.out.println("Pallet: "+ name +" is larger than 9");
            this.palletName = "INVALID PALLET";
            return;
        }


        this.palletName = name;
        
        for(int i = 0; i < 9; i++){
            
            if(i < pallet.length){
                this.pallet[i+1] = pallet[i];
            }
            else{
                this.pallet[i+1] = ParticleID.ERASE;
            }
            
        }

        this.pallet[0] = ParticleID.ERASE;

    }


    public String getName(){
        return palletName;
    }

    public ParticleID getParticle(int index){
        return this.pallet[index];
    }

}
