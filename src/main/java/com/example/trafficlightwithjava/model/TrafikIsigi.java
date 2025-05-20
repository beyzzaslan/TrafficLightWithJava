package com.example.trafficlightwithjava.model;

public class TrafikIsigi {
    Yon yon;
    IsıkDurumTipi durumTipi;
    int toplamSure;
    Zamanlayici zamanlayici;

    public TrafikIsigi(Yon yon,int toplamSure) {
        this.yon = yon;
        this.toplamSure = toplamSure;
        this.durumTipi=IsıkDurumTipi.RED;
        this.zamanlayici=new Zamanlayici(toplamSure);
    }
    public Yon getYon() {
        return yon;
    }
    public IsıkDurumTipi getDurumTipi() {
        return durumTipi;
    }
    public void setDurumTipi(IsıkDurumTipi durum){
        this.durumTipi=durum;
    }
    public void setYon(Yon yon) {
        this.yon = yon;
    }
    public void setToplamSure(int toplamSure) {
        this.toplamSure = toplamSure;
    }
    public int getKalanSure(){
        return zamanlayici.getKalanSure();
    }
    //eksikler var eklenecek

}
