package com.example.trafficlightwithjava.controller;

import com.example.trafficlightwithjava.model.*;
import com.example.trafficlightwithjava.model.yonetici.*;
import com.example.trafficlightwithjava.view.*;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class TrafficLightController {

    // --- Model Katmanı Referansları ---
    private Kavsak kavsak;
    private AracYoneticisi aracYoneticisi;
    private KavsakFazYonetici kavsakFazYonetici;
    private CarpismaOnleyici carpismaOnleyici;
    private SureHesaplayici sureHesaplayici;

    // --- View Katmanı Referansları ---
    private Arayuz arayuz;
    private IntersectionView intersectionView;
    private InputPanel inputPanel;

    // --- Simülasyon Yönetimi ---
    private AnimationTimer gameLoop;
    private long lastUpdateTime;
    private boolean isSimulationRunning;
    private boolean isSimulationPaused;

    // --- Araba Modeli ve Görünümü Arasındaki Eşleşme ---
    private Map<Araba, ArabaView> arabaViewMap;

    // --- Çapraz Konumlandırma Sabitleri ---
    // Bu değerler IntersectionView'deki yol ve ışık konumlarına göre ayarlandı
    private static final int INTERSECTION_WIDTH = 1000;
    private static final int INTERSECTION_HEIGHT = 1000;
    private static final int ROAD_WIDTH = 100;

    // Model için giriş/durma konumları (IntersectionView'deki konumlarla eşleşmeli)
    // Bu değerleri IntersectionView'deki mantığa göre merkezi olarak tanımlıyoruz
    // Kavşağın merkezinden ROAD_WIDTH/2 kadar kaydırılmış X veya Y değerleri
    private final Konum KUZEY_GIRIS_KONUM = new Konum(INTERSECTION_WIDTH / 2.0 - ROAD_WIDTH / 4.0, 0); // KUZEY arabaları için giriş yolu
    private final Konum GUNEY_GIRIS_KONUM = new Konum(INTERSECTION_WIDTH / 2.0 + ROAD_WIDTH / 4.0, INTERSECTION_HEIGHT); // GÜNEY arabaları için giriş yolu
    private final Konum DOGU_GIRIS_KONUM = new Konum(INTERSECTION_WIDTH, INTERSECTION_HEIGHT / 2.0 - ROAD_WIDTH / 4.0); // DOĞU arabaları için giriş yolu
    private final Konum BATI_GIRIS_KONUM = new Konum(0, INTERSECTION_HEIGHT / 2.0 + ROAD_WIDTH / 4.0); // BATI arabaları için giriş yolu

    // Durma noktaları (Işıkların önünde duracakları tahmini noktalar)
    // Bunlar da ışıkların konumuna ve araba boyutlarına göre hesaplanmalı
    private final Konum KUZEY_DURMA_NOKTASI = new Konum(INTERSECTION_WIDTH / 2.0 - ROAD_WIDTH / 4.0, INTERSECTION_HEIGHT / 2.0 - ROAD_WIDTH / 2.0 - TrafikIsigi.SARI_ISIK_SURESI); // IntersectionView'daki ışık konumuna göre ayarlandı
    private final Konum GUNEY_DURMA_NOKTASI = new Konum(INTERSECTION_WIDTH / 2.0 + ROAD_WIDTH / 4.0, INTERSECTION_HEIGHT / 2.0 + ROAD_WIDTH / 2.0 + TrafikIsigi.SARI_ISIK_SURESI); // IntersectionView'daki ışık konumuna göre ayarlandı
    private final Konum DOGU_DURMA_NOKTASI = new Konum(INTERSECTION_WIDTH / 2.0 + ROAD_WIDTH / 2.0 + TrafikIsigi.SARI_ISIK_SURESI, INTERSECTION_HEIGHT / 2.0 - ROAD_WIDTH / 4.0); // IntersectionView'daki ışık konumuna göre ayarlandı
    private final Konum BATI_DURMA_NOKTASI = new Konum(INTERSECTION_WIDTH / 2.0 - ROAD_WIDTH / 2.0 - TrafikIsigi.SARI_ISIK_SURESI, INTERSECTION_HEIGHT / 2.0 + ROAD_WIDTH / 4.0); // IntersectionView'daki ışık konumuna göre ayarlandı

    public TrafficLightController() {
        // 1. View Bileşenlerini Oluşturma
        arayuz = new Arayuz();
        intersectionView = arayuz.getIntersectionView();
        inputPanel = arayuz.getInputPanel();

        // 2. Model Bileşenlerini Oluşturma
        // Kavşak merkezi ve boyutları
        Konum kavsakMerkezi = new Konum(INTERSECTION_WIDTH / 2.0, INTERSECTION_HEIGHT / 2.0);
        kavsak = new Kavsak(kavsakMerkezi, ROAD_WIDTH, ROAD_WIDTH); // Kavşağın kendisi bir kare alan olarak düşünebiliriz

        // Yönleri oluştur (SureHesaplayici ve KavsakFazYonetici için)
        Yon kuzeyYon = new Yon(YonTipi.KUZEY);
        Yon guneyYon = new Yon(YonTipi.GUNEY);
        Yon doguYon = new Yon(YonTipi.DOGU);
        Yon batiYon = new Yon(YonTipi.BATI);
        List<Yon> yonler = new ArrayList<>();
        yonler.add(kuzeyYon);
        yonler.add(guneyYon);
        yonler.add(doguYon);
        yonler.add(batiYon);

        sureHesaplayici = new SureHesaplayici(yonler);
        kavsakFazYonetici = new KavsakFazYonetici(kavsak, sureHesaplayici, yonler);

        aracYoneticisi = new AracYoneticisi(
                KUZEY_GIRIS_KONUM, GUNEY_GIRIS_KONUM, DOGU_GIRIS_KONUM, BATI_GIRIS_KONUM,
                0, INTERSECTION_HEIGHT, 0, INTERSECTION_WIDTH, // Ekran sınırları
                ArabaView.ARABA_GENISLIGI, ArabaView.ARABA_YUKSEKLIGI // Araba boyutları
        );

        arabaViewMap = new HashMap<>();

        // Çarpışma önleyiciyi oluştur
        carpismaOnleyici = new CarpismaOnleyici(
                kavsak,
                aracYoneticisi.getAktifArabalar(), // AracYoneticisi'nin aktif arabalar listesi
                100.0, // Kırmızı ışıkta durma mesafesi (örnek değer)
                ArabaView.ARABA_GENISLIGI + 5.0, // Araba takip mesafesi (kendi genişliği + boşluk)
                KUZEY_DURMA_NOKTASI, GUNEY_DURMA_NOKTASI, DOGU_DURMA_NOKTASI, BATI_DURMA_NOKTASI,
                ArabaView.ARABA_GENISLIGI, ArabaView.ARABA_YUKSEKLIGI
        );

        // 3. Simülasyon Döngüsünü Oluşturma
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isSimulationRunning && !isSimulationPaused) {
                    double gecenSure = (now - lastUpdateTime) / 1_000_000_000.0; // Saniye cinsinden
                    lastUpdateTime = now;
                    guncelleSimulasyon(gecenSure);
                }
            }
        };

        // 4. Event Handler'ları Ayarlama (InputPanel'den gelen olaylar)
        setupEventHandlers();

        // Başlangıçta simülasyon duraklatılmış veya başlatılmamış durumda olacak
        isSimulationRunning = false;
        isSimulationPaused = false;
        lastUpdateTime = System.nanoTime(); // İlk zamanı başlat
    }

    public Parent getRoot() {
        return arayuz.getRoot();
    }

    private void setupEventHandlers() {
        // Kullanıcı Manuel veya Rastgele giriş yaptığında
        inputPanel.setOnApplyListener(counts -> {
            // Önceki tüm arabaları temizle (simülasyonu sıfırlamak için)
            aracYoneticisi.tumArabalariTemizle();
            intersectionView.getArabaKatmani().getChildren().clear();
            arabaViewMap.clear();

            // Yeni araçları kuyruğa ekle
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                YonTipi yonTipi = YonTipi.valueOf(entry.getKey());
                int count = entry.getValue();

                // Modeldeki Yon nesnesinin araç yoğunluğunu güncelle
                // Ve araçları AracYoneticisi'nin kendi iç kuyruklarına ekle
                // (AracYoneticisi'nin topluAraclariKuyrugaEkle metodu zaten YonTipi'ne göre arabaları ekliyor)
                aracYoneticisi.topluAraclariKuyrugaEkle(yonTipi, count);

                // İlgili Yon nesnesinin AracYogunlugu'nu güncelle
                // (AracYoneticisi'nin kuyruk yönetimi ayrı, Yon objesinin yoğunluğu ayrı tutuluyor)
                // Bu kısım Model tutarlılığı için önemlidir.
                // Yon listesi üzerinden ilgili Yon objesini bulup yoğunluğunu set etmeliyiz.
                // Aksi takdirde SureHesaplayici yanlış veriyle çalışır.
                for (Yon yon : kavsakFazYonetici.getYonList()) { // KavsakFazYonetici içindeki yonler listesine eriştik
                    if (yon.getYonTipi() == yonTipi) {
                        yon.getAracYogunlugu().setSayi(count);
                        break;
                    }
                }
            }

            if (!isSimulationRunning) {
                startSimulation();
            } else {
                // Eğer zaten çalışıyorsa, sadece kuyrukları güncelledik, simülasyon devam eder.
                // Ancak başlangıç ışık fazını tekrar ayarlayabiliriz.
                kavsakFazYonetici.simuulasyonuSifirla(); // Işıkları başlangıç kırmızı durumuna getirir
            }
        });

        // Simülasyonu başlatma/devam ettirme
        inputPanel.setOnStartSimulationListener(() -> {
            if (!isSimulationRunning) {
                startSimulation();
            } else if (isSimulationPaused) {
                isSimulationPaused = false;
                lastUpdateTime = System.nanoTime(); // Duraklatmadan sonra zamanı sıfırla
            }
        });

        // Simülasyonu sıfırlama
        inputPanel.setOnResetListener(() -> {
            stopSimulation(); // Önce durdur
            aracYoneticisi.tumArabalariTemizle(); // Tüm araçları modelden temizle
            intersectionView.getArabaKatmani().getChildren().clear(); // Tüm araç görünümlerini View'den temizle
            arabaViewMap.clear(); // Eşleşme haritasını temizle
            kavsakFazYonetici.simuulasyonuSifirla(); // Işık faz yöneticisini sıfırla (tüm ışıkları kırmızı yapar)
            // Tüm ışıkların View'ini başlangıç durumuna getir
            for (TrafikIsigi modelIsik : kavsak.getTrafikIsiklari()) {
                TrafficLight viewIsik = getTrafficLightViewForModel(modelIsik.getYon().getYonTipi());
                if (viewIsik != null) {
                    viewIsik.updateState(IsıkDurumTipi.RED); // Başlangıçta tüm ışıklar kırmızı
                    viewIsik.getTimerDisplay().updateTime(TrafikIsigi.SARI_ISIK_SURESI); // Varsayılan bir süre gösterebiliriz (örneğin Sarı ışık süresi)
                    viewIsik.getTimerDisplay().setTextColor(Color.RED); // Kırmızı renk
                }
            }
            inputPanel.hideSimulationControls(); // Butonları gizle
        });

        // Simülasyonu durdurma
        inputPanel.setOnStopListener(() -> {
            isSimulationPaused = true;
            System.out.println("Simülasyon Duraklatıldı.");
        });

        // Simülasyonu devam ettirme
        inputPanel.setOnContinueListener(() -> {
            if (isSimulationPaused) {
                isSimulationPaused = false;
                lastUpdateTime = System.nanoTime(); // Duraklatmadan sonra zamanı sıfırla
                System.out.println("Simülasyon Devam Ediyor.");
            }
        });
    }

    private void startSimulation() {
        if (!isSimulationRunning) {
            isSimulationRunning = true;
            isSimulationPaused = false;
            lastUpdateTime = System.nanoTime(); // Simülasyon başladığında zamanı sıfırla
            kavsakFazYonetici.simuulasyonuSifirla(); // İlk fazı başlat (tüm ışıklar kırmızı)
            gameLoop.start();
            System.out.println("Simülasyon Başlatıldı.");
        }
    }

    private void stopSimulation() {
        isSimulationRunning = false;
        isSimulationPaused = false;
        gameLoop.stop();
        System.out.println("Simülasyon Durduruldu.");
    }

    private void guncelleSimulasyon(double gecenSure) {
        // 1. Model Güncellemeleri (Simülasyon Mantığı)
        aracYoneticisi.guncelle(gecenSure); // Arabaları oluştur/hareket ettir
        carpismaOnleyici.guncelle(gecenSure); // Çarpışmaları önle (hız ayarı)
        kavsakFazYonetici.guncelle(gecenSure); // Işık fazlarını yönet

        // 2. View Güncellemeleri (Görsel Yansıtma)

        // a) Trafik Işıklarını Güncelle
        for (TrafikIsigi modelIsik : kavsak.getTrafikIsiklari()) {
            TrafficLight viewIsik = getTrafficLightViewForModel(modelIsik.getYon().getYonTipi());
            if (viewIsik != null) {
                // Işık durumunu View'e aktar
                viewIsik.updateState(modelIsik.getDurumTipi());

                // Zamanlayıcıyı View'e aktar
                viewIsik.getTimerDisplay().updateTime(modelIsik.getKalanSure());

                // Zamanlayıcı metin rengini ışık durumuna göre ayarla
                Color timerColor;
                switch (modelIsik.getDurumTipi()) {
                    case GREEN:
                        timerColor = Color.LIMEGREEN;
                        break;
                    case RED:
                        timerColor = Color.RED;
                        break;
                    case YELLOW:
                        timerColor = Color.YELLOW;
                        break;
                    default:
                        timerColor = Color.BLACK; // Varsayılan
                        break;
                }
                viewIsik.getTimerDisplay().setTextColor(timerColor);
            }
        }

        // b) Arabaları Güncelle (Ekle, Hareket Ettir, Kaldır)
        // Yeni arabaları ekle
        for (Araba modelAraba : aracYoneticisi.getAktifArabalar()) {
            if (!arabaViewMap.containsKey(modelAraba)) {
                // Rastgele renk ataması
                Color randomColor = Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
                ArabaView yeniArabaView = new ArabaView(
                        modelAraba.getId(),
                        randomColor,
                        modelAraba.getKonum(), // İlk konum
                        ArabaView.ARABA_GENISLIGI,
                        ArabaView.ARABA_YUKSEKLIGI
                );
                arabaViewMap.put(modelAraba, yeniArabaView);
                intersectionView.getArabaKatmani().getChildren().add(yeniArabaView);
            }
        }

        // Mevcut arabaların konumlarını güncelle
        for (Map.Entry<Araba, ArabaView> entry : arabaViewMap.entrySet()) {
            Araba modelAraba = entry.getKey();
            ArabaView arabaView = entry.getValue();
            arabaView.updatePosition(modelAraba.getKonum());
        }

        // Ekrandan çıkan arabaları temizle
        Iterator<Map.Entry<Araba, ArabaView>> iterator = arabaViewMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Araba, ArabaView> entry = iterator.next();
            Araba modelAraba = entry.getKey();
            ArabaView arabaView = entry.getValue();

            // Eğer modeldeki aktif arabalar listesinde bu araba yoksa, View'den de kaldır
            if (!aracYoneticisi.getAktifArabalar().contains(modelAraba)) {
                intersectionView.getArabaKatmani().getChildren().remove(arabaView);
                iterator.remove(); // Haritadan kaldır
            }
        }
    }

    // Yardımcı metot: Modeldeki YonTipi'ne karşılık gelen View'deki TrafficLight nesnesini bulur
    private TrafficLight getTrafficLightViewForModel(YonTipi yonTipi) {
        switch (yonTipi) {
            case KUZEY: return intersectionView.getLightNorth();
            case GUNEY: return intersectionView.getLightSouth();
            case DOGU:  return intersectionView.getLightEast();
            case BATI:  return intersectionView.getLightWest();
            default: return null;
        }
    }
}