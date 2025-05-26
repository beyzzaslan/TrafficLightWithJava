package com.example.trafficlightwithjava.model;

import java.util.ArrayList;
import java.util.List;

public class Kavsak {
    Konum merkezKonum;//kavşağın x y koordinatındaki yerini almak için
    double genislik;
    double yukseklik;

    List<TrafikIsigi> trafikIsiklari;

    public Kavsak(Konum merkezKonum, double genislik, double yukseklik) {
        if (merkezKonum == null) {
            throw new IllegalArgumentException("Merkez konum null olamaz.");
        }
        if (genislik <= 0 || yukseklik <= 0) {
            throw new IllegalArgumentException("Genişlik ve yükseklik pozitif olmalıdır.");
        }
        this.merkezKonum = merkezKonum;
        this.genislik = genislik;
        this.yukseklik = yukseklik;
        this.trafikIsiklari = new ArrayList<>();

        for (YonTipi tip : YonTipi.values()) {
            this.trafikIsiklari.add(new TrafikIsigi(new Yon(tip)));//4 tarafa da ışık üretip listeye ekliyoruz.
        }
    }

    public List<TrafikIsigi> getTrafikIsiklari() {
        return trafikIsiklari;
    }

    public TrafikIsigi getTrafikIsigiByYonTipi(YonTipi yonTipi) {
        for (TrafikIsigi isik : trafikIsiklari) {
            if (isik.getYon().getYonTipi() == yonTipi) {
                return isik;
            }
        }
        return null;
    }

    public Konum getMerkezKonum() {
        return merkezKonum;
    }

    public double getGenislik() {
        return genislik;
    }

    public double getYukseklik() {
        return yukseklik;
    }
}