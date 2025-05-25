package com.example.trafficlightwithjava.model;

public class Araba {
    static int nextID=0;
    final int id;
    YonTipi yon;
    double hiz;
    Konum konum;
    boolean kavsaktaMi;

    public Araba(YonTipi yon,Konum baslangicKonumu) {
        this.id=nextID++;
        this.yon = yon;
        this.hiz = 80.0;//?emin değilim
        this.konum =baslangicKonumu;
        kavsaktaMi=false;
    }
    public int getId(){
        return id;
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
    public void setHiz(double hiz) {
        if(hiz<0)
            throw new IllegalArgumentException("Hiz negatif olamaz.");
        this.hiz = hiz;
    }
    public double getHiz() {
        return hiz;
    }
    public void setKavsaktaMi(boolean kavsaktaMi){
        this.kavsaktaMi = kavsaktaMi;}
    public boolean getKavsaktaMi(){
        return kavsaktaMi;
    }
    public void hareketEt(double gecenSure)
    {
        if(this.hiz==0.0)
            return;
        double katedilenMesafe=this.hiz*gecenSure;
        switch(this.yon){
            case KUZEY :
                this.konum.setY(this.konum.getY()+katedilenMesafe);
                break;
            case GUNEY:
                this.konum.setY(konum.getY()-katedilenMesafe);
                break;
            case DOGU:
                this.konum.setX(konum.getX()-katedilenMesafe);
                break;
            case BATI:
                this.konum.setX(konum.getX()+katedilenMesafe);
                break;
        }
    }
    @Override
    public String toString() {
        return "Araba[ID="+id+",Yön="+yon+",Hız="+hiz+",Konum="+konum.toString()+",Kavşakta mı="+kavsaktaMi+"]";
    }
}
