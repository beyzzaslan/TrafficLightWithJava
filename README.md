

# 🚦 Araç Yoğunluğuna Dayalı Akıllı Trafik Işığı Kontrol Sistemi

Bu proje, **araç yoğunluğuna göre trafik ışıklarının sürelerini otomatik olarak ayarlayan** akıllı bir kavşak simülasyonudur.
Java ve JavaFX kullanılarak geliştirilmiş, Model-View-Controller (MVC) mimarisiyle yapılandırılmıştır.
Amaç; kavşaktaki trafik akışını daha verimli ve güvenli hale getirmektir.

---

## 🎯 Proje Amaçları

* 🛣️ Dört yönlü (Kuzey, Güney, Doğu, Batı) kavşakta trafik simülasyonu oluşturmak
* 🔢 Araç yoğunluğunu **manuel** veya **rastgele** olarak girmek
* ⏱️ Yoğunluğa göre **yeşil ışık sürelerini dinamik şekilde ayarlamak**
* 🚗💨 Animasyonlu olarak, kavşaktaki araç hareketlerini ve ışık değişimlerini göstermek
* 🚦🚦 Araçların **kırmızı ışıkta beklemesi**, yeşilde hareket etmesi ve kavşaktan geçtikten sonra sistemden çıkarılması
* 🧩 Model-View-Controller (MVC) ve Nesne Yönelimli Programlama (OOP) prensiplerini uygulamak

---

## 🧩 Özellikler

* 🖥️ **Kullanıcı dostu ve görsel açıdan zengin arayüz** (JavaFX ile)
* 🔢 Dört yöne ait araç sayısının **manuel giriş** veya **rastgele üretim** seçenekleriyle belirlenmesi
* 🔄 **Dinamik olarak güncellenen yeşil, sarı ve kırmızı ışık süreleri**
* ⏳ Her ışık için ekranda **dijital sayaç** ile kalan sürenin gösterilmesi
* 🚙💨 **Animasyonlu araç hareketleri** ve trafik akışı
* 🚧 Araçların **çarpışmasını önleyen mantık**
* ⏱️ **Sabit toplam faz süresi** (ör. 120 saniye) ve **sabit sarı ışık süresi** (ör. 3 saniye)
* 🟩 **Her yön için yeşil ışık süresinin** minimum ve maksimum aralıklarda tutulması

---

## ⚙️ Kullanılan Teknolojiler

* ☕ **Java 17+**
* 🎨 **JavaFX**
* 🧩 **Mimari:** Model-View-Controller (MVC)
* 📚 **Koleksiyonlar:** List, Map (Java Collections Framework)
* 🚫 3. parti kütüphane kullanılmamıştır

---

## 💡 Sinyal Zamanlama Mantığı

* 🕒 **Toplam faz süresi:** 120 saniye (sabit)
* 🟩 **Yeşil ışık:** Her yönün yoğunluğuna orantılı süre (ör. %50 yoğunluk = 60 sn)
* 🟨 **Sarı ışık:** Sabit 3 saniye
* 🟩 **Yeşil süre sınırı:** En az 10, en fazla 60 saniye
* 🟥 **Kırmızı süre:** Toplamdan diğer fazlar çıkarılarak hesaplanır

#### 🔢 **Örnek Hesaplama:**

| 🚗 Yön | 🚦 Araç Sayısı | 📊 Yoğunluk (%) | 🟩 Yeşil Işık Süresi (sn) |
| :----: | :------------: | :-------------: | :-----------------------: |
|  Kuzey |       40       |       %50       |             60            |
|  Güney |       20       |       %25       |             30            |
|  Doğu  |       10       |      %12,5      |             15            |
|  Batı  |       10       |      %12,5      |             15            |

---

## 🖥️ Kullanım

1. 🚦 Uygulama başlatıldığında **dört yönlü kavşak** ve trafik ışıkları ekranda görüntülenir.
2. 🔢 Her bir yön için araç sayısını **elle girebilir** veya **rastgele üret** butonu ile otomatik oluşturabilirsiniz.
3. ▶️ **“Başlat”** butonuna tıkladığınızda, sistem yoğunluklara göre yeşil ışık sürelerini otomatik hesaplar ve simülasyonu başlatır.
4. 👀 Ekranda her ışık için kalan süreyi ve **araçların hareketlerini animasyonlu olarak** izleyebilirsiniz.
5. ⏸️ “Durdur”, ▶️ “Devam” ve 🔄 “Sıfırla” gibi kontrollerle simülasyonu yönetebilirsiniz.

---

## ⏳ Sinyal Süresi Hesaplama Mantığı

* 🕒 Toplam faz süresi sabittir (örn. 120 saniye).
* 🔢 Her yönün yeşil ışık süresi, o yöndeki araç yoğunluğunun toplam yoğunluğa oranı ile orantılı hesaplanır.
* 🟨 Sarı ışık süresi sabittir (örn. 3 saniye).
* 🟩 Her bir yeşil süresi, belirlenen min. ve maks. aralıklarla sınırlandırılır (örn. 10-60 saniye).
* 🟥 Kırmızı ışık süresi, ilgili fazda o yöne düşen toplam fazdan kalan süredir.
  
---

## 📷 Ekran Görüntüleri

![image](https://github.com/user-attachments/assets/d4d9b94d-62e5-4ce1-b1cf-bed6c6c37d5b)

---

## 💬 Takım Arkadaşları

* 👩‍💻 **Beyzanur Aslan**
* 👩‍💻 **Ezgi Yücel**
* 👨‍💻 **Mustafa Semih Kaya**

---



