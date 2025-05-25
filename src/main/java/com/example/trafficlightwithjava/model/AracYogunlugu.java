package com.example.trafficlightwithjava.model;

public class AracYogunlugu {
    int sayi;

    public AracYogunlugu(int sayi) {
        if(sayi<0)
            throw new IllegalArgumentException("Araç sayısı negatif olamaz.");
        this.sayi = sayi;
    }

    public void setSayi(int sayi) {
        if(sayi<0)
            throw new IllegalArgumentException("Araç sayisi negatif olamaz.");
        this.sayi = sayi;
    }

    public int getSayi(){
        return this.sayi;
    }
}
