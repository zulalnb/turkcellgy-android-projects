# Splash Basics

Bu uygulama, Android'de Splash Screen (Açılış Ekranı) oluşturmanın eski (Legacy) ve modern (Modern) yöntemlerini öğrenmek amacıyla hazırlanmıştır.

Projede iki farklı yaklaşım uygulanmıştır:

- **Legacy Splash Screen:** XML layout kullanılarak manuel olarak oluşturulan açılış ekranı.
- **Modern Splash Screen:** AndroidX SplashScreen API kullanılarak Android'in sistem açılış ekranından yararlanılması.

Uygulamada `SplashMode` enum değeri değiştirilerek iki yöntem arasında geçiş yapılabilir. Varsayılan olarak modern yöntem seçilmiştir.

## Ekran Görüntüsü

<p align="center">
  <img src="./screenshots/splash.gif" alt="Splash Basics uygulama açılış ekranları" width="300" />
</p>

## Konu: Splash Screen

Splash Screen, bir uygulama başlatılırken kullanıcıya gösterilen başlangıç ekranıdır.

Genellikle uygulamanın logosunu, marka kimliğini veya uygulama simgesini içerir.

Açılış sırasında uygulamanın gerekli hazırlıkları tamamlaması için kullanıcıya görsel bir geçiş sunar.

Android'de Splash Screen oluşturmak için iki farklı yaklaşım incelenebilir:

- Legacy yöntem: Arayüzün XML içerisinde manuel oluşturulması.
- Modern yöntem: AndroidX SplashScreen API kullanılması.

---

## 1. Legacy Splash Screen

Legacy yöntemde Splash Screen, uygulamanın kendi XML layout'u içerisinde bir View olarak oluşturulur.

Bu yaklaşımda açılış ekranı ayrı bir arayüz bileşeni gibi tasarlanır ve ana ekranın üzerine yerleştirilir.

Projede `activity_main_legacy.xml` dosyası bu amaçla kullanılmıştır.

### XML ile Splash Screen Oluşturma

`activity_main_legacy.xml` içerisinde ana ekranın üzerine yerleştirilmiş bir ConstraintLayout bulunmaktadır.

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/splashScreen"
    android:layout_width="0dp"
    android:layout_height="0dp"
    android:background="#FF9800"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:contentDescription="Uygulama logosu"
        android:scaleType="fitCenter"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@mipmap/ic_launcher" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

Bu layout içerisinde:

- Turuncu arka planlı bir ConstraintLayout oluşturulmuştur.
- Uygulama logosu ImageView ile gösterilmiştir.
- Splash ekranı tüm ekranı kaplayacak şekilde konumlandırılmıştır.

### XML Splash Ekranını Yönetme

Legacy yöntemde Splash Screen görünürlüğü Kotlin tarafından yönetilir.

Öncelikle XML içerisindeki View bulunur:

```kotlin
private var legacySplashView: ConstraintLayout? = null

// onCreate() içerisinde
setContentView(R.layout.activity_main_legacy)

legacySplashView = findViewById(R.id.splashScreen)
```

Splash ekranının kapatılması için `View.GONE` kullanılır:

```kotlin
legacySplashView?.visibility = View.GONE
```

- `View.VISIBLE`: View görünür durumdadır.
- `View.INVISIBLE`: View görünmez ancak layout içerisindeki alanını korur.
- `View.GONE`: View görünmez olur ve layout içerisindeki alanı kaldırılır.

Bu projede ana ekranın üzerine yerleştirilen Splash Screen, gerekli koşullar sağlandığında `GONE` durumuna getirilir.

### Legacy Yöntemin Çalışma Mantığı

Legacy yaklaşımında açılış ekranının oluşturulması, konumlandırılması ve kaldırılması tamamen geliştiricinin kontrolündedir.

Bu yöntem görsel tasarım açısından esneklik sağlar. Ancak sistemin kendi başlangıç ekranıyla bütünleşik çalışmaz ve açılışın ilk anlarında görülebilen sistem splash ekranının yerini doğrudan alamaz.

Bu nedenle modern Android uygulamalarında tercih edilmesi önerilen yaklaşım değildir.

---

## 2. Modern Splash Screen (AndroidX SplashScreen API)

AndroidX SplashScreen API, Android uygulamalarında sistemin Splash Screen mekanizmasını kullanmayı sağlayan modern yaklaşımdır.

Android 12 (API 31) ve üzeri sürümlerde sistem Splash Screen desteği bulunur.

AndroidX SplashScreen kütüphanesi ise bu deneyimi daha eski Android sürümlerinde de uyumlu şekilde kullanabilmeyi sağlar.

Bu yöntemde ayrıca bir Splash Activity oluşturulmasına veya ana ekranın üzerine manuel bir Splash View yerleştirilmesine gerek yoktur.

### AndroidX SplashScreen Bağımlılığı

Öncelikle projeye AndroidX SplashScreen kütüphanesi eklenmelidir.

`app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("androidx.core:core-splashscreen:1.2.0")
}
```

Bu bağımlılık, AndroidX SplashScreen API'nin kullanılmasını sağlar.

### Splash Screen Temasını Tanımlama

Modern yöntemde Splash Screen görünümü tema üzerinden yapılandırılır.

Projede `themes.xml` içerisinde `Theme.App.Starting` adında bir tema oluşturulmuştur.

```xml
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenBackground">#FF9800</item>
    <item name="windowSplashScreenAnimatedIcon">@mipmap/ic_launcher</item>
    <item name="postSplashScreenTheme">@style/Theme.SplashBasics</item>
</style>
```

Bu tema içerisindeki önemli özellikler şunlardır:

**`Theme.SplashScreen`**

AndroidX tarafından sağlanan başlangıç ekranı temasının temelidir.

**`windowSplashScreenBackground`**

Splash Screen'in arka plan rengini belirler.

**`windowSplashScreenAnimatedIcon`**

Splash Screen üzerinde gösterilecek uygulama simgesini belirler. Uygun bir animated vector drawable kullanıldığında animasyon da gösterilebilir.

**`postSplashScreenTheme`**

Splash Screen kapandıktan sonra Activity'nin kullanacağı normal uygulama temasını belirler.

Bu projede normal tema olarak `Theme.SplashBasics` kullanılmıştır.

### AndroidManifest.xml Ayarı

Modern Splash Screen temasının uygulamanın başlangıç Activity'sine uygulanması gerekir.

Bu projede uygulama teması `AndroidManifest.xml` içerisindeki application etiketi üzerinden tanımlanmıştır:

```xml
<application
    android:theme="@style/Theme.App.Starting">

    <activity
        android:name=".MainActivity"
        android:exported="true">

        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>

    </activity>
</application>
```

Uygulama başlatıldığında sistem bu başlangıç temasını kullanarak Splash Screen'i gösterir.

### installSplashScreen()

Modern yöntemin temel adımı `installSplashScreen()` metodudur.

```kotlin
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
```

Bu metot, sistem Splash Screen mekanizmasını Activity'ye bağlar.

Projede `onCreate()` metodundan önce, `super.onCreate()` çağrısından önce kullanılmıştır:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {

    val splashScreen = installSplashScreen()

    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
}
```

`installSplashScreen()` çağrısı, Activity'nin başlangıç ekranını AndroidX SplashScreen API üzerinden yönetebilmesini sağlar.

Modern yöntemde ayrıca `activity_main.xml` içerisinde bir Splash View bulunmaz. Bu layout doğrudan ana ekran içeriğini tanımlar.

### setKeepOnScreenCondition()

Bazı uygulamalarda açılış sırasında gerekli işlemlerin tamamlanması beklenmek istenebilir.

Örneğin:

- Kullanıcı oturumunun kontrol edilmesi.
- Başlangıç verilerinin yüklenmesi.
- Uygulama ayarlarının hazırlanması.

Splash Screen'in bu işlemler tamamlanana kadar görünür kalmasını sağlamak için `setKeepOnScreenCondition()` kullanılır.

```kotlin
splashScreen.setKeepOnScreenCondition {
    !isActionCompleted || !isSplashDelayCompleted
}
```

Bu metot bir Boolean değer döndüren koşul alır.

- `true`: Splash Screen gösterilmeye devam eder.
- `false`: Splash Screen kapatılabilir.

Projede iki koşul birlikte kontrol edilir:

- `isActionCompleted`: Gerekli işlemin tamamlanıp tamamlanmadığını belirtir.
- `isSplashDelayCompleted`: Minimum gösterim süresinin tamamlanıp tamamlanmadığını belirtir.

Her iki koşul da tamamlanmadan Splash Screen kapanmaz.

Burada `||` (OR) operatörü kullanıldığı için iki değişkenden herhangi biri henüz `false` ise Splash Screen ekranda kalır.

### Handler ile Açılış İşlemlerini Simüle Etme

Projede açılış işlemini ve Splash Screen'in minimum gösterim süresini simüle etmek için Handler kullanılmıştır.

Öncelikle gerekli değişkenler tanımlanır:

```kotlin
var isActionCompleted = false
var isSplashDelayCompleted = false

private val handler = Handler(Looper.getMainLooper())
```

**Gerekli işlemin tamamlanması:**

```kotlin
private fun startLongRunningTask() {
    handler.postDelayed({

        isActionCompleted = true

    }, 1000)
}
```

Burada gerekli işlem 1000 milisaniye, yani 1 saniye sonra tamamlanmış kabul edilir.

**Minimum Splash süresinin tamamlanması:**

```kotlin
private fun startSplashDelay() {
    handler.postDelayed({

        isSplashDelayCompleted = true

    }, 2500)
}
```

Bu işlem ise 2500 milisaniye, yani 2,5 saniye sonra tamamlanır.

İki koşul da `true` olduğunda `setKeepOnScreenCondition()` artık `false` döndürür ve sistem Splash Screen'i kaldırır.

> Bu projedeki Handler işlemleri gerçek bir veri yükleme veya ağ isteği gerçekleştirmez. Yalnızca işlemin tamamlanmasını ve minimum gösterim süresini simüle eder. Gerçek uzun süren işlemler Main Thread'i bloklamadan arka planda yürütülmelidir.

---

## Legacy ve Modern Splash Screen Karşılaştırması

| Özellik | Legacy | Modern |
|---|---|---|
| Oluşturma yöntemi | XML layout | AndroidX SplashScreen API |
| Görünüm yönetimi | View üzerinden | Tema ve sistem API'si |
| Sistemle entegrasyon | Sınırlı | Yerleşik sistem deneyimi |
| Ayrı Splash Activity | Gerekmez | Gerekmez |
| Kapatma yöntemi | `View.GONE` | `setKeepOnScreenCondition()` |
| Tema yapılandırması | Normal Activity teması | `Theme.SplashScreen` |
| Eski Android sürümleri | Manuel uygulanır | AndroidX uyumluluğu sağlar |
| Önerilen yaklaşım | Eski projelerde karşılaşılabilir | Yeni projeler için önerilir |

### Neden Modern Yöntem Tercih Edilmeli?

Modern SplashScreen API, Android'in sistem açılış deneyimiyle uyumlu çalışır.

Legacy yöntemde geliştirici kendi Splash View'ını oluşturup yönetirken modern yöntemde Android'in sağladığı mekanizmadan yararlanılır.

Bunun avantajları:

- Sistem açılış deneyimiyle tutarlı görünüm.
- Android sürümleri arasında daha uyumlu davranış.
- Ayrı bir Splash Activity veya manuel overlay gerektirmemesi.
- Tema üzerinden kolay yapılandırma.
- Açılış işlemlerini bekletmek için `setKeepOnScreenCondition()` desteği.

Bu nedenle yeni Android uygulamalarında AndroidX SplashScreen API tercih edilmelidir.

## Uygulama Akışı

1. Uygulama başlatılır ve `AndroidManifest.xml` içerisindeki başlangıç teması uygulanır.
2. `SplashMode` değişkenine göre kullanılacak yöntem belirlenir.
3. Modern yöntemde `installSplashScreen()` ile sistem Splash Screen etkinleştirilir.
4. Legacy yöntemde normal tema ayarlanır ve `activity_main_legacy.xml` yüklenir.
5. Modern yöntemde ana ekran doğrudan `activity_main.xml` üzerinden yüklenir.
6. Handler ile gerekli işlemin tamamlanması simüle edilir.
7. Handler ile minimum Splash gösterim süresi takip edilir.
8. Her iki koşul tamamlandığında modern Splash Screen kapanır veya Legacy Splash View `GONE` durumuna getirilir.
9. Ana ekran kullanıcıya gösterilir.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- AndroidX Core SplashScreen
- `installSplashScreen()`
- `setKeepOnScreenCondition()`
- Handler
- Looper
- ConstraintLayout
- XML Layout
- Android Themes
- AndroidManifest.xml

## Öğrenme Kazanımları

- Splash Screen kavramını öğrenme.
- Android'de Legacy ve Modern Splash Screen yaklaşımlarını karşılaştırma.
- XML ile manuel Splash Screen oluşturma.
- AndroidX SplashScreen API kullanma.
- `Theme.SplashScreen` temasını yapılandırma.
- `windowSplashScreenBackground` ve `windowSplashScreenAnimatedIcon` özelliklerini kullanma.
- `postSplashScreenTheme` ile açılış sonrası temayı belirleme.
- `installSplashScreen()` metodunu kullanma.
- `setKeepOnScreenCondition()` ile Splash Screen'in görünürlük sürecini yönetme.
- Handler ve Looper kullanarak gecikmeli işlem gerçekleştirme.
- Uygulama açılışında gerekli işlemlerin tamamlanmasını bekleme.