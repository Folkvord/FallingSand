package math;

public class Vector {
    
    public float x;
    public float y;


    public Vector(float x, float y){
        this.x = x;
        this.y = y;
    }


    public Vector copy(){
        return new Vector(this.x, this.y);
    }

    // Adderer komponentene med parameterverdiene
    public void add(float x, float y){
        this.x = this.x + x;
        this.y = this.y + y;
    }

    // Adderer komponentene med en annen vektor
    public void add(Vector vector){
        this.x = this.x + vector.x;
        this.y = this.y + vector.y;
    }

    // Setter komponentene
    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }
 
    // Setter komponentene lik en annen vektor
    public void set(Vector vector){
        this.x = vector.x;
        this.y = vector.y;
    }

    // Returnerer lengden til en vektor
    public float lenght(){
        return (float) Math.sqrt(x*x + y*y);
    }

    // Normaliserer vektoren
    public Vector normalize(){
        float len = lenght();
        
        if(len != 0){
            this.x /= len;
            this.y /= len;
        }
        
        return this; 

    }

    public boolean isNullVector(){
        return (this.x == 0 && this.y == 0);
    }

    @Override
    public String toString(){
        return "("+ x +", "+ y +")";
    }

}
