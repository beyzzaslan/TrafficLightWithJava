package com.example.trafficlightwithjava.model;

public class Konum {
    double x;
    double y;

    public Konum(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {

        return x;
    }
    public void setX(double x) {

        this.x = x;
    }
    public double getY() {

        return y;
    }
    public void setY(double y) {

        this.y = y;
    }
    public double mesafeHesapla(Konum digerKonum) {
        if (digerKonum == null) {
            throw new IllegalArgumentException("Mesafe hesaplanacak diğer konum null olamaz.");
        }
        double deltaX = this.x - digerKonum.x;
        double deltaY = this.y - digerKonum.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    @Override
    public String toString() {
        return "Konum("+x+","+y+")";
    }
}
