package com.example.trafficlightwithjava.model;

import java.util.LinkedList;
import java.util.Queue;

    public class Yon {
    YonTipi yonTipi;
    AracYogunlugu aracYogunlugu;
    Queue<Araba> arabalar;

    public Yon(YonTipi yonTipi){
        this.yonTipi = yonTipi;
        this.aracYogunlugu=new AracYogunlugu(0);
        this.arabalar=new LinkedList<>();
    }

    public YonTipi getYonTipi() {
        return yonTipi;
    }
    public void setYonTipi(YonTipi yonTipi) {
        this.yonTipi = yonTipi;
    }
    public AracYogunlugu getAracYogunlugu() {
        return aracYogunlugu;
    }
    public void setAracYogunlugu(AracYogunlugu yogunluk) {
        this.aracYogunlugu = yogunluk;
    }
    public void arabaEkle(Araba araba){
        this.arabalar.offer(araba);
        this.aracYogunlugu.setSayi(this.arabalar.size());//yoğunluğu otomatik güncellemek için
    }
    public Araba arabaCikar(){
        Araba cikanAraba=this.arabalar.poll();
        this.aracYogunlugu.setSayi(this.arabalar.size()); // Yoğunluğu otomatik günceller
        return cikanAraba;
    }
    public int getArabaSayisi(){
        return this.arabalar.size();
    }
    public Queue<Araba> getArabalar(){
        return this.arabalar;}
    public void tumArabalariTemizle(){
        this.arabalar.clear();
        this.aracYogunlugu.setSayi(0);
    }
    @Override
    public String toString(){
        return "Yon[tip="+yonTipi+",yoğunluk="+aracYogunlugu.getSayi()+",arabaSayisi="+arabalar.size()+"]";
    }
}
