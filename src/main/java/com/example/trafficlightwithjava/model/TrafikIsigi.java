package com.example.trafficlightwithjava.model;

public class TrafikIsigi {
    final Yon yon;
    IsıkDurumTipi durumTipi;
    int toplamSure;
    Zamanlayici zamanlayici;
    private double birikenSure=0.0;

    public static final int SARI_ISIK_SURESI=3;
    public static final int MIN_YESIL_SURE=0;
    public static final int MAX_YESIL_SURE=60;

    public TrafikIsigi(Yon yon) {
        if(yon==null)
            throw new IllegalArgumentException("Yon null bir değer olamaz.");
        this.yon = yon;
        this.toplamSure = 0;
        this.durumTipi=IsıkDurumTipi.RED;
        this.zamanlayici=new Zamanlayici(0);
    }
    public Yon getYon() {
        return yon;
    }
    public IsıkDurumTipi getDurumTipi() {
        return durumTipi;
    }
    private void setDurumTipi(IsıkDurumTipi durum){
        this.durumTipi=durum;
    }
    public int getToplamSure() {
        return toplamSure;
    }
    private void setToplamSure(int toplamSure) {
        if(toplamSure<0)
            throw new IllegalArgumentException("Sure negatif olamaz.");
        this.toplamSure = toplamSure;
    }
    public int getKalanSure(){
        return zamanlayici.getKalanSure();
    }
    public void yesilYap(int hesaplananYesilSure){
        int gecerliSure=Math.max(MIN_YESIL_SURE,Math.min(MAX_YESIL_SURE,hesaplananYesilSure));
        setDurumTipi(IsıkDurumTipi.GREEN);
        setToplamSure(gecerliSure);
        zamanlayici.sifirla(gecerliSure);
    }
    public void sariYap(){
        setDurumTipi(IsıkDurumTipi.YELLOW);
        setToplamSure(SARI_ISIK_SURESI);
        zamanlayici.sifirla(SARI_ISIK_SURESI);
    }
    public void kirmiziYap(int kirmiziSure){
        setDurumTipi(IsıkDurumTipi.RED);
        setToplamSure(kirmiziSure);
        zamanlayici.sifirla(kirmiziSure);
    }
    public void sureyiGuncelle(double gecenSure){
        birikenSure+=gecenSure;
        if(birikenSure>=1.0){
        zamanlayici.sureyiAzalt();//geri sayma işlemi
        birikenSure=0.0;}
    }
    public boolean fazBittiMi(){
        return zamanlayici.getKalanSure()<=0;
    }
    public static boolean yesilSureGecerliMi(int sure) {
        return sure >= MIN_YESIL_SURE && sure <= MAX_YESIL_SURE;
    }
    @Override
    public String toString() {
        return "TrafikIsigi[Yön=" + yon.getYonTipi() +
                ", Durum=" + durumTipi +
                ", Kalan Süre=" + zamanlayici.getKalanSure() + "]";
    }
}
