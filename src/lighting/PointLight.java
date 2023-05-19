package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {


    private Point position;
    private double kC = 1, kL = 0, kQ = 0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight setPosition(Point position) {
        this.position = position;
        return this;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }


    public Color getIntensity(Point p){
        double distance = p.distance(position);
        return getIntensity().scale (1/(kC+(kL*distance)+kQ*distance*distance));
    }

    public Vector getL(Point p){
            return p.subtract(position).normalize();
        }
    }
