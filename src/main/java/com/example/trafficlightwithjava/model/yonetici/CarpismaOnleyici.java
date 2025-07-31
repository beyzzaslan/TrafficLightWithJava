package com.example.trafficlightwithjava.model.yonetici;

import com.example.trafficlightwithjava.model.Araba;
import com.example.trafficlightwithjava.model.Kavsak;
import com.example.trafficlightwithjava.model.TrafikIsigi;
import com.example.trafficlightwithjava.model.IsıkDurumTipi;
import com.example.trafficlightwithjava.model.Konum;
import com.example.trafficlightwithjava.model.YonTipi;
import java.util.List;

public class CarpismaOnleyici {
    final Kavsak kavsak;
    final List<Araba> aktifArabalar;
    final double kirmiziIsikDurmaMesafesi;
    final double arabaTakipMesafesi;

    final Konum kuzeyDurmaNoktasi;
    final Konum guneyDurmaNoktasi;
    final Konum doguDurmaNoktasi;
    final Konum batiDurmaNoktasi;

    private final double arabaGenisligi;
    private final double arabaYuksekligi;

    private static final double VARSAYILAN_BASLANGIC_HIZI = 150.0;
    private static final double YAVASLAMA_CARPANI = 0.9;
    private static final double MIN_HIZ_DURMA_ESIGI = 0.5;
    private static final double SON_DURMA_MESAFESI_ESIGI_HIZ_ORANI = 0.05;


    public CarpismaOnleyici(Kavsak kavsak, List<Araba> aktifArabalar,
                            double kirmiziIsikDurmaMesafesi, double arabaTakipMesafesi,
                            Konum kuzeyDurmaNoktasi, Konum guneyDurmaNoktasi,
                            Konum doguDurmaNoktasi, Konum batiDurmaNoktasi,
                            double arabaGenisligi, double arabaYuksekligi) {
        if (kavsak == null || aktifArabalar == null || kirmiziIsikDurmaMesafesi < 0 || arabaTakipMesafesi < 0 ||
                arabaGenisligi <= 0 || arabaYuksekligi <= 0) {
            throw new IllegalArgumentException("Geçersiz çarpışma önleyici parametreleri.");
        }
        this.kavsak = kavsak;
        this.aktifArabalar = aktifArabalar;
        this.kirmiziIsikDurmaMesafesi = kirmiziIsikDurmaMesafesi;
        this.arabaTakipMesafesi = arabaTakipMesafesi;
        this.kuzeyDurmaNoktasi = kuzeyDurmaNoktasi;
        this.guneyDurmaNoktasi = guneyDurmaNoktasi;
        this.doguDurmaNoktasi = doguDurmaNoktasi;
        this.batiDurmaNoktasi = batiDurmaNoktasi;
        this.arabaGenisligi = arabaGenisligi;
        this.arabaYuksekligi = arabaYuksekligi;
    }

    public void guncelle(double gecenSure) {
        for (Araba araba : aktifArabalar) {
            trafikIsigiKontrolu(araba);
            arabaTakipKontrolu(araba);
        }
    }

    private void trafikIsigiKontrolu(Araba araba) {
        TrafikIsigi ilgiliIsik = kavsak.getTrafikIsigiByYonTipi(araba.getYon());
        if (ilgiliIsik == null) {
            return;
        }
        Konum durmaNoktasi = getDurmaNoktasi(araba.getYon());
        if (durmaNoktasi == null) {
            return;
        }

        double mesafeDurmaNoktasina;
        switch (araba.getYon()) {
            case KUZEY:
                mesafeDurmaNoktasina = durmaNoktasi.getY() - (araba.getKonum().getY() + (this.arabaYuksekligi / 2.0));
                break;
            case GUNEY:
                mesafeDurmaNoktasina = (araba.getKonum().getY() - (this.arabaYuksekligi / 2.0)) - durmaNoktasi.getY();
                break;
            case DOGU:
                mesafeDurmaNoktasina = (araba.getKonum().getX() - (this.arabaGenisligi / 2.0)) - durmaNoktasi.getX();
                break;
            case BATI:
                mesafeDurmaNoktasina = durmaNoktasi.getX() - (araba.getKonum().getX() + (this.arabaGenisligi / 2.0));
                break;
            default:
                mesafeDurmaNoktasina = Double.MAX_VALUE;
        }
        if (isigiGectiMi(araba, durmaNoktasi)) {
            if (ilgiliIsik.getDurumTipi() == IsıkDurumTipi.RED) {
                System.out.println("UYARI: Araba " + araba.getId() + " kırmızı ışık ihlali yaptı!");
            }
            return; // durma kontrolü yapma, geçmesine izin ver
        }


        if ((ilgiliIsik.getDurumTipi() == IsıkDurumTipi.RED || ilgiliIsik.getDurumTipi() == IsıkDurumTipi.YELLOW) &&
                mesafeDurmaNoktasina <= kirmiziIsikDurmaMesafesi && mesafeDurmaNoktasina > 0) {
            if (mesafeDurmaNoktasina <= araba.getHiz() * SON_DURMA_MESAFESI_ESIGI_HIZ_ORANI) {
                araba.setHiz(0.0);
                araba.setKavsaktaMi(false);
            } else {
                araba.setHiz(araba.getHiz() * YAVASLAMA_CARPANI);
                if (araba.getHiz() < MIN_HIZ_DURMA_ESIGI) araba.setHiz(0.0);
            }

        } else if (ilgiliIsik.getDurumTipi() == IsıkDurumTipi.GREEN && araba.getHiz() == 0.0 && mesafeDurmaNoktasina <= 0) {
            araba.setHiz(VARSAYILAN_BASLANGIC_HIZI);
            araba.setKavsaktaMi(true);
        }
    }

    private void arabaTakipKontrolu(Araba takipEdenAraba) {//aracın önündeki diğer araçlarla çarpışmasını önlemek için hızını ayarlar
        Araba oneCikanAraba = null;
        double enYakinMesafe = Double.MAX_VALUE;

        for (Araba digerAraba : aktifArabalar) {
            if (takipEdenAraba.getId() == digerAraba.getId() || takipEdenAraba.getYon() != digerAraba.getYon()) {
                continue;
            }

            boolean isOneCikan = false;
            switch (takipEdenAraba.getYon()) {
                case KUZEY:
                    if (digerAraba.getKonum().getY() > takipEdenAraba.getKonum().getY()) isOneCikan = true;
                    break;
                case GUNEY:
                    if (digerAraba.getKonum().getY() < takipEdenAraba.getKonum().getY()) isOneCikan = true;
                    break;
                case DOGU:
                    if (digerAraba.getKonum().getX() < takipEdenAraba.getKonum().getX()) isOneCikan = true;
                    break;
                case BATI:
                    if (digerAraba.getKonum().getX() > takipEdenAraba.getKonum().getX()) isOneCikan = true;
                    break;
            }

            if (isOneCikan) {
                double mevcutBosluk;
                switch (takipEdenAraba.getYon()) {
                    case KUZEY:
                        mevcutBosluk = (digerAraba.getKonum().getY() - (this.arabaYuksekligi / 2.0)) - (takipEdenAraba.getKonum().getY() + (this.arabaYuksekligi / 2.0));
                        break;
                    case GUNEY:
                        mevcutBosluk = (takipEdenAraba.getKonum().getY() - (this.arabaYuksekligi / 2.0)) - (digerAraba.getKonum().getY() + (this.arabaYuksekligi / 2.0));
                        break;
                    case DOGU:
                        mevcutBosluk = (takipEdenAraba.getKonum().getX() - (this.arabaGenisligi / 2.0)) - (digerAraba.getKonum().getX() + (this.arabaGenisligi / 2.0));
                        break;
                    case BATI:
                        mevcutBosluk = (digerAraba.getKonum().getX() - (this.arabaGenisligi / 2.0)) - (takipEdenAraba.getKonum().getX() + (this.arabaGenisligi / 2.0));
                        break;
                    default:
                        mevcutBosluk = Double.MAX_VALUE;
                }

                if (mevcutBosluk < enYakinMesafe) {
                    enYakinMesafe = mevcutBosluk;
                    oneCikanAraba = digerAraba;
                }
            }
        }

        if (oneCikanAraba != null) {
            if (enYakinMesafe <= arabaTakipMesafesi) {
                double hedefHiz = oneCikanAraba.getHiz();
                if (takipEdenAraba.getHiz() > hedefHiz) {
                    takipEdenAraba.setHiz(Math.max(hedefHiz, takipEdenAraba.getHiz() * YAVASLAMA_CARPANI));
                    if (takipEdenAraba.getHiz() < MIN_HIZ_DURMA_ESIGI) takipEdenAraba.setHiz(0.0);
                } else if (takipEdenAraba.getHiz() < hedefHiz && enYakinMesafe > arabaTakipMesafesi * 1.5) {
                }

                if (enYakinMesafe < arabaTakipMesafesi * 0.5) {
                    takipEdenAraba.setHiz(takipEdenAraba.getHiz() * YAVASLAMA_CARPANI);
                    if (takipEdenAraba.getHiz() < MIN_HIZ_DURMA_ESIGI) takipEdenAraba.setHiz(0.0);
                }

            } else {
                if (takipEdenAraba.getHiz() == 0.0) {
                    TrafikIsigi ilgiliIsik = kavsak.getTrafikIsigiByYonTipi(takipEdenAraba.getYon());
                    if (ilgiliIsik != null && ilgiliIsik.getDurumTipi() == IsıkDurumTipi.GREEN) {
                        takipEdenAraba.setHiz(VARSAYILAN_BASLANGIC_HIZI);
                    }
                }
            }
        } else {
            if (takipEdenAraba.getHiz() == 0.0) {
                TrafikIsigi ilgiliIsik = kavsak.getTrafikIsigiByYonTipi(takipEdenAraba.getYon());
                if (ilgiliIsik != null && ilgiliIsik.getDurumTipi() == IsıkDurumTipi.GREEN) {
                    takipEdenAraba.setHiz(VARSAYILAN_BASLANGIC_HIZI);
                }
            }
        }
    }

    private boolean isigiGectiMi(Araba araba, Konum durmaNoktasi) {
        double arabaBurnu;
        switch (araba.getYon()) {
            case KUZEY:
                return (araba.getKonum().getY() + (this.arabaYuksekligi / 2.0)) > durmaNoktasi.getY();
            case GUNEY:
                return (araba.getKonum().getY() - (this.arabaYuksekligi / 2.0)) < durmaNoktasi.getY();
            case DOGU:
                return (araba.getKonum().getX() - (this.arabaGenisligi / 2.0)) < durmaNoktasi.getX();
            case BATI:
                return (araba.getKonum().getX() + (this.arabaGenisligi / 2.0)) > durmaNoktasi.getX();
            default:
                return false;
        }
    }

    private Konum getDurmaNoktasi(YonTipi yonTipi) {
        switch (yonTipi) {
            case KUZEY: return kuzeyDurmaNoktasi;
            case GUNEY: return guneyDurmaNoktasi;
            case DOGU: return doguDurmaNoktasi;
            case BATI: return batiDurmaNoktasi;
            default:
                throw new IllegalArgumentException("Geçersiz YönTipi: " + yonTipi);
        }
    }
}