package com.example.trafficlightwithjava.model;

public class Zamanlayici {
    int kalanSure;

    public Zamanlayici(int sure) {
        this.kalanSure = sure;
    }
    public void sureyiAzalt()
    {if(this.kalanSure>0)
        this.kalanSure--;
    }
    public void sifirla(int yeniSure){
        this.kalanSure=yeniSure;
    }
    public int getKalanSure() {
        return kalanSure;
    }
    public void setKalanSure(int kalanSure) {this.kalanSure=kalanSure;}

}
