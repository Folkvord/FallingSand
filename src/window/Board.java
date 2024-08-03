package window;

import entities.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import resourcemenu.*;
import tools.*;
import tools.brush.*;

public class Board extends JPanel {

    // Dimensjonen til vinduet
    public final int BOARDWIDTH;        // Bredden av board i piksler
    public final int BOARDHEIGHT;       // Høyden ------- .. --------
    
    // Input
    public Keyhandler keyhandler;       // Håndterer tastaturet
    public Mousehandler mousehandler;   // Håndterer musa

    private int mouseX, mouseY;         // Koordinater som gis på matriseform
    private int lastMX, lastMY;         // Forrige ticks sine muskoordinater
    public Brush brush;                 // Børste
    public Rectangletool rectangletool; // Rektangelverktøy
    public Toolmanager toolmanager;     // Verktøybehandler

    private boolean lineToolActive = false;
    private int lineToolStartX = -1;
    private int lineToolStartY = -1;
    private char lineToolAxis = 'n';

    // Verdenen
    public World world;

    // Menyen
    public Supermenu menu;

    // Informasjon om sesjonen
    private int framerate = 0;



    public Board(World world, Toolmanager toolmanager){

        this.world = world;

        BOARDWIDTH = world.PARTICLEX * world.PARTICLEDIMENSION;
        BOARDHEIGHT = world.PARTICLEY * world.PARTICLEDIMENSION;

        // Størrelse og estetikk
        setPreferredSize(new Dimension(BOARDWIDTH, BOARDHEIGHT));
        setLayout(null);
        setBackground(Color.black);

        // Mus
        mousehandler = new Mousehandler(); 
        addMouseListener(mousehandler);

        // Verktøyhandler
        this.toolmanager = toolmanager;
        toolmanager.createTools(this);

        // Tastatur
        setFocusable(true);
        keyhandler = new Keyhandler(this, world);
        addKeyListener(keyhandler);

        // Meny
        menu = new Supermenu(this, 3);
        add(menu);

    }


    // ------------------------------------<| Mus |>-------------------------------------------- //

    public void updateMousePosition(){

        Point point = MouseInfo.getPointerInfo().getLocation();

        SwingUtilities.convertPointFromScreen(point, this);

        lastMX = mouseX;
        lastMY = mouseY;

        switch(lineToolAxis){

            case 'x':
    
                mouseX = lineToolStartX;
                mouseY = ((int) point.getY())/world.PARTICLEDIMENSION;
    
                break;

            case 'y':

                mouseX = ((int) point.getX())/world.PARTICLEDIMENSION;
                mouseY = lineToolStartY;
    
                break;

            default:
    
                mouseX = ((int) point.getX())/world.PARTICLEDIMENSION;
                mouseY = ((int) point.getY())/world.PARTICLEDIMENSION;
    
                break;
            
        }

        // Aktiverer linjeværktøyet
        if(lineToolActive && lineToolAxis == 'n'){
            lineToolInit();
        }

    }

    public int getCurrentMouseX(){
        return mouseX;
    }

    public int getCurrentMouseY(){
        return mouseY;
    }

    public int getLastMouseX(){
        return lastMX;
    }

    public int getLastMouseY(){
        return lastMY;
    }


    // ------------------------------------<| Verktøy |>-------------------------------------------- //

    public void setLineToolState(){

        lineToolActive = !lineToolActive;

        lineToolAxis = 'n';
        lineToolStartX = -1;
        lineToolStartY = -1;

    }

    private void lineToolInit(){

        if(lineToolStartX == -1){

            lineToolStartX = mouseX;
            lineToolStartY = mouseY;

        }
        else{

            lineToolAxis = determineLineToolAxis();

        }

    }

    private char determineLineToolAxis(){

        if(lineToolStartX != mouseX){
            return 'y';
        }
        else if(lineToolStartY != mouseY){
            return 'x';
        }
        else{
            return 'n';
        }

    }


    // ------------------------------------<| Prototyper |>-------------------------------------------- //

    // Prototype
    public void drawVector(Graphics g){
        int ps = world.PARTICLEDIMENSION;
        
        int x0 = 30, y0 = 30;
        int x1 = mouseX, y1 = mouseY;
        
        if(x0 > x1){
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
            }
            
        float y = (y1 - y0);
        float x = (x1 - x0);
            
        if(x0 == x1){
            x = 1;
        }

        // y = ax + b
        double a = y/x;
        int b = (int) (y0-a*x0);

        int placeX = x0;
        int placeY;

        while(placeX <= x1){

            placeY = (int) (a*placeX) + b;
            g.fillRect(placeX*ps, placeY*ps, ps, ps);
            g.setColor(Color.white);
            placeX += 1;

        }

    }

    // Prototype
    public void drawNewVector(Graphics g){
        int ps = world.PARTICLEDIMENSION;

        int x0 = world.PARTICLEX/2, y0 = world.PARTICLEY/2;
        int x1 = mouseX, y1 = mouseY;

        int xDiff = x0 - x1;
        int yDiff = y0 - y1;

        boolean xDiffIsLarger = Math.abs(xDiff) > Math.abs(yDiff);

        int xMod = xDiff < 0 ? 1 : -1;
        int yMod = yDiff < 0 ? 1 : -1;

        int longestSide = Math.max(Math.abs(xDiff), Math.abs(yDiff));
        int shortestSide = Math.min(Math.abs(xDiff), Math.abs(yDiff));

        float a = (longestSide == 0 || shortestSide == 0) ? 0 : ((float) (shortestSide)/(longestSide));

        g.setColor(Color.red);
        g.fillRect(x0*ps, y0*ps, ps, ps);

        int shortestSideIncrease;
        int xIncrease, yIncrease;
        int x, y;
        int lastX = x0, lastY = y0;
        for(int i = 1; i <= longestSide; i++){

            shortestSideIncrease = Math.round(i * a);

            if(xDiffIsLarger){
                xIncrease = i;
                yIncrease = shortestSideIncrease;
            }
            else{
                xIncrease = shortestSideIncrease;
                yIncrease = i;
            }

            
            x = x0 + (xIncrease * xMod);
            y = y0 + (yIncrease * yMod);

            if(i == 5){x0++; y0++;}
            
            if(world.pointIsOccupied(x, y)){
                g.setColor(Color.red);
                g.fillRect(lastX*ps, lastY*ps, ps, ps);
                return;
            }

            else{
                g.setColor(Color.white);
                g.fillRect(x*ps, y*ps, ps, ps);
            }

            lastX = x0 + (xIncrease * xMod);
            lastY = y0 + (yIncrease * yMod);

        }

    }

    public void circlelul(Graphics g){
        int radius = 10;
        int mx = mouseX;
        int my = mouseY;
        int ps = world.PARTICLEDIMENSION;
        int x0 = 0, y0 = 0, x1 = 1, y1 = 1;

        double[] cos = {1.0, 0.5403023058681398, -0.4161468365471424, -0.9899924966004454, -0.6536436208636119, 0.28366218546322625, 0.960170286650366, 0.7539022543433046, -0.14550003380861354, -0.9111302618846769, -0.8390715290764524, 0.004425697988050785, 0.8438539587324921, 0.9074467814501962, 0.1367372182078336, -0.7596879128588213, -0.9576594803233847, -0.27516333805159693, 0.6603167082440802, 0.9887046181866692, 0.40808206181339196, -0.5477292602242684, -0.9999608263946371, -0.5328330203333975, 0.424179007336997, 0.9912028118634736, 0.6469193223286404, -0.2921388087338362, -0.9626058663135666, -0.7480575296890004, 0.15425144988758405, 0.9147423578045313, 0.8342233605065102, -0.013276747223059479, -0.8485702747846052, -0.9036922050915067, -0.12796368962740468, 0.7654140519453434, 0.9550736440472949, 0.26664293235993725, -0.6669380616522619, -0.9873392775238264, -0.39998531498835127, 0.5551133015206257, 0.9998433086476912, 0.5253219888177297, -0.4321779448847783, -0.9923354691509287, -0.6401443394691997, 0.3005925437436371, 0.9649660284921133, 0.7421541968137826, -0.16299078079570548, -0.9182827862121189, -0.8293098328631502, 0.022126756261955736, 0.853220107722584, 0.8998668269691937, 0.11918013544881928, -0.7710802229758452, -0.9524129804151563, -0.25810163593826746, 0.6735071623235862, 0.9858965815825497, 0.39185723042955, -0.562453851238172, -0.99964745596635, -0.5177697997895051, 0.4401430224960407, 0.9933903797222716, 0.6333192030862999, -0.3090227281660707, -0.9672505882738824, -0.7361927182273159, 0.17171734183077755, 0.9217512697247493, 0.8243313311075577, -0.03097503173121646, -0.8578030932449878, -0.8959709467909631, -0.11038724383904756, 0.7766859820216312, 0.9496776978825432, 0.24954011797333814, -0.6800234955873388, -0.9843766433940419, -0.38369844494974187, 0.569750334265312, 0.9993732836951247, 0.5101770449416689, -0.4480736161291701, -0.9943674609282015, -0.626444447910339, 0.31742870151970165, 0.9694593666699876, 0.7301735609948197, -0.18043044929108396, -0.9251475365964139, -0.8192882452914593, 0.0398208803931389, 0.8623188722876839, 0.8920048697881602, 0.10158570369662134, -0.7822308898871159, -0.9468680107512125, -0.24095904923620143, 0.6864865509069841, 0.9827795820412206, 0.3755095977670121, -0.577002178942952, -0.999020813314648, -0.5025443191453852, 0.4559691044442761, 0.9952666362171313, 0.6195206125592099, -0.3258098052199642, -0.9715921906288022, -0.7240971967004738, 0.1891294205289584, 0.9284713207390763, 0.8141809705265618, -0.04866360920015389, -0.8667670910519801, -0.8879689066918555, -0.09277620459766088, 0.7877145121442345, 0.9439841391523142, 0.23235910202965793, -0.6928958219201651, -0.9811055226493881, -0.3672913304546965, 0.5842088171092893, 0.9985900724399912, 0.4948722204034305, -0.4638288688518717, -0.9960878351411849, -0.6125482394960996, 0.33416538263076073, 0.9736488930495181, 0.717964101410472, -0.19781357400426822, -0.9317223617435201, -0.8090099069535975, 0.05750252534912421, 0.8711474010323434, 0.8838633737085002, 0.08395943674184847, -0.7931364191664784, -0.9410263090291437, -0.22374095013558368, 0.6992508064783751, 0.9793545963764285, 0.35904428689111606, -0.5913696841443247, -0.9980810948185003, -0.48716134980334147, 0.47165229356133864, 0.9968309933617175, 0.6055278749869898, -0.34249477911590703, -0.9756293127952373, -0.7117747556357236, 0.20648222933781113, 0.9349004048997503, 0.803775459710974, -0.06633693633562374, -0.875459459043705, -0.8796885924951523, -0.07513609089835323, 0.7984961861625556, 0.9379947521194415, 0.21510526876214117, -0.7055510066862999, -0.9775269404025313, -0.3507691132091307, 0.5984842190140996, 0.9974939203271522, 0.47941231147032193, -0.4794387656291727, -0.9974960526543551, -0.5984600690578581};
        double[] sin = {0.0, 0.8414709848078965, 0.9092974268256817, 0.1411200080598672, -0.7568024953079282, -0.9589242746631385, -0.27941549819892586, 0.6569865987187891, 0.9893582466233818, 0.4121184852417566, -0.5440211108893698, -0.9999902065507035, -0.5365729180004349, 0.4201670368266409, 0.9906073556948704, 0.6502878401571168, -0.2879033166650653, -0.9613974918795568, -0.7509872467716762, 0.14987720966295234, 0.9129452507276277, 0.8366556385360561, -0.008851309290403876, -0.8462204041751706, -0.9055783620066238, -0.13235175009777303, 0.7625584504796027, 0.956375928404503, 0.27090578830786904, -0.6636338842129675, -0.9880316240928618, -0.404037645323065, 0.5514266812416906, 0.9999118601072672, 0.5290826861200238, -0.428182669496151, -0.9917788534431158, -0.6435381333569995, 0.2963685787093853, 0.9637953862840878, 0.7451131604793488, -0.158622668804709, -0.9165215479156338, -0.8317747426285983, 0.017701925105413577, 0.8509035245341184, 0.9017883476488092, 0.123573122745224, -0.7682546613236668, -0.9537526527594719, -0.26237485370392877, 0.6702291758433747, 0.9866275920404853, 0.39592515018183416, -0.5587890488516163, -0.9997551733586199, -0.5215510020869119, 0.43616475524782494, 0.9928726480845371, 0.6367380071391379, -0.3048106211022167, -0.9661177700083929, -0.7391806966492228, 0.16735570030280691, 0.9200260381967907, 0.8268286794901034, -0.026551154023966794, -0.8555199789753223, -0.8979276806892913, -0.11478481378318722, 0.7738906815578891, 0.9510546532543747, 0.25382336276203626, -0.6767719568873076, -0.9851462604682474, -0.38778163540943045, 0.5661076368981803, 0.9995201585807313, 0.5139784559875352, -0.4441126687075084, -0.9938886539233752, -0.6298879942744539, 0.31322878243308516, 0.9683644611001854, 0.7331903200732922, -0.1760756199485871, -0.9234584470040598, -0.8218178366308225, 0.03539830273366068, 0.8600694058124532, 0.8939966636005579, 0.10598751175115685, -0.7794660696158047, -0.9482821412699473, -0.24525198546765434, 0.683261714736121, 0.9835877454343449, 0.3796077390275217, -0.5733818719904229, -0.9992068341863537, -0.5063656411097588, 0.45202578717835057, 0.9948267913584063, 0.6229886314423488, -0.32162240316253093, -0.9705352835374847, -0.7271425000808526, 0.18478174456066745, 0.926818505417785, 0.8167426066363169, -0.044242678085070965, -0.8645514486106083, -0.8899956043668333, -0.09718190589320902, 0.7849803886813105, 0.9454353340247703, 0.23666139336428604, -0.689697940935389, -0.9819521690440836, -0.3714041014380902, 0.5806111842123143, 0.9988152247235795, 0.4987131538963941, -0.45990349068959124, -0.9956869868891794, -0.6160404591886565, 0.329990825673782, 0.972630067242408, 0.7210377105017316, -0.19347339203846847, -0.9301059501867618, -0.8116033871367005, 0.05308358714605824, 0.8689657562142357, 0.8859248164599484, 0.08836868610400143, -0.7904332067228887, -0.9425144545582509, -0.2280522595008612, 0.6960801312247415, 0.9802396594403116, 0.363171365373259, -0.5877950071674065, -0.9983453608739179, -0.49102159389846933, 0.4677451620451334, 0.9964691731217737, 0.6090440218832924, -0.3383333943242765, -0.9746486480944947, -0.7148764296291646, 0.20214988141565363, 0.933320523748862, 0.8064005807754863, -0.06192033725605731, -0.8733119827746476, -0.8817846188147811, -0.0795485428747221, 0.7958240965274552, 0.9395197317131483, 0.21942525837900473, -0.702407785577371, -0.9784503507933796, -0.35491017584493534, 0.5949327780232085, 0.9977972794498907, 0.48329156372825655, -0.47555018687189876, -0.9971732887740798, -0.6019998676776046, 0.3466494554970303, 0.9765908679435658, 0.7086591401823227, -0.2108105329134813, -0.9364619742512132, -0.8011345951780408, 0.07075223608034517, 0.8775897877771157, 0.8775753358042688, 0.07072216723899125, -0.8011526357338304};

        int deg = 0;
        while(deg < 180){

            x0 = mx + (int) (Math.ceil((int) (cos[deg] * radius)));
            y0 = my + (int) (Math.ceil((int) (sin[deg] * radius)));

            x1 = mx - (int) (Math.ceil((int) (cos[deg] * radius)));
            y1 = my - (int) (Math.ceil((int) (sin[deg] * radius)));

            deg++;

            g.drawRect(x0*ps, y0*ps, ps, ps);
            g.drawRect(x1*ps, y1*ps, ps, ps);

        }

    }
    // ----------------------------------<| Informasjon |>------------------------------------------ //

    public void importFramerate(int framerate){

        this.framerate = framerate;

    }

    // ------------------------------------<| Grafikk |>-------------------------------------------- //

    


    public void showInfo(Graphics g){
        String type;
        
        // Finn ut type
        switch(toolmanager.getBrush().getParticleID()) {
            
            case ERASE:
                type = "ERASER";
                break;
            
            case SAND:
                type = "SAND";
                break;
        
            case WATER:
                type = "WATER";
                break;

            case STONE:
                type = "STONE";
                break;

            default:
                type = "NULL";
                break;
        }

        g.setFont(new Font(null, Font.PLAIN, 20));
        g.setColor(Color.white);

        g.drawString("TYPE: "+type, 0, 25);
        g.drawString("BRUSH SIZE: "+toolmanager.getBrush().getRadius()*2, 0, 25*2);
        
        g.drawString("PARTICLE AMOUNT: "+world.countParticles(), 0, 25*3);  // Lagger

        g.drawString("TICKRATE: "+framerate, 0, 25*4);

        if(world.isPaused()){

            g.drawString("PAUSED", 0, 25*5);

        }

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        // Vis verdenen
        world.drawParticles(g);

        // Vis informasjon
        showInfo(g);

        // Verktøy
        toolmanager.drawCorrectTool(g);
        
        // Annet LOL
        //drawNewVector(g);
        
    }

}