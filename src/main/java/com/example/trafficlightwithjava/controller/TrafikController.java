package com.example.trafficlightwithjava.controller;

import com.example.trafficlightwithjava.model.TrafikIsigi;

import java.util.ArrayList;
import java.util.List;

public class TrafikController {
    List<TrafikIsigi> trafikIsiklari;

    public TrafikController() {
        trafikIsiklari = new ArrayList<TrafikIsigi>();
    }
    public void trafikIsigiEkle(TrafikIsigi trafikIsigi) {
        trafikIsiklari.add(trafikIsigi);
    }
    public void sinyalSureleriniHesapla()
    {
        final int YESIL_ISIK_SURESİ_TOPLAM=120;
        final int SARI_ISIK=3;
        int toplamYogunluk=0;

        for(TrafikIsigi isik: trafikIsiklari){
            toplamYogunluk+=isik.getYon().getAracYogunlugu().getSayi();
        }
        if(toplamYogunluk==0)return;
        for(TrafikIsigi isik:trafikIsiklari)
        {
            int yogunluk=isik.getYon().getAracYogunlugu().getSayi();
            double oran=(double)yogunluk/toplamYogunluk;

            int yesilSure=(int)(oran*YESIL_ISIK_SURESİ_TOPLAM);
            yesilSure=Math.max(10,Math.min(yesilSure,60));

            int toplamSure=yesilSure+SARI_ISIK;

         //   isik.setToplamSure((toplamSure));
            //isik.getZamanlayici().sifirla(toplamSure);
        }

    }
}
