
# 🚦 Araç Yoğunluğuna Duyarlı Akıllı Trafik Işığı Kontrol Sistemi

Bu projede, dört yol kavşağında trafik ışıklarının sürelerini, o anki **araç yoğunluğuna göre otomatik** ayarlayan bir simülasyon geliştirdik. Amacımız, kavşaktaki trafiği daha **akıcı, adil ve güvenli** bir hale getirmek. Her şey Java & JavaFX ile, kullanıcı dostu ve görsel olarak zengin bir arayüzle hazırlandı!

---

## 🎯 Proje Amaçları

* 🛣️ **Gerçekçi Kavşak Simülasyonu:** Kuzey, güney, doğu ve batıdan araçlar geliyor, her şey animasyonlu!
* 🔢 **Esnek Araç Girişi:** Araç sayılarını ister **manuel** gir, ister **rastgele üret** butonuyla keyfine bak!
* ⏱️ **Dinamik Yeşil Işık:** Her yönün yeşil ışık süresi, araç yoğunluğuna göre otomatik ayarlanıyor.
* 🚗💨 **Animasyonlu Akış:** Araçlar hareket ediyor, ışıklar değişiyor, her şey ekranda canlanıyor!
* 🚦 **Doğru Işık Yönetimi:** Araçlar sadece kendi ışığı yeşilken geçiyor, kırmızıda kesinlikle bekliyor.
* 🧩 **Temiz Kod ve Yapı:** Kodun tamamı MVC mimarisine uygun ve sürdürülebilir.

---

## 🧩 Temel Özellikler

* 🖥️ **Kullanıcı Dostu Arayüz:** JavaFX ile sade, şık ve kolay kullanılır bir ekran
* 🔢 **Araç Girişi:** Dört yöne birden araç sayısı girilebiliyor, ya da sistem otomatik rastgele atıyor!
* ⏳ **Dinamik Işıklar:** Yeşil, sarı, kırmızı ışık süreleri, araç yoğunluğuna göre otomatik değişiyor.
* 🕒 **Dijital Geri Sayım:** Her ışığın üstünde, kalan süre dijital sayaçla gösteriliyor.
* 🚙💨 **Animasyon:** Araçlar ışıklara göre hareket ediyor ve kavşaktan geçtiğinde ekrandan kayboluyor.
* 🚧 **Çarpışma Önleme:** Araçların çarpışmaması için özel mantık kullanıldı.
* ⏰ **Sabit Toplam Süre:** Her tur toplam süre sabit (ör. 120 sn). Sarı ışıklar ise her zaman 3 saniye.
* 🟩 **Yeşil Süre Sınırı:** Her yön için yeşil ışık minimum ve maksimum aralıkta tutuluyor (ör. 10-60 sn).

---

## ⚙️ Kullanılan Teknolojiler

* ☕ **Java 17+**
* 🎨 **JavaFX**
* 🧩 **MVC mimarisi**
* 📚 **List, Map** gibi Java koleksiyonları
* 🚫 **Ekstra kütüphane yok**, sadece Java'nın standart kütüphaneleri

---

## 💡 Trafik Işığı Sırası ve Zamanlama Mantığı

* 🟢 **Yeşil ışıklar sırayla ve saat yönünde yanar**: (Kuzey → Doğu → Güney → Batı)
* 🛑 **Aynı anda sadece bir yönde yeşil ışık** yanar, diğerleri bekler.
* 🔢 **Yeşil ışık süresi, o yöndeki araç sayısının toplam içindeki oranına göre dağıtılır** (yani çok aracı olan yöne daha fazla yeşil ışık!)
* ⏱️ **Toplam yeşil süreler** her turda 120 saniyeyi aşmaz.
* 🟨 **Her yeşilden sonra 3 saniye sarı ışık**, sonra kısa bir süre (1 sn) kırmızı ışık olur.
* 🔁 Sıra tekrar başa döner, döngü devam eder.

#### 🔢 Örnek Süre Dağılımı

| 🚗 Yön | 🚦 Araç | 🟩 Yeşil Işık |
| ------ | ------- | ------------- |
| Kuzey  | 40      | 60 sn         |
| Güney  | 20      | 30 sn         |
| Doğu   | 10      | 15 sn         |
| Batı   | 10      | 15 sn         |

---

## 🖥️ Nasıl Kullanılır?

1. 🚦 **Başlatınca** ekranda dört yönlü kavşak ve trafik ışıkları gözükür.
2. 🔢 Her yön için araç sayılarını **elle gir** ya da **rastgele üret** butonuna bas.
3. ▶️ **“Başlat”** dediğinde sistem, yoğunluklara göre süreleri hesaplayıp simülasyonu başlatır.
4. 👀 Ekranda ışıklar, kalan süreler ve araç hareketleri animasyonlu olarak gözükür.
5. ⏸️ **“Durdur”, “Devam” ve “Sıfırla”** ile simülasyonu kontrol edebilirsin.

---

## 📸 Ekran Görüntüleri

![image](https://github.com/user-attachments/assets/a4c63d49-7626-42b2-9e51-12b352bbd6ca)

---

## 👥 Takım Üyeleri

* 👩‍💻 **Beyzanur Aslan**
* 👩‍💻 **Ezgi Yücel**
* 👨‍💻 **Mustafa Semih Kaya**

---

