package com.example.trafficlightwithjava.model.yonetici;

import com.example.trafficlightwithjava.model.Araba;
import com.example.trafficlightwithjava.model.Konum;
import com.example.trafficlightwithjava.model.YonTipi;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class AracYoneticisi {
    List<Araba> aktifArabalar;

    private Queue<Araba> kuzeyKuyrugu;
    private Queue<Araba> guneyKuyrugu;
    private Queue<Araba> doguKuyrugu;
    private Queue<Araba> batiKuyrugu;

    double olusturmaSayaci;
    static final double ARABA_OLUSTURMA_ARALIGI = 0.5;

    final Konum kuzeyGirisKonum;
    final Konum guneyGirisKonum;
    final Konum doguGirisKonum;
    final Konum batiGirisKonum;

    final double ekranAltSinir;
    final double ekranUstSinir;
    final double ekranSolSinir;
    final double ekranSagSinir;

    private final double arabaGenisligi;
    private final double arabaYuksekligi;

    private static final double ARABA_TAKIP_MESAFESI = 5.0;

    public AracYoneticisi(Konum kuzeyGirisKonum, Konum guneyGirisKonum,
                          Konum doguGirisKonum, Konum batiGirisKonum,
                          double ekranUstSinir, double ekranAltSinir,
                          double ekranSolSinir, double ekranSagSinir,
                          double arabaGenisligi, double arabaYuksekligi) {
        this.aktifArabalar = new ArrayList<>();

        this.kuzeyKuyrugu = new LinkedList<>();
        this.guneyKuyrugu = new LinkedList<>();
        this.doguKuyrugu = new LinkedList<>();
        this.batiKuyrugu = new LinkedList<>();

        this.olusturmaSayaci = 0;

        this.kuzeyGirisKonum = kuzeyGirisKonum;
        this.guneyGirisKonum = guneyGirisKonum;
        this.doguGirisKonum = doguGirisKonum;
        this.batiGirisKonum = batiGirisKonum;

        this.ekranUstSinir = ekranUstSinir;
        this.ekranAltSinir = ekranAltSinir;
        this.ekranSolSinir = ekranSolSinir;
        this.ekranSagSinir = ekranSagSinir;

        this.arabaGenisligi = arabaGenisligi;
        this.arabaYuksekligi = arabaYuksekligi;
    }

    public void guncelle(double gecenSure) {
        olusturmaSayaci += gecenSure;

        if (olusturmaSayaci >= ARABA_OLUSTURMA_ARALIGI) {
            denemeYeniAracOlustur();
            olusturmaSayaci -= ARABA_OLUSTURMA_ARALIGI;
        }

        hareketEdenArabalariGuncelle(gecenSure);
        temizleGecenArabalari();
    }

    public void topluAraclariKuyrugaEkle(YonTipi yonTipi, int toplamOlusturulacakSayi) {
        if (toplamOlusturulacakSayi < 0) {
            throw new IllegalArgumentException("Araç sayısı negatif olamaz.");
        }
        if (toplamOlusturulacakSayi == 0) {
            return;
        }

        Queue<Araba> hedefKuyruk;
        switch (yonTipi) {
            case KUZEY:
                hedefKuyruk = kuzeyKuyrugu;
                break;
            case GUNEY:
                hedefKuyruk = guneyKuyrugu;
                break;
            case DOGU:
                hedefKuyruk = doguKuyrugu;
                break;
            case BATI:
                hedefKuyruk = batiKuyrugu;
                break;
            default:
                throw new IllegalArgumentException("Geçersiz YönTipi: " + yonTipi);
        }

        for (int i = 0; i < toplamOlusturulacakSayi; i++) {
            hedefKuyruk.add(new Araba(yonTipi, new Konum(0, 0)));
        }
        System.out.println(yonTipi + " yönü için " + toplamOlusturulacakSayi + " araç kuyruğa eklendi. Kuyruk boyutu: " + hedefKuyruk.size());
    }

    private void denemeYeniAracOlustur() {
        denemeYeniAracOlusturTekYon(YonTipi.KUZEY, kuzeyKuyrugu);
        denemeYeniAracOlusturTekYon(YonTipi.GUNEY, guneyKuyrugu);
        denemeYeniAracOlusturTekYon(YonTipi.DOGU, doguKuyrugu);
        denemeYeniAracOlusturTekYon(YonTipi.BATI, batiKuyrugu);
    }

    private void denemeYeniAracOlusturTekYon(YonTipi yonTipi, Queue<Araba> kuyruk) {
        if (!kuyruk.isEmpty()) {
            Araba sonArabaGirisAlaninda = getSonArabaGirisAlaninda(yonTipi);

            boolean yerVarMi = false;
            if (sonArabaGirisAlaninda == null) {
                yerVarMi = true;
            } else {
                Konum girisKonumuObjesi = getGirisKonumuByYonTipi(yonTipi);
                double mesafe;

                double gerekliBosluk;
                if (yonTipi == YonTipi.KUZEY || yonTipi == YonTipi.GUNEY) {
                    gerekliBosluk = this.arabaYuksekligi + ARABA_TAKIP_MESAFESI;
                } else { // DOGU, BATI
                    gerekliBosluk = this.arabaGenisligi + ARABA_TAKIP_MESAFESI;
                }

                switch (yonTipi) {
                    case KUZEY:
                        mesafe = sonArabaGirisAlaninda.getKonum().getY() + (this.arabaYuksekligi / 2.0) - girisKonumuObjesi.getY();
                        break;
                    case GUNEY:
                        mesafe = girisKonumuObjesi.getY() - (sonArabaGirisAlaninda.getKonum().getY() - (this.arabaYuksekligi / 2.0));
                        break;
                    case DOGU:
                        mesafe = girisKonumuObjesi.getX() - (sonArabaGirisAlaninda.getKonum().getX() - (this.arabaGenisligi / 2.0));
                        break;
                    case BATI:
                        mesafe = sonArabaGirisAlaninda.getKonum().getX() + (this.arabaGenisligi / 2.0) - girisKonumuObjesi.getX();
                        break;
                    default:
                        mesafe = 0;
                }

                if (mesafe >= gerekliBosluk) {
                    yerVarMi = true;
                }
            }

            if (yerVarMi) {
                Araba yeniAraba = kuyruk.poll();
                if (yeniAraba != null) {
                    Konum baslangicKonumuObjesi = getGirisKonumuByYonTipi(yonTipi);

                    double baslangicX = baslangicKonumuObjesi.getX();
                    double baslangicY = baslangicKonumuObjesi.getY();

                    switch (yonTipi) {
                        case KUZEY:
                            baslangicY = baslangicKonumuObjesi.getY() - (this.arabaYuksekligi / 2.0);
                            break;
                        case GUNEY:
                            baslangicY = baslangicKonumuObjesi.getY() + (this.arabaYuksekligi / 2.0);
                            break;
                        case DOGU:
                            baslangicX = baslangicKonumuObjesi.getX() + (this.arabaGenisligi / 2.0);
                            break;
                        case BATI:
                            baslangicX = baslangicKonumuObjesi.getX() - (this.arabaGenisligi / 2.0);
                            break;
                    }

                    yeniAraba.setKonum(new Konum(baslangicX, baslangicY));
                    aktifArabalar.add(yeniAraba);
                    System.out.println("Kuyruktan yeni araba simülasyona alındı (" + yonTipi + "). Kuyrukta kalan: " + kuyruk.size());
                }
            }
        }
    }


    private Araba getSonArabaGirisAlaninda(YonTipi yonTipi) {
        Araba enGeridekiAraba = null;

        for (Araba araba : aktifArabalar) {
            if (araba.getYon() == yonTipi) {
                if (enGeridekiAraba == null) {
                    enGeridekiAraba = araba;
                } else {
                    switch (yonTipi) {
                        case KUZEY:
                            if (araba.getKonum().getY() < enGeridekiAraba.getKonum().getY()) {
                                enGeridekiAraba = araba;
                            }
                            break;
                        case GUNEY:
                            if (araba.getKonum().getY() > enGeridekiAraba.getKonum().getY()) {
                                enGeridekiAraba = araba;
                            }
                            break;
                        case DOGU:
                            if (araba.getKonum().getX() > enGeridekiAraba.getKonum().getX()) {
                                enGeridekiAraba = araba;
                            }
                            break;
                        case BATI:
                            if (araba.getKonum().getX() < enGeridekiAraba.getKonum().getX()) {
                                enGeridekiAraba = araba;
                            }
                            break;
                    }
                }
            }
        }
        return enGeridekiAraba;
    }


    private Konum getGirisKonumuByYonTipi(YonTipi yonTipi) {
        switch (yonTipi) {
            case KUZEY: return this.kuzeyGirisKonum;
            case GUNEY: return this.guneyGirisKonum;
            case DOGU:  return this.doguGirisKonum;
            case BATI:  return this.batiGirisKonum;
            default: return null;
        }
    }

    private void hareketEdenArabalariGuncelle(double gecenSure) {
        for (Araba araba : aktifArabalar) {
            araba.hareketEt(gecenSure);
        }
    }

    private void temizleGecenArabalari() {
        aktifArabalar.removeIf(araba -> {
            boolean ekranDisindaMi = false;
            switch (araba.getYon()) {
                case KUZEY:
                    if (araba.getKonum().getY() - (this.arabaYuksekligi / 2.0) > this.ekranAltSinir) ekranDisindaMi = true;
                    break;
                case GUNEY:
                    if (araba.getKonum().getY() + (this.arabaYuksekligi / 2.0) < this.ekranUstSinir) ekranDisindaMi = true;
                    break;
                case DOGU:
                    if (araba.getKonum().getX() + (this.arabaGenisligi / 2.0) < this.ekranSolSinir) ekranDisindaMi = true;
                    break;
                case BATI:
                    if (araba.getKonum().getX() - (this.arabaGenisligi / 2.0) > this.ekranSagSinir) ekranDisindaMi = true;
                    break;
            }
            if (ekranDisindaMi) {
                System.out.println("Araba simülasyondan çıkarıldı (ID: " + araba.getId() + ")");
            }
            return ekranDisindaMi;
        });
    }

    public List<Araba> getAktifArabalar() {
        return aktifArabalar;
    }

    public void tumArabalariTemizle() {
        aktifArabalar.clear();
        kuzeyKuyrugu.clear();
        guneyKuyrugu.clear();
        doguKuyrugu.clear();
        batiKuyrugu.clear();
        System.out.println("Tüm arabalar ve bekleyen kuyruklar simülasyondan temizlendi.");
    }
}