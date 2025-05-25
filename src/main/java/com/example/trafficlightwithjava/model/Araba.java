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
        this.hiz = 1.0;//?emin değilim
        this.konum =baslangicKonumu;
        kavsaktaMi=true;
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
    public void hareketEt()
    {
        if(this.hiz==0.0)
            return;
        switch(this.yon){
            case KUZEY :
                this.konum.setY(this.konum.getY()-this.hiz);
                break;
            case GÜNEY:
                this.konum.setY(konum.getY()+this.hiz);
                break;
            case DOĞU:
                this.konum.setX(konum.getX()+this.hiz);
                break;
            case BATI:
                this.konum.setX(konum.getX()-this.hiz);
                break;
        }
    }
    @Override
    public String toString() {
        return "Araba[ID="+id+",Yön="+yon+",Hız="+hiz+",Konum="+konum+",Kavşakta mı="+kavsaktaMi+"]";
    }
}
