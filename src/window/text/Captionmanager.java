package window.text;

import entities.World;
import java.awt.Graphics;
import java.util.ArrayList;
import tools.ParticleID;
import window.Board;

public class Captionmanager {

    private Board board;
    private World world;

    private ArrayList<Caption> captions = new ArrayList<>();
    private Caption[] defaultCaptions;

    private boolean renderDefaultCaptions = true;


    public Captionmanager(Board board, World world){

        this.board = board;
        this.world = world;

        createDefaultCaptions();

    }

    
    // --------------------------------| DYNAMISK TEKST |-------------------------------- //

    public void renderCaptions(Graphics g){

        for(int i = 0; i < captions.size(); i++){

            captions.get(i).show(g);

        }

    }
    
    // Legger til overtekst i overtekstregisteret 
    public void addCaption(String message, int x, int y){

        Caption caption = new Caption(message, x, y);
        captions.add(caption);

    }

    // Fjerner et objekt fra overtekstregisteret
    // Tar inn teksten til objektet
    public void removeCaption(String caption){

        for(int i = 0; i < captions.size(); i++){

            if(captions.get(i).getMessage().equals(caption)){
                captions.remove(i);
                return;
            }

        }

    }

    // --------------------------------| HARDKODA TEKST LOL |-------------------------------- //

    private void createDefaultCaptions(){
        int x = 0;
        int y = 25;
        
        defaultCaptions = new Caption[4];

        // Partikkeltype
        ParticleID particleID = board.toolmanager.getBrush().getParticleID();
        defaultCaptions[0] = new Caption("PARTICLETYPE: " + particleID, x, y);

        // Partikkeltall
        defaultCaptions[1] = new Caption("PARTICLE COUNT: 0", x, y*2);

        // Tickrate
        defaultCaptions[2] = new Caption("FRAMERATE: 60", x, y*3);

        // Spall
        String palletName = board.toolmanager.getCurrentPallet();
        defaultCaptions[3] = new Caption("PALLET: " + palletName, x, y*4);

    }

    public void renderDefaultText(Graphics g){

        if(!renderDefaultCaptions) return;

        for(int i = 0; i < defaultCaptions.length; i++){

            defaultCaptions[i].show(g);

        }

    }

    public void updateIndividualDefaultCaption(int index, String message){

        defaultCaptions[index].changeMessage(message);

    }

    public void updateDefaultCaptions(){

        // Partikkeltall
        defaultCaptions[1].changeMessage("PARTICLE COUNT: " + world.countParticles());

        // Tickrate
        defaultCaptions[2].changeMessage("FRAMERATE: " + board.framerate);

    }

    public void changeDefaultCaptionRenderStatus(){

        renderDefaultCaptions = !renderDefaultCaptions;

    }

}
