# User Interactions Basics

Bu uygulama, Android'de XML Layout dosyalarında tanımlanan arayüz bileşenlerine Kotlin kodu içerisinden erişmeyi ve bu bileşenlerin özelliklerini programatik olarak değiştirmeyi öğrenmek amacıyla hazırlanmıştır.

Projede XML üzerinde tanımlanan bir Button ve TextView bileşenlerine Kotlin üzerinden erişilmesi, TextView içeriğinin değiştirilmesi ve farklı Activity'lerde farklı layout dosyalarının kullanılması ele alınmıştır.

## Konu: XML Bileşenlerine Kotlin Üzerinden Erişim

Android uygulamalarında kullanıcı arayüzü genellikle XML Layout dosyalarında tanımlanır. Bu dosyalarda oluşturulan View bileşenlerine Kotlin kodu içerisinden erişilerek metin, renk, görünürlük ve benzeri özellikleri çalışma anında değiştirilebilir.

XML ve Kotlin arasındaki bağlantı, `setContentView()` ve `findViewById()` gibi metotlar aracılığıyla kurulur.

### setContentView()

`setContentView()` metodu, bir Activity'nin hangi XML Layout dosyasını kullanacağını belirler.

Örneğin:

```kotlin
setContentView(R.layout.first_screen)
```

Bu kullanımda `first_screen.xml` dosyası Activity'nin arayüzü olarak yüklenir.

`R.layout` üzerinden XML Layout kaynaklarına erişilir. Android tarafından oluşturulan `R` sınıfı, uygulamanın kaynak dosyalarını kod içerisinden referans etmeyi sağlar.

### findViewById()

XML Layout içerisinde tanımlanan bir View bileşenine Kotlin üzerinden erişmek için `findViewById()` metodu kullanılır.

Örneğin, XML içerisinde bir TextView tanımlandığını düşünelim:

**first_screen.xml**

```xml
<TextView
    android:id="@+id/contentTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="TextView" />
```

Burada `android:id` özelliği ile bileşene `contentTextView` kimliği verilmiştir.

Kotlin tarafında bu bileşene aşağıdaki şekilde erişilebilir:

```kotlin
val contentTextView = findViewById<TextView>(R.id.contentTextView)
```

- `findViewById()`: Belirtilen ID'ye sahip View bileşenini bulur.
- `TextView`: Bulunan bileşenin türünü belirtir.
- `R.id.contentTextView`: XML içerisinde tanımlanan View ID'sini referans alır.

Bu yöntemle XML'de oluşturulan bileşenler Kotlin kodu içerisinde kullanılabilir.

### View Properties

Bir View bileşenine eriştikten sonra özellikleri Kotlin üzerinden değiştirilebilir.

Örneğin, TextView içeriğini değiştirmek için `text` özelliği kullanılabilir:

```kotlin
contentTextView.text = "Yeni metin"
```

Bu işlem, XML içerisinde başlangıçta tanımlanan metni çalışma anında değiştirir.

Benzer şekilde View bileşenlerinin farklı özellikleri de kod içerisinden düzenlenebilir.

```kotlin
// Metin değiştirme
contentTextView.text = "Merhaba"

// Görünürlüğü değiştirme
contentTextView.visibility = View.GONE

// Metin rengini değiştirme
contentTextView.setTextColor(Color.RED)
```

### String Resources ve getString()

Android'de metinleri doğrudan Kotlin veya XML dosyalarına yazmak yerine `strings.xml` içerisinde tanımlamak daha düzenli ve sürdürülebilir bir yaklaşımdır.

Örneğin:

**res/values/strings.xml**

```xml
<resources>
    <string name="text">Açıklama metni</string>
</resources>
```

Kotlin içerisinde bu metne `getString()` metodu ile erişilebilir:

```kotlin
contentTextView.text = getString(R.string.text)
```

`R.string.text` ilgili metin kaynağını belirtir. `getString()` ise kaynakta tanımlanan String değerini döndürür.

Bu yaklaşım, metinlerin merkezi olarak yönetilmesini ve çoklu dil desteğinin daha kolay eklenmesini sağlar.

## Projede Kullanımı

Projede `FirstActivity` içerisinde XML'de tanımlanan TextView bileşenine erişilerek metni Kotlin üzerinden değiştirilmiştir.

**FirstActivity.kt**

```kotlin
class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.first_screen)

        val contentTextView = findViewById<TextView>(R.id.contentTextView)

        contentTextView.text = getString(R.string.text)
    }
}
```

Buradaki işlem sırası:

1. `setContentView()` ile `first_screen.xml` yüklenir.
2. `findViewById()` ile `contentTextView` bileşeni bulunur.
3. `getString()` ile `strings.xml` içerisindeki metin alınır.
4. TextView'ın `text` özelliğine yeni değer atanır.

### Farklı Activity'lerde Layout Kullanımı

Android'de her Activity kendi arayüzünü farklı bir XML Layout dosyası üzerinden oluşturabilir.

Bu projede:

- `MainActivity`, `activity_main.xml` dosyasını kullanır.
- `FirstActivity`, `first_screen.xml` dosyasını kullanır.

Her Activity içerisinde ilgili layout `setContentView()` ile yüklenir. Böylece her ekranın kendi View bileşenleri üzerinden işlem yapılabilir.

## Uygulama Akışı

1. XML Layout içerisinde View bileşenleri oluşturulur.
2. Bileşenlere `android:id` aracılığıyla benzersiz ID'ler atanır.
3. Activity içerisinde ilgili XML dosyası `setContentView()` ile yüklenir.
4. `findViewById()` kullanılarak View bileşenlerine erişilir.
5. Kotlin üzerinden bileşenlerin özellikleri değiştirilir.
6. Gerekli metin kaynakları `getString()` ile alınır.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- XML Layout
- ConstraintLayout
- Activity
- TextView
- Button
- findViewById()
- String Resources

## Öğrenme Kazanımları

- XML Layout dosyalarını Activity içerisinde kullanma.
- `setContentView()` metodunun görevini anlama.
- `findViewById()` ile XML bileşenlerine Kotlin üzerinden erişme.
- View ID'lerini `R.id` aracılığıyla kullanma.
- View özelliklerini Kotlin üzerinden değiştirme.
- `strings.xml` kaynaklarına `getString()` ile erişme.
- Farklı Activity'lerde farklı XML Layout dosyaları kullanma.