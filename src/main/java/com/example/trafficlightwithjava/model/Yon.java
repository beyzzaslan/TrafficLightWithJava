package com.example.trafficlightwithjava.model;

import java.util.LinkedList;
import java.util.Queue;

    public class Yon {
    String yonAdi;
    AracYogunlugu aracYogunlugu;
    Queue<Araba> arabalar;

    public Yon(String yonAdi){
        this.yonAdi = yonAdi;
        this.aracYogunlugu=new AracYogunlugu(0);
        this.arabalar=new LinkedList<>();
    }

    public String getYonAdi() {
        return yonAdi;
    }

    public void setYonAdi(String yonAdi) {
        this.yonAdi = yonAdi;
    }

    public AracYogunlugu getAracYogunlugu() {
        return aracYogunlugu;
    }

    public void setAracYogunlugu(AracYogunlugu yogunluk) {
        this.aracYogunlugu = yogunluk;
    }

    public void arabaEkle(Araba araba){
        this.arabalar.add(araba);
    }
    public Araba arabaCikar(){
        return this.arabalar.poll();
    }
    public int getArabaSayisi(){
        return this.arabalar.size();
    }
    //?
}
