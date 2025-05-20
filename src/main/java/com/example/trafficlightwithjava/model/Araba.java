package com.example.trafficlightwithjava.model;

public class Araba {
    Yon yon;
    int hiz;
    Konum konum;

    public Araba(Yon yon) {
        this.yon = yon;
        this.hiz = 0;
        this.konum = new Konum(0,0);
    }
    public Konum getKonum() {
        return konum;
    }
    public void setKonum(Konum konum) {
        this.konum = konum;
    }

    public void setYon(Yon yon) {
        this.yon = yon;
    }

    public Yon getYon() {
        return yon;
    }

    public void setHiz(int hiz) {
        this.hiz = hiz;
    }
    public int getHiz() {
        return hiz;
    }
    public void hareketEt()
    {
        //....
    }

}
