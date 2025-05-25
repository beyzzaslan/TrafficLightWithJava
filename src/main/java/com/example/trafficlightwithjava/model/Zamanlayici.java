package com.example.trafficlightwithjava.model;

public class Zamanlayici {
    int kalanSure;

    public Zamanlayici(int sure) {
        if(sure<0)
            throw new IllegalArgumentException("Zamanlayici suresi negatif olamaz.");
        this.kalanSure = sure;
    }
    public void sureyiAzalt()
    {if(this.kalanSure>0)
        this.kalanSure--;
    }
    public void sifirla(int yeniSure){
        if(yeniSure<0)
            throw new IllegalArgumentException("Yeni zamanlayici süresi negatif olamaz.");
        this.kalanSure=yeniSure;
    }
    public int getKalanSure() {

        return kalanSure;
    }
    public void setKalanSure(int kalanSure) {
        if(kalanSure<0)
            throw new IllegalArgumentException("Kalan süre negatif olamaz.");
        this.kalanSure=kalanSure;}

}
