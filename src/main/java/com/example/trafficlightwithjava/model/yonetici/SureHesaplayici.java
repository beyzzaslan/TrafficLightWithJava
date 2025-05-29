package com.example.trafficlightwithjava.model.yonetici;

import com.example.trafficlightwithjava.model.TrafikIsigi;
import com.example.trafficlightwithjava.model.Yon;
import com.example.trafficlightwithjava.model.YonTipi;

import java.util.List;

public class SureHesaplayici {
    final List<Yon> yonler;
    static final int YESIL_TOPLAM_DONGU_SURESİ=120;
    static final int SABIT_SARI_SURE= TrafikIsigi.SARI_ISIK_SURESI;
    static final int KIRMIZI_ARA_SURE=1;//bir yöndeki ışık kırmızı olduktan sonra diğer yöndeki ışığın yeşil olmadan tüm ışıkların kırmızı kaldığı saniye gibi düşündüm  birbirne çarpmama açısında mantıklı olabilir.

    public SureHesaplayici(List<Yon> yonler){
        this.yonler = yonler;
    }
    public int[] yesilSureleriHesapla(){
        int[] yesilSureleri = new int[YonTipi.values().length];//4 elemanlı bir dizi olmuş oldu.

        int toplamAracSayisi=0;
        for(Yon yon : yonler){
            toplamAracSayisi+=yon.getAracYogunlugu().getSayi();
        }//tüm yönlerdeki araçların sayısını toplamış oldum bu sayede

        if(toplamAracSayisi==0) {
            for (int i = 0; i < yesilSureleri.length; i++)
                yesilSureleri[i] = 0;
            return yesilSureleri;
        }//eğer hiç araç yoksa her bir yeşil ışığa 0 değeri verilmiş oldu.

        int yesilKullanilabilirSure=YESIL_TOPLAM_DONGU_SURESİ;

        for(Yon yon: yonler){
            YonTipi tip=yon.getYonTipi();//tüm yönlerin tipini almış olduk
            int aracSayisi=yon.getAracYogunlugu().getSayi();//o yöndeki araç sayısı

            if(aracSayisi==0){
                yesilSureleri[tip.ordinal()] = 0;//o yönde araç yoksa o yöne yeşil ışık süresi olarak 0 verdik.
            }else{
                double yuzde=(double)aracSayisi/toplamAracSayisi;
                int hesaplananSure=(int)Math.round(yuzde*yesilKullanilabilirSure);//

                int minSureBuYonIcin=10;
                hesaplananSure=Math.max(minSureBuYonIcin,Math.min(TrafikIsigi.MAX_YESIL_SURE,hesaplananSure));
                yesilSureleri[tip.ordinal()] = hesaplananSure;
            }
        }
        int gecerliSureToplami=0;
        for(int sure : yesilSureleri){
            gecerliSureToplami+=sure;
        }
        int fark=yesilKullanilabilirSure-gecerliSureToplami;
        if(fark!=0){
            int hedefIndex=-1;
            if(fark>0){
                int minSure = Integer.MAX_VALUE;
                for (int i = 0; i < yesilSureleri.length; i++) {
                    if (yonler.get(YonTipi.values()[i].ordinal()).getAracYogunlugu().getSayi() > 0) {
                        if (yesilSureleri[i] < minSure) {
                            minSure = yesilSureleri[i];
                            hedefIndex = i;
                        }
                    }
                }
            }else {
                int maxSure = Integer.MIN_VALUE;
                for (int i = 0; i < yesilSureleri.length; i++) {
                    if (yonler.get(YonTipi.values()[i].ordinal()).getAracYogunlugu().getSayi() > 0) {
                        if (yesilSureleri[i] > maxSure) {
                            maxSure = yesilSureleri[i];
                            hedefIndex = i;
                        }
                    }
                }
            }
            if (hedefIndex != -1 && yonler.get(YonTipi.values()[hedefIndex].ordinal()).getAracYogunlugu().getSayi() > 0) {
                int hedefYonSuresi = yesilSureleri[hedefIndex];
                hedefYonSuresi += fark;
                int minSureBuYonIcin = 10;
                hedefYonSuresi = Math.max(minSureBuYonIcin, Math.min(TrafikIsigi.MAX_YESIL_SURE, hedefYonSuresi));

                yesilSureleri[hedefIndex] = hedefYonSuresi;
            }else{
                System.err.println("Yuvarlama farkı (" + fark + " s) hiç aracı olmayan bir yöne atanamadı. Göz ardı edildi.");
            }
        }
        return yesilSureleri;
    }
    public int[] kirmiziBeklemeSuresi(int[] yesilSureleri) {
        int[] kirmiziSureleri = new int[yesilSureleri.length];
        YonTipi[] fazSirasi={
                YonTipi.KUZEY,
                YonTipi.DOGU,
                YonTipi.GUNEY,
                YonTipi.BATI,
        };

        for(int i=0;i<fazSirasi.length;i++){
            int toplam=0;
            for(int j=0;j<fazSirasi.length;j++){
                if(i==j) break;

                YonTipi oncekiYon=fazSirasi[j];
                int yesil=yesilSureleri[oncekiYon.ordinal()];
                toplam+=yesil+TrafikIsigi.SARI_ISIK_SURESI+1;
            }
            kirmiziSureleri[fazSirasi[i].ordinal()]=toplam;
        }
        return kirmiziSureleri;
    }
}
