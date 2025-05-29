
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

* 🖥️ **Kullanıcı Dostu Arayüz:** JavaFX ile sade, şık ve kolay kullanılır bir ekran.
  
* 🔢 **Araç Girişi:** Dört yöne birden araç sayısı girilebiliyor, ya da sistem otomatik rastgele atıyor.
  
* ⏳ **Dinamik Işıklar:** Yeşil, sarı, kırmızı ışık süreleri, araç yoğunluğuna göre otomatik değişiyor.
  
* 🕒 **Dijital Geri Sayım:** Her ışığın üstünde, kalan süre dijital sayaçla gösteriliyor.
  
* 🚙💨 **Animasyon:** Araçlar ışıklara göre hareket ediyor ve kavşaktan geçtiğinde ekrandan kayboluyor.
  
* 🚧 **Çarpışma Önleme:** Araçların çarpışmaması için özel bir Çarpışma Önleyici isimli bir sınıf yazıldı.
  
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

## 💡 Trafik Işığı Kontrol ve Sinyal Zamanlama Mantığı
Projenin en önemli kısmı, trafik ışıklarının sürelerinin akıllıca ve adil şekilde dağıtılmasıdır.
Burada şu algoritma kullanılır:

🟢 Yeşil ışıklar saat yönünde sırayla yanar: Her seferinde sadece bir yönün ışığı yeşil olur; diğerleri kırmızıda bekler.

🔢 Her yönün yeşil ışık süresi, o yöndeki araç yoğunluğunun toplam araç sayısına oranına göre hesaplanır. (Yani, daha fazla aracı olan yöne daha uzun yeşil süre verilir.)

🕒 Toplam faz süresi sabittir (örn. 120 saniye). Her döngüde dört yön için yeşil ışıkların toplamı 120'yi geçmez.

🟨 Sarı ışık süresi sabittir (örn. 3 saniye) ve her yeşilden sonra yanar.

🟥 Kırmızı ışık süresi, ilgili yön için toplamdan yeşil ve sarı süreler çıkarılarak hesaplanır.

⏳ Bir yönün yeşil ışığı tekrar yanmadan önce, tüm ışıklar minimum 1 saniye kırmızıda bekler.

🔁 Yeşil ışık sırası: Sıra her zaman Kuzey → Doğu → Güney → Batı şeklinde döner.

#### 🔢 Örnek Süre Dağılımı

| 🚗 Yön | 🚦 Araç | 🟩 Yeşil Işık |
| ------ | ------- | ------------- |
| Kuzey  | 40      | 60 sn         |
| Güney  | 20      | 30 sn         |
| Doğu   | 10      | 15 sn         |
| Batı   | 10      | 15 sn         |

* Her yönün yeşil ışığı yandıktan sonra 3 saniye sarı ışık, ardından 1 saniye kırmızı ışık olur.
* Sonra saat yönünde sıradaki yöne geçilir; böylece her yön, yeşil ışığı tekrar almadan önce diğer yönlerin sırasını ve kendi kırmızı süresini bekler.


---
## 🖥️ Uygulama Kullanımı
🚦 Başlangıçta: Uygulama açıldığında dört yönlü kavşak ve trafik ışıkları ekranda yer alır.

🔢 Araç Girişi: Her yön için araç sayısı manuel girilebilir veya rastgele üret butonuyla otomatik oluşturulabilir.

▶️ Başlat: “Başlat” butonuna tıklanır; sistem girilen araç sayılarına göre yeşil ışık sürelerini hesaplar ve simülasyon başlar.

👀 Görsellik: Ekranda her trafik ışığı için kalan süre, araçların hareketleri ve durma-ilerleme animasyonları gerçek zamanlı olarak izlenir.

⏸️ Simülasyon Kontrolü: “Durdur”, “Devam” ve “Sıfırla” gibi kontrollerle simülasyon istenildiği gibi yönetilebilir.



---

## 📸 Ekran Görüntüleri

![image](https://github.com/user-attachments/assets/3467d539-2bbf-4e48-aad4-2b63814ce4c0)
![image](https://github.com/user-attachments/assets/952dd51d-04bc-4b60-ba2f-a6ff283cc8f7)
![image](https://github.com/user-attachments/assets/729a9007-3d82-436f-9053-d1369e20b94d)




---

## 👥 Takım Üyeleri

* 👩‍💻 **Beyzanur Aslan**
* 👩‍💻 **Ezgi Yücel**
* 👨‍💻 **Mustafa Semih Kaya**



---

