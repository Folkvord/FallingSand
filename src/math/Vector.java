package math;

public class Vector {
    
    public double x;
    public double y;


    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }


    // Adderer komponentene med parameterverdiene
    public void add(double x, double y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

    // Adderer komponentene med en annen vektor
    public void add(Vector vector){
        this.x = this.x + vector.x;
        this.y = this.y + vector.y;
    }

    // Setter komponentene
    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
 
    // Setter komponentene lik en annen vektor
    public void set(Vector vector){
        this.x = vector.x;
        this.y = vector.y;
    }

}