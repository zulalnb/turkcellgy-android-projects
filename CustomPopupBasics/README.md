# Custom Popup Basics

Bu proje, Android uygulamalarında varsayılan sistem diyalogları yerine özel tasarımlı popup pencereleri oluşturmayı ele alır.

Android'in `PopupWindow` sınıfı kullanılarak XML ile tasarlanan bir uyarı penceresi ekrana gösterilir. Popup içerisinde başlık, açıklama ve iki farklı işlem butonu bulunur. Kullanıcı işlemi onaylayabilir veya iptal edebilir.

## Ekran Görüntüsü

<p align="center">
    <img src="./screenshots/app.gif" alt="Custom Popup uygulaması" width="300">
</p>

## Ele Alınan Konular

- `PopupWindow` kullanımı
- XML ile özel popup tasarımı
- `LayoutInflater` ile layout oluşturma
- `Gravity` ile popup konumlandırma
- `MATCH_PARENT` ile pencere boyutlandırma
- `PopupWindow.dismiss()` ile popup kapatma
- Lambda expressions ve callback fonksiyonları
- `findViewById()` ile popup bileşenlerine erişme

## 1. PopupWindow Nedir?

`PopupWindow`, Android'de mevcut Activity arayüzünün üzerinde ayrı bir pencere göstermek için kullanılan bir sınıftır.

Popup penceresinin görünümü XML layout ile tasarlanabilir. Böylece standart `AlertDialog` görünümünden farklı, uygulamanın tasarım diline uygun arayüzler oluşturulabilir.

Bu projede `PopupWindow` ile tam ekran bir pencere oluşturulmuş ve arka plan koyu, yarı saydam bir katmanla kaplanmıştır.

## 2. Popup Tasarımını XML ile Oluşturmak

Popup arayüzü `res/layout/popup_warning.xml` dosyasında tanımlanmıştır.

Dıştaki `ConstraintLayout`, tüm ekranı kaplayacak şekilde tasarlanmıştır:

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#990F172A"
    android:padding="24dp">
```

`#990F172A` rengi, alfa kanalı sayesinde yarı saydam koyu bir arka plan oluşturur. Böylece popup açıldığında arkadaki Activity görünür kalır ancak görsel olarak geri planda bırakılır.

İçerideki `popupCard` ise beyaz arka planlı bir kart görünümüdür:

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/popupCard"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:background="@color/white"
    android:padding="24dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent">
```

Bu bileşen yatayda ebeveyninin genişliğini kullanır ve dikeyde içerik kadar yer kaplar. Dört kenara uygulanan Constraint bağlantıları sayesinde ekranın ortasında konumlanır.

Popup kartının içinde şu bileşenler bulunur:

- `ImageView`: Uyarı ikonu.
- `TextView`: Popup başlığı.
- `TextView`: Açıklama metni.
- `Button`: İşlemi iptal etmek için.
- `Button`: İşlemi onaylamak için.

Butonlarda Material Components özellikleriyle arka plan rengi, köşe yuvarlaklığı ve ripple efekti özelleştirilmiştir.

## 3. LayoutInflater ile Popup Görünümünü Oluşturmak

Popup gösterilmeden önce XML layout'unun View nesnesine dönüştürülmesi gerekir. Bunun için `LayoutInflater` kullanılır.

```kotlin
val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
val popupView = inflater.inflate(R.layout.popup_warning, null)
```

`inflate()` metodu, `popup_warning.xml` dosyasını gerçek bir View hiyerarşisine dönüştürür.

İkinci parametre olan `null`, bu aşamada bir parent View verilmediğini ifade eder. Popup'ın boyut ve konumlandırma işlemleri daha sonra `PopupWindow` üzerinden yapılır.

## 4. PopupWindow Oluşturmak

XML'den oluşturulan View, `PopupWindow` nesnesine aktarılır:

```kotlin
val warningPopup = PopupWindow(
    popupView,
    WindowManager.LayoutParams.MATCH_PARENT,
    WindowManager.LayoutParams.MATCH_PARENT
)
```

Constructor parametreleri:

1. **Content View:** Popup içerisinde gösterilecek View.
2. **Width:** Popup genişliği.
3. **Height:** Popup yüksekliği.

Bu projede hem genişlik hem de yükseklik `MATCH_PARENT` olarak ayarlanmıştır. Böylece popup penceresi ekranın tamamını kaplar.

Kodda ayrıca eski `windowManager.defaultDisplay.width` ve `height` yaklaşımına ait bir örnek yorum satırında bırakılmıştır. Bu yöntem deprecated olarak işaretlendiği için aktif uygulamada `MATCH_PARENT` tercih edilmiştir.

## 5. Popup İçerisindeki Bileşenlere Erişmek

Popup'ın içindeki buton ve metin alanları, Activity'nin layout'undan değil, popup için inflate edilen `popupView` üzerinden bulunur.

```kotlin
val tvPopupTitle = popupView.findViewById<TextView>(
    R.id.tvPopupTitle
)

val tvPopupMessage = popupView.findViewById<TextView>(
    R.id.tvPopupMessage
)

val btnPopupCancel = popupView.findViewById<Button>(
    R.id.btnPopupCancel
)

val btnPopupConfirm = popupView.findViewById<Button>(
    R.id.btnPopupConfirm
)
```

Başlık ve açıklama metinleri fonksiyon parametrelerinden alınarak atanır:

```kotlin
tvPopupTitle.text = title
tvPopupMessage.text = message
```

Böylece popup'ın görünümü sabit kalırken başlık ve mesaj farklı çağrılarda değiştirilebilir.

## 6. Popup'ı Ekranda Göstermek

Popup'ın ekranda görüntülenmesi için `showAtLocation()` metodu kullanılır:

```kotlin
warningPopup.showAtLocation(
    popupView,
    Gravity.CENTER,
    0,
    0
)
```

Parametreler:

- **Parent View:** Popup'ın konumlandırılacağı pencerenin referans View nesnesi.
- **Gravity:** Popup'ın konumlandırılacağı alan.
- **X ve Y Offset:** Konuma uygulanacak yatay ve dikey uzaklık.

`Gravity.CENTER`, popup'ın ekranın ortasında gösterilmesini sağlar. X ve Y değerleri `0` olduğu için ek bir konum kaydırması uygulanmaz.

## 7. Popup'ı Kapatmak: dismiss()

Kullanıcı popup üzerindeki butonlardan birine bastığında pencere `dismiss()` ile kapatılır.

İptal butonu:

```kotlin
btnPopupCancel.setOnClickListener {
    warningPopup.dismiss()
}
```

Onay butonu:

```kotlin
btnPopupConfirm.setOnClickListener {
    onConfirm()
    warningPopup.dismiss()
}
```

Onay işleminde önce callback fonksiyonu çalıştırılır, ardından popup kapatılır.

`dismiss()`, gösterilmekte olan PopupWindow'u ekrandan kaldırır.

## 8. Callback Fonksiyonu ile İşlem Yönetimi

Popup'ın onaylandığında ne yapacağı, popup oluşturma fonksiyonunun içinde sabitlenmemiştir. Bunun yerine `onConfirm` adında bir callback parametresi kullanılmıştır.

```kotlin
private fun showCustomPopup(
    title: String,
    message: String,
    onConfirm: () -> Unit
) {
    // Popup oluşturma ve gösterme işlemleri
}
```

`onConfirm: () -> Unit`, parametre almayan ve geriye değer döndürmeyen bir fonksiyon tipidir.

Popup onaylandığında bu fonksiyon çağrılır:

```kotlin
btnPopupConfirm.setOnClickListener {
    onConfirm()
    warningPopup.dismiss()
}
```

Popup'ın gösterilmesi için kullanılan fonksiyon:

```kotlin
private fun showWarningPopup() {
    showCustomPopup(
        title = "İşleme Devam Edilsin mi?",
        message = "Bu işlemi gerçekleştirmek istediğinizden emin misiniz?",
        onConfirm = ::onConfirm
    )
}
```

`::onConfirm`, Kotlin'deki function reference operatörüdür. `onConfirm` fonksiyonunun kendisini parametre olarak iletir.

Onay callback'inin gerçekleştirdiği işlem:

```kotlin
private fun onConfirm() {
    Toast.makeText(
        this,
        "İşlem başarıyla onaylandı.",
        Toast.LENGTH_SHORT
    ).show()
}
```

Bu örnekte onay verildiğinde kullanıcıya Toast mesajı gösterilir.

Bu tasarımın avantajı, popup görünümünün ve genel etkileşim davranışının asıl iş mantığından ayrılmasıdır. `showCustomPopup()` farklı başlık, açıklama ve onay işlemleriyle tekrar kullanılabilir.

## 9. PopupWindow ve Dialog Arasındaki Fark

Android'de kullanıcıya geçici bir arayüz göstermek için birden fazla yaklaşım bulunur.

| Bileşen          | Kullanım amacı                                 |
| ---------------- | ---------------------------------------------- |
| `PopupWindow`    | Özel içerikli, konumlandırılabilir pencere     |
| `AlertDialog`    | Standart uyarı ve onay diyalogları             |
| `Dialog`         | Daha esnek özel diyalog pencereleri            |
| `DialogFragment` | Fragment yaşam döngüsüyle yönetilen diyaloglar |

`PopupWindow`, içerik ve konum üzerinde daha doğrudan kontrol sunar. `AlertDialog` ise standart uyarı ve onay senaryolarında daha az kodla kullanılabilir.

Bu projede özel XML tasarımı ve tam ekran yarı saydam arka plan gerektiği için `PopupWindow` tercih edilmiştir.

## Öğrenilenler

- Android'de PopupWindow oluşturmak ve göstermek
- XML layout'u LayoutInflater ile View nesnesine dönüştürmek
- Popup penceresinin boyut ve konumunu belirlemek
- Popup içerisindeki bileşenlere erişmek
- Popup'ı `dismiss()` ile kapatmak
- Lambda ve callback fonksiyonları kullanmak
- Arayüz ile işlem mantığını birbirinden ayırmak
- PopupWindow, Dialog ve AlertDialog arasındaki temel farkları anlamak

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- AndroidX AppCompat
- AndroidX Core
- Material Components
- ConstraintLayout
- XML Layouts
