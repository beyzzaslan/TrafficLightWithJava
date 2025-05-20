package com.example.trafficlightwithjava.model;

public class ModelTest {public static void main(String[] args) {

    System.out.println("===== MODEL TEST BAŞLIYOR =====");

    // 1. Yön oluştur
    Yon kuzey = new Yon("Kuzey");
    System.out.println("Yön adı: " + kuzey.getYonAdi());

    // 2. Araba oluştur ve yönle ilişkilendir
    Araba araba1 = new Araba(kuzey);
    araba1.setHiz(2);
    kuzey.arabaEkle(araba1);

    // 3. Araba kuyruğu testi
    System.out.println("Yön'deki araba sayısı: " + kuzey.getArabaSayisi());

    // 4. Trafik ışığı oluştur
    TrafikIsigi isik = new TrafikIsigi(kuzey, 15);
    System.out.println("Işık durumu: " + isik.getDurumTipi());
    System.out.println("Işık kalan süresi: " + isik.getKalanSure());

    // 5. Zamanlayıcıyı test et (5 saniye azalt)
    for (int i = 0; i < 5; i++) {
        isik.zamanlayici.sureyiAzalt();
        System.out.println("Azaltıldı -> Yeni kalan süre: " + isik.getKalanSure());
    }

    // 6. Arabanın hareketi
    Konum konumOnce = araba1.getKonum();
    System.out.println("Arabanın ilk konumu: x=" + konumOnce.getX() + ", y=" + konumOnce.getY());

    araba1.hareketEt(); // şu an boş, istersen bir ilerleme yazabilirim

    // 7. Araba kuyruktan çıkarılıyor
    Araba cikan = kuzey.arabaCikar();
    System.out.println("Kuyruktan çıkan araba yönü: " + cikan.getYon().getYonAdi());
    System.out.println("Kuyrukta kalan: " + kuzey.getArabaSayisi());

    System.out.println("===== TEST TAMAMLANDI =====");
}
}
