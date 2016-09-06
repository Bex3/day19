package sample;

/**
 * Created by bearden-tellez on 9/4/16.
 */
public class Stroke {
    Double x;
    Double y;
    Double strokeS;

    public Stroke(double strokeX, double strokeY, double strokeSize) {
        this.x = strokeX;
        this.y = strokeY;
        this.strokeS = strokeSize;
    }

    public Stroke() {
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getStrokeS() {
        return strokeS;
    }

    public void setStrokeS(Double strokeS) {
        this.strokeS = strokeS;
    }

}
