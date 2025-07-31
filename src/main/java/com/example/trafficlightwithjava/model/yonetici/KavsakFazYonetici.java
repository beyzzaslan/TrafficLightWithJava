package com.example.trafficlightwithjava.model.yonetici;

import com.example.trafficlightwithjava.model.Kavsak;
import com.example.trafficlightwithjava.model.TrafikIsigi;
import com.example.trafficlightwithjava.model.IsıkDurumTipi;
import com.example.trafficlightwithjava.model.Yon;
import com.example.trafficlightwithjava.model.YonTipi;

import java.util.List;

public class KavsakFazYonetici {//ışıkların durum değiştirmesi
    final Kavsak kavsak;//1 kere kavşak üetilecek ve değiştirilmeyecek.
    public final SureHesaplayici sureHesaplayici;
    final List<Yon> yonler;
    int[] kirmiziSureleri;
    int[] yesilSureleri;
    boolean sariBeklemede = false;

    int mevcutFazIndex;//hangi faz aktif yeşil-sarı-kırmızı gibi
    double fazSayaci;//o anki fazın kaç saniyedir sürdüğünü tutacak

    final YonTipi[] fazlar = {//hangi sırayla yeşil yanacağı
            YonTipi.KUZEY,
            YonTipi.DOGU,
            YonTipi.GUNEY,
            YonTipi.BATI
    };

    static final int KIRMIZI_ARAC_GECIS_SURESI = 1; //tüm ışıkların kırmızı kaldığı ara süre
    static final int BASLANGIC_KIRMIZI_SURESI = 3;

    public KavsakFazYonetici(Kavsak kavsak, SureHesaplayici sureHesaplayici, List<Yon> yonler) {
        if (kavsak == null || sureHesaplayici == null || yonler == null || yonler.isEmpty()) {
            throw new IllegalArgumentException("Kavşak, süre hesaplayıcı ve yönler boş olamaz.");
        }
        this.kavsak = kavsak;
        this.sureHesaplayici = sureHesaplayici;
        this.yonler = yonler;

        this.mevcutFazIndex = 0;
        this.fazSayaci = 0;

    }
    public void guncelle(double gecenSure) {
        fazSayaci += gecenSure;//o anki fazın süresini başlatmış olduk
        YonTipi aktifYonTipi = fazlar[mevcutFazIndex];//şuan olduğumuz rengi alıyoruz.
        TrafikIsigi aktifIsik = kavsak.getTrafikIsigiByYonTipi(aktifYonTipi);//hangi ışık aktifse onu alıyoruz.
        if (aktifIsik != null) {
            aktifIsik.sureyiGuncelle(gecenSure);
        }//aktif bir ışık varsa süresini güncelliyor.

        for (TrafikIsigi isik : kavsak.getTrafikIsiklari()) {
            if (isik != aktifIsik) { //burada ise sadece yeşil olanı değilde tüm timerları güncellemeye çalışacağız.
                isik.sureyiGuncelle(gecenSure);
            }
        }
        if (!sariBeklemede && fazSayaci >= yesilSureleri[aktifYonTipi.ordinal()]) {
            // Yeşilden sarıya geç
            aktifIsik.sariYap();
            sariBeklemede = true;
            fazSayaci = 0;
            System.out.println("SARI faz başladı: " + aktifYonTipi);
        } else if (sariBeklemede && fazSayaci >= TrafikIsigi.SARI_ISIK_SURESI + KavsakFazYonetici.KIRMIZI_ARAC_GECIS_SURESI) {
            sonrakiFazaGec();
            sariBeklemede = false;
            fazSayaci = 0;
        }
    }
    public double getMevcutFazSuresi() {
        YonTipi anaYon = fazlar[mevcutFazIndex];
        int yesilSure = yesilSureleri[anaYon.ordinal()];
        return (double)yesilSure + TrafikIsigi.SARI_ISIK_SURESI + KIRMIZI_ARAC_GECIS_SURESI;
    }
    public double getFazSayaci(){
        return fazSayaci;
    }
    public YonTipi getAktifYonTipi() {
        return fazlar[mevcutFazIndex];
    }
    public YonTipi[] getFazlar(){
        return fazlar;
    }

    public void ilkFaziKur() {
        mevcutFazIndex = 0;
        fazSayaci = 0;

        for (YonTipi tip : YonTipi.values()) {
            TrafikIsigi isik = kavsak.getTrafikIsigiByYonTipi(tip);
            if (isik != null) {
                isik.kirmiziYap(BASLANGIC_KIRMIZI_SURESI); //başlangıçta tüm ışıkları 3 sn kırmızı yaptık
            }
        }
        YonTipi ilkYon = fazlar[mevcutFazIndex];
        TrafikIsigi ilkIsik = kavsak.getTrafikIsigiByYonTipi(ilkYon);
        if (ilkIsik != null) {
            int yesilSure = yesilSureleri[ilkYon.ordinal()];
            ilkIsik.yesilYap(yesilSure);
            System.out.println("İlk faz başladı: " + ilkYon.name() + " Yeşil. Süre: " + yesilSure + "sn");//ilk sıradaki ışığı yeşil yaptık
        }
    }

    private void sonrakiFazaGec() {
        YonTipi mevcutYesilYon = fazlar[mevcutFazIndex];
        TrafikIsigi mevcutIsik = kavsak.getTrafikIsigiByYonTipi(mevcutYesilYon);
        if (mevcutIsik != null) {
            mevcutIsik.sariYap();
        }
        System.out.println("Faz " + mevcutFazIndex + " (" + mevcutYesilYon.name() + ") Sarıya döndü. Kalan süre: " + TrafikIsigi.SARI_ISIK_SURESI + "sn");
        mevcutFazIndex = (mevcutFazIndex + 1) % fazlar.length; // Bir sonraki faza geç, döngüsel olarak

        for (YonTipi tip : YonTipi.values()) {
            TrafikIsigi tumIsik = kavsak.getTrafikIsigiByYonTipi(tip);
            if (tumIsik != null) {
                tumIsik.kirmiziYap(KIRMIZI_ARAC_GECIS_SURESI);
            }
        }
        fazSayaci = 0;

        YonTipi yeniYesilYon = fazlar[mevcutFazIndex];
        TrafikIsigi yeniIsik = kavsak.getTrafikIsigiByYonTipi(yeniYesilYon);
        if (yeniIsik != null) {
            int hesaplananYesilSure = yesilSureleri[yeniYesilYon.ordinal()];
            yeniIsik.yesilYap(hesaplananYesilSure);
            System.out.println("Yeni faz başladı: " + yeniYesilYon.name() + " Yeşil. Süre: " + sureHesaplayici.yesilSureleriHesapla()[yeniYesilYon.ordinal()] + "sn");
        }
    }

    public Kavsak getKavsak() {
        return kavsak;
    }
    public List<Yon> getYonList() {
        return yonler;
    }

    public void simuulasyonuSifirla() {
        mevcutFazIndex = 0;
        fazSayaci = 0;
        for (TrafikIsigi isik : kavsak.getTrafikIsiklari()) {
            isik.kirmiziYap(BASLANGIC_KIRMIZI_SURESI);
        }
        System.out.println("Kavşak Faz Yöneticisi sıfırlandı. Başlangıç kırmızı süresi ayarlandı.");
    }
    public int getKirmiziKalanSure(YonTipi yonTipi){
        int kirmiziToplam=kirmiziSureleri[yonTipi.ordinal()];
        return (int)(kirmiziToplam-fazSayaci);
    }
    public void hazirlikYap() {
        this.yesilSureleri = sureHesaplayici.yesilSureleriHesapla();
        this.kirmiziSureleri = sureHesaplayici.kirmiziBeklemeSuresi(yesilSureleri);
    }
}