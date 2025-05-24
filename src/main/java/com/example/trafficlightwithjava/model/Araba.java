package com.example.trafficlightwithjava.model;

public class Araba {
    YonTipi yon;
    double hiz;
    Konum konum;
    boolean kavsaktaMi;

    public Araba(YonTipi yon,Konum baslangicKonumu) {
        this.yon = yon;
        this.hiz = 0.0;//?emin değilim
        this.konum =baslangicKonumu;
        kavsaktaMi=true;
    }
    public Konum getKonum() {
        return konum;
    }
    public void setKonum(Konum konum) {
        this.konum = konum;
    }

    public void setYon(YonTipi yon) {
        this.yon = yon;
    }

    public YonTipi getYon() {
        return yon;
    }

    public void setHiz(int hiz) {
        this.hiz = hiz;
    }
    public double getHiz() {
        return hiz;
    }
    public void hareketEt()
    {
    }
    public void setKavsaktaMi(boolean kavsaktaMi){
        this.kavsaktaMi = kavsaktaMi;
    }
    public boolean getKavsaktaMi(){return kavsaktaMi;}

}
