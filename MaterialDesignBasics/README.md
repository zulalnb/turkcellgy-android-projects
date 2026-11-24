# Material Design Basics

Bu proje, Android uygulamalarında Material Design bileşenlerinin kullanımını ve XML üzerinden arayüz oluşturmayı öğrenmek amacıyla hazırlanmıştır.

Projede AppBarLayout, Toolbar, CardView, TextInputLayout, FloatingActionButton ve BottomNavigationView gibi arayüz bileşenleri kullanılmıştır. Ayrıca e-posta doğrulama, Toolbar yapılandırması ve kullanıcı etkileşimlerinin yönetimi ele alınmıştır.

Uygulamanın teması `Theme.Material3.DayNight.NoActionBar` olarak yapılandırılmıştır.

## Ekran Görüntüsü

<p align="center">
  <img src="./screenshots/home.png" alt="Material Design Basics uygulama ekranı" width="300" />
</p>

## Ele Alınan Konular

- Material Design ve Material Components
- Material 3 DayNight tema yapılandırması
- `AppBarLayout` ve `Toolbar`
- `CardView`
- `TextInputLayout` ve `TextInputEditText`
- `Patterns.EMAIL_ADDRESS` ile e-posta doğrulama
- `FloatingActionButton`
- `BottomNavigationView`
- `ContextCompat.getColor()`
- Edge-to-edge ekran düzeni
- `WindowInsetsCompat` ile sistem çubuklarının yönetimi

## 1. Material Design Nedir?

Material Design, Google tarafından geliştirilen bir tasarım sistemidir. Android uygulamalarında tutarlı ve kullanıcı dostu arayüzler oluşturmak için bileşenler, renkler, tipografi ve etkileşim kuralları sunar.

Android projelerinde Material Design bileşenlerini kullanabilmek için Material Components kütüphanesinden yararlanılabilir.

### Material 3 DayNight Teması

Projede kullanılan tema:

```xml
<style name="Base.Theme.MaterialDesignBasics"
    parent="Theme.Material3.DayNight.NoActionBar">
    ...
</style>
```

`Theme.Material3.DayNight.NoActionBar`, Material 3 tasarım sistemini temel alan ve açık/koyu tema desteği sunan bir temadır.

- `Material3`: Material Design 3 tema özelliklerini kullanır.
- `DayNight`: Sistem temasına göre açık veya koyu görünümün kullanılmasını sağlar.
- `NoActionBar`: Varsayılan ActionBar'ı devre dışı bırakır. Böylece uygulama kendi Toolbar bileşenini kullanabilir.

## 2. AppBarLayout ve Toolbar

### AppBarLayout

`AppBarLayout`, Material Components kütüphanesinde bulunan ve uygulamanın üst bölümündeki AppBar alanını oluşturmak için kullanılan bir bileşendir.

Genellikle Toolbar gibi üst çubuk bileşenlerini kapsar. `CoordinatorLayout` ile birlikte kullanıldığında kaydırılabilir içerikle koordineli davranışlar da sağlayabilir.

Bu projede AppBarLayout, Toolbar bileşenini kapsayan bir container olarak kullanılmıştır.

```xml
<com.google.android.material.appbar.AppBarLayout
    android:id="@+id/appBarLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:text="Yeni Android"
            android:textColor="@color/white" />

    </androidx.appcompat.widget.Toolbar>

</com.google.android.material.appbar.AppBarLayout>
```

### Toolbar

`Toolbar`, uygulamanın üst kısmında başlık ve işlem alanı oluşturmak için kullanılan bir arayüz bileşenidir.

Projede Toolbar, Activity'nin ActionBar bileşeni olarak atanmıştır:

```kotlin
val toolbar = findViewById<Toolbar>(R.id.toolbar)

setSupportActionBar(toolbar)
setTitle("Android")
```

- `findViewById()`: XML içerisinde tanımlanan Toolbar bileşenine erişir.
- `setSupportActionBar()`: Toolbar'ı Activity'nin desteklenen ActionBar yapısına entegre eder.
- `setTitle()`: Activity başlığını belirler.

Toolbar başlığının rengini değiştirmek için `setTitleTextColor()` metodu kullanılmıştır:

```kotlin
toolbar.setTitleTextColor(
    ContextCompat.getColor(this, R.color.blue)
)
```

`ContextCompat.getColor()`, AndroidX Core kütüphanesinin sağladığı uyumluluk metodudur. Belirtilen renk kaynağının renk değerini Context üzerinden elde etmeyi sağlar.

Gerekli import:

```kotlin
import androidx.core.content.ContextCompat
```

## 3. CardView

`CardView`, içerikleri kart görünümünde sunmak için kullanılan AndroidX bileşenidir.

Kart görünümüne yuvarlatılmış köşeler ve elevation gibi görsel özellikler kazandırılabilir.

Projede CardView, `LinearLayout` içerisinde konumlandırılarak içerik alanı oluşturmak için kullanılmıştır.

```xml
<androidx.cardview.widget.CardView
    android:id="@+id/cardView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="10dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView"
        android:padding="20dp" />

</androidx.cardview.widget.CardView>
```

`app:cardCornerRadius` özelliği kartın köşe yuvarlaklığını belirler.

**Not:** CardView, Material Components kütüphanesinin değil, AndroidX CardView kütüphanesinin bir bileşenidir.

## 4. TextInputLayout ve TextInputEditText

`TextInputLayout`, metin giriş alanlarına Material Design görünümü ve ek etkileşim özellikleri kazandıran bir bileşendir.

`TextInputEditText` ise kullanıcının metin girişi yaptığı alandır.

Projede e-posta adresi almak için bu iki bileşen birlikte kullanılmıştır:

```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/tilUser"
    android:layout_width="0dp"
    android:layout_height="wrap_content">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="E-posta" />

</com.google.android.material.textfield.TextInputLayout>
```

`TextInputLayout`, giriş alanının etiketini ve hata mesajlarını yönetmeye yardımcı olur.

Giriş alanında hata göstermek için `error` özelliği kullanılabilir:

```kotlin
tilUser.error = "Geçerli bir e-posta adresi giriniz"
tilUser.isErrorEnabled = true
```

Hata durumunu kaldırmak için:

```kotlin
tilUser.isErrorEnabled = false
```

## 5. E-posta Doğrulama

Projede e-posta adresinin biçimini kontrol etmek için Android'in `Patterns.EMAIL_ADDRESS` yapısı kullanılmıştır.

Gönder butonuna tıklandığında `validateForm()` metodu çağrılarak giriş kontrol edilir.

```kotlin
fun validateForm() {
    val tilUser = findViewById<TextInputLayout>(R.id.tilUser)
    val etEmail = findViewById<TextInputEditText>(R.id.etEmail)

    val email = etEmail.text.toString().trim()

    if (email.isEmpty()) {
        tilUser.error = "Email adresi boş olamaz"
        tilUser.isErrorEnabled = true
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        tilUser.error = "Geçerli bir email adresi giriniz"
        tilUser.isErrorEnabled = true
    } else {
        tilUser.isErrorEnabled = false
    }
}
```

Burada kullanılan metot ve özellikler:

- `text.toString()`: EditText içerisindeki metni String türüne dönüştürür.
- `trim()`: Metnin başındaki ve sonundaki boşluk karakterlerini temizler.
- `isEmpty()`: Giriş alanının boş olup olmadığını kontrol eder.
- `Patterns.EMAIL_ADDRESS`: E-posta adresi biçimi için Android tarafından sağlanan hazır desendir.
- `matcher(email).matches()`: Girilen metnin ilgili desenle eşleşip eşleşmediğini kontrol eder.
- `isErrorEnabled`: TextInputLayout üzerindeki hata durumunu etkinleştirir veya devre dışı bırakır.

Bu kontrol yalnızca e-posta adresinin biçimini doğrular. Adresin gerçekten var olduğunu veya kullanıcıya ait olduğunu doğrulamaz.

## 6. FloatingActionButton

`FloatingActionButton` (FAB), ekranda öne çıkarılmak istenen temel bir işlemi temsil etmek için kullanılan Material Design bileşenidir.

Genellikle ekranın sağ alt bölümünde konumlandırılır ve kullanıcıya önemli bir işleve hızlı erişim sağlar.

Projede FAB tıklama olayı `setOnClickListener()` ile yönetilmiştir:

```kotlin
val fab = findViewById<FloatingActionButton>(
    R.id.floatingActionButton
)

fab.setOnClickListener {
    Toast.makeText(
        this,
        "fab",
        Toast.LENGTH_SHORT
    ).show()
}
```

Bu örnekte butona tıklandığında kısa süreli bir Toast mesajı gösterilir.

## 7. BottomNavigationView

`BottomNavigationView`, uygulamanın alt bölümünde ana ekranlar veya içerik bölümleri arasında geçiş yapılmasını sağlayan Material Components bileşenidir.

Projede menü öğeleri `bottom_navigation_menu.xml` dosyasında tanımlanmış ve BottomNavigationView içerisine atanmıştır.

```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
    android:id="@+id/bottomNav"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:menu="@menu/bottom_navigation_menu" />
```

Menü öğelerinin seçilme olayları `setOnItemSelectedListener()` metodu ile dinlenir:

```kotlin
val bottomNavigation: BottomNavigationView =
    findViewById(R.id.bottomNav)

bottomNavigation.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.signIn -> {
            Toast.makeText(
                this,
                "Giriş ekranı",
                Toast.LENGTH_SHORT
            ).show()
            true
        }

        R.id.detail -> {
            Toast.makeText(
                this,
                "Detay ekranı",
                Toast.LENGTH_SHORT
            ).show()
            true
        }

        else -> false
    }
}
```

`item.itemId` üzerinden seçilen menü öğesinin ID değeri kontrol edilir.

- `true`: Seçilen menü öğesinin işlendiğini belirtir.
- `false`: Seçimin işlenmediğini belirtir.

Bu projede menü öğelerine tıklandığında ilgili Toast mesajı gösterilir. Gerçek Activity veya Fragment navigasyonu uygulanmamıştır.

## 8. Edge-to-Edge ve Window Insets

Edge-to-edge yaklaşımı, uygulamanın içerik alanının sistem çubuklarının arkasına kadar uzanmasını sağlar.

Projede edge-to-edge ekran düzenini etkinleştirmek için `enableEdgeToEdge()` metodu kullanılmıştır.

Sistem çubuklarının içerikle çakışmasını önlemek için `WindowInsetsCompat` üzerinden alınan boşluk değerleri kök görünüme uygulanmıştır.

```kotlin
enableEdgeToEdge()

ViewCompat.setOnApplyWindowInsetsListener(
    findViewById(R.id.main)
) { view, insets ->

    val systemBars = insets.getInsets(
        WindowInsetsCompat.Type.systemBars()
    )

    view.setPadding(
        systemBars.left,
        systemBars.top,
        systemBars.right,
        0
    )

    insets
}
```

- `enableEdgeToEdge()`: Uygulamanın edge-to-edge ekran düzenini kullanmasını sağlar.
- `ViewCompat.setOnApplyWindowInsetsListener()`: Kök View üzerine sistem boşlukları uygulandığında çalışacak listener tanımlar.
- `WindowInsetsCompat.Type.systemBars()`: Durum çubuğu ve gezinme çubuğu gibi sistem çubuklarının kapladığı alanları belirtir.
- `getInsets()`: İlgili kenarlara uygulanması gereken boşluk değerlerini döndürür.
- `setPadding()`: Alınan değerleri kök görünüme uygular.

Bu projede sol, üst ve sağ inset değerleri padding olarak uygulanırken alt padding `0` olarak belirlenmiştir.

## Uygulama Akışı

1. Uygulama başlatılır ve `MainActivity` açılır.
2. `enableEdgeToEdge()` ile edge-to-edge ekran düzeni etkinleştirilir.
3. `activity_main.xml` dosyası `setContentView()` ile yüklenir.
4. `WindowInsetsCompat` kullanılarak sistem çubuklarının boşlukları kök görünüme uygulanır.
5. Toolbar, `setSupportActionBar()` ile Activity'nin ActionBar yapısına entegre edilir.
6. FloatingActionButton için tıklama olayı tanımlanır.
7. Gönder butonuna tıklandığında `validateForm()` metodu çağrılır ve e-posta alanı doğrulanır.
8. BottomNavigationView için menü öğesi seçme listener'ı tanımlanır.
9. Seçilen menü öğesine göre kullanıcıya Toast mesajı gösterilir.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- Material Components for Android
- Material 3 DayNight Theme
- AndroidX AppCompat
- AndroidX Core KTX
- AndroidX CardView
- ConstraintLayout
- LinearLayout
- XML Layout
- Toolbar
- AppBarLayout
- TextInputLayout
- FloatingActionButton
- BottomNavigationView
- WindowInsetsCompat

## Öğrenme Kazanımları

- Material Design ve Material 3 tema yapılandırmasını öğrenme.
- Material Components bileşenlerini XML içerisinde kullanma.
- AppBarLayout ve Toolbar yapılandırmasını anlama.
- Toolbar'ı Activity'nin ActionBar yapısına entegre etme.
- CardView ile kart görünümü oluşturma.
- TextInputLayout ile form alanlarını düzenleme ve hata mesajı gösterme.
- `Patterns.EMAIL_ADDRESS` ile e-posta biçimini kontrol etme.
- FloatingActionButton tıklama olaylarını yönetme.
- BottomNavigationView menü öğelerinin seçim olaylarını dinleme.
- `ContextCompat.getColor()` ile renk kaynaklarına erişme.
- Edge-to-edge ekran düzenini ve WindowInsets yönetimini uygulama.