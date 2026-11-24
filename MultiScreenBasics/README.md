# Multi Screen Basics

Bu uygulama, Android'de birden fazla ekranla çalışmayı, Activity'ler arasında geçiş yapmayı ve ekranlar arasında çift yönlü veri aktarımını öğrenmek amacıyla hazırlanmıştır.

Projede `MainActivity` ve `SecondActivity` olmak üzere iki farklı ekran bulunmaktadır. İlk ekrandan ikinci ekrana Intent aracılığıyla veri gönderilir. İkinci ekrandan geri dönülürken de sonuç verisi ilk ekrana iletilir.

## Ekran Görüntüsü

<p align="center">
    <img src="./screenshots/app.gif" alt="Multi Screen Basics uygulama akışı" width="300" />
</p>

## Konu: Çok Ekran ile Çalışmak

Android uygulamalarında farklı işlevleri birbirinden ayırmak ve kullanıcıya farklı içerikler sunmak için birden fazla Activity kullanılabilir.

Her Activity kendi yaşam döngüsüne, kullanıcı arayüzüne ve görevlerine sahiptir.

Bu projede iki Activity kullanılmıştır:

- **MainActivity:** Uygulamanın başlangıç ekranıdır. İkinci ekrana geçişi başlatır ve geri dönen veriyi karşılar.
- **SecondActivity:** İlk ekrandan gelen veriyi görüntüler ve geri dönüş sırasında ilk ekrana veri gönderir.

Her Activity için ayrı bir XML layout dosyası kullanılmıştır:

- `activity_main.xml`
- `activity_second.xml`

Yeni bir Activity'nin uygulama içerisinde kullanılabilmesi için `AndroidManifest.xml` dosyasında tanımlanması gerekir.

## Intent Kavramı ve Çalışma Yapısı

Intent, Android bileşenleri arasında iletişim kurmak ve bir işlem başlatmak için kullanılan mesaj nesnesidir.

Activity'ler arasında ekran geçişi yapmak ve veri taşımak Intent'in yaygın kullanım alanlarındandır.

### Explicit Intent

Explicit Intent, hedef bileşenin doğrudan belirtildiği Intent türüdür.

Bu projede `MainActivity` üzerinden `SecondActivity` ekranını açmak için kullanılmıştır.

```kotlin
val intent = Intent(this, SecondActivity::class.java)
secondActivityLauncher.launch(intent)
```

- `this`: Mevcut Activity context'ini belirtir.
- `SecondActivity::class.java`: Hedef Activity sınıfını belirtir.
- `launch(intent)`: Intent'i kullanarak hedef Activity'yi başlatır.

## Ekranlar Arası Veri Taşımak

Intent kullanılarak bir Activity'den başka bir Activity'ye veri gönderilebilir.

Veri eklemek için `putExtra()`, gönderilen veriyi okumak için ise uygun `get...Extra()` metotları kullanılır.

### putExtra()

`putExtra()` metodu, Intent içerisine anahtar-değer (key-value) yapısıyla veri eklemeyi sağlar.

```kotlin
val intent = Intent(this, SecondActivity::class.java)

intent.putExtra("intentData", "Aktarılan Veri")

secondActivityLauncher.launch(intent)
```

Burada `intentData` anahtarına karşılık gelen metin, `SecondActivity` ekranına gönderilir.

### getStringExtra()

Hedef Activity içerisinde gönderilen veriyi okumak için `getStringExtra()` kullanılır.

```kotlin
val intentData = intent.getStringExtra("intentData")

val tvIntentData = findViewById<TextView>(R.id.tvIntentData)
tvIntentData.text = intentData
```

`getStringExtra()` metodu, belirtilen anahtara karşılık gelen String verisini döndürür. Anahtar bulunamazsa veya değer uygun türde değilse `null` dönebilir.

Intent ile String dışında Integer, Boolean ve Parcelable gibi farklı veri türleri de taşınabilir. Ancak Intent, büyük miktarda veri taşımak için uygun değildir.

## Geri Yönlü Veri Taşımak

Bazı senaryolarda yalnızca bir ekrana veri göndermek yeterli olmayabilir. Açılan ekrandan önceki ekrana da sonuç döndürmek gerekebilir.

Bu projede ikinci ekrandan ilk ekrana veri göndermek için `setResult()` ve `finish()` metotları kullanılmıştır.

### setResult()

`setResult()`, tamamlanan Activity'nin sonucunu ve döndürülecek Intent verisini belirler.

```kotlin
fun navigateBack() {
    val intent = Intent()
    intent.putExtra("returnedData", "İkinci ekrandan dönen veri")

    setResult(RESULT_OK, intent)
    finish()
}
```

- `Intent()`: Geri döndürülecek verileri taşıyan Intent nesnesini oluşturur. Bu kullanımda hedef Activity belirtilmez.
- `putExtra()`: Geri gönderilecek veriyi Intent'e ekler.
- `setResult(RESULT_OK, intent)`: Activity'nin başarılı sonuçla tamamlandığını ve gönderilecek veriyi belirtir.
- `finish()`: Mevcut Activity'yi sonlandırarak önceki ekrana dönülmesini sağlar.

`RESULT_OK`, işlemin başarılı bir sonuçla tamamlandığını belirtir. Kullanıcı herhangi bir sonuç döndürmeden geri tuşuyla ekrandan ayrılırsa varsayılan sonuç `RESULT_CANCELED` olur.

## Activity Result API

Android'de başka bir Activity'yi sonuç almak amacıyla başlatmak için modern yaklaşım **Activity Result API** kullanmaktır.

Eski `startActivityForResult()` ve `onActivityResult()` yöntemleri artık deprecated durumundadır. Bu projede onların yerine `registerForActivityResult()` ve `ActivityResultContracts.StartActivityForResult()` kullanılmıştır.

### registerForActivityResult()

İlk Activity içerisinde sonuç almak için bir launcher tanımlanır:

```kotlin
private val secondActivityLauncher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == RESULT_OK) {
            val returnedMessage = result.data?.getStringExtra("returnedData")

            Toast.makeText(
                this,
                "Alınan veri: $returnedMessage",
                Toast.LENGTH_SHORT
            ).show()

        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(
                this,
                "Geri tuşuyla önceki ekrana dönüldü",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
```

- `registerForActivityResult()`: Başlatılan işlemin sonucunu karşılayacak callback'i kaydeder.
- `StartActivityForResult()`: Başka bir Activity'yi başlatıp sonucunu almayı sağlayan sözleşmedir.
- `result.resultCode`: Dönen işlemin sonuç kodunu belirtir.
- `result.data`: Sonuçla birlikte döndürülen Intent nesnesidir.
- `getStringExtra()`: Döndürülen Intent içerisindeki String verisini okur.

### Activity'yi Başlatma

Launcher tanımlandıktan sonra `launch()` metodu ile ikinci Activity açılır:

```kotlin
secondActivityLauncher.launch(intent)
```

İkinci Activity `setResult()` ve `finish()` ile tamamlandığında sonuç callback içerisinde karşılanır.

Bu yaklaşım, eski `onActivityResult()` yöntemine göre daha düzenli ve yaşam döngüsüyle uyumlu bir sonuç yönetimi sunar.

## Uygulama Akışı

1. Uygulama `MainActivity` ekranında açılır.
2. Kullanıcı **İkinci Ekrana Git** butonuna basar.
3. `Intent` oluşturulur ve `putExtra()` ile ikinci ekrana veri eklenir.
4. `ActivityResultLauncher.launch()` aracılığıyla `SecondActivity` başlatılır.
5. İkinci ekran, `getStringExtra()` ile gelen veriyi okur ve TextView üzerinde gösterir.
6. Kullanıcı **Geri Dön** butonuna bastığında yeni bir Intent oluşturulur.
7. `putExtra()` ile geri gönderilecek veri eklenir.
8. `setResult(RESULT_OK, intent)` ile sonuç belirlenir ve `finish()` ile ikinci ekran kapatılır.
9. İlk Activity içerisindeki callback çalışır ve dönen veri `result.data` üzerinden okunur.
10. Alınan veri Toast mesajıyla kullanıcıya gösterilir.

Kullanıcı ikinci ekrandan geri tuşuyla ayrılırsa sonuç `RESULT_CANCELED` olarak karşılanır.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- AndroidX Activity
- AppCompat
- Intent
- Activity Result API
- ActivityResultContracts
- ConstraintLayout
- Toast

## Öğrenme Kazanımları

- Android'de birden fazla Activity ile çalışma.
- Activity oluşturma ve AndroidManifest.xml içerisinde tanımlama.
- Explicit Intent kullanarak ekranlar arasında geçiş yapma.
- `putExtra()` ile Intent'e veri ekleme.
- `getStringExtra()` ile Intent üzerinden veri okuma.
- Activity'ler arasında ileri yönlü veri aktarımı.
- `setResult()` ile Activity sonucunu belirleme.
- `finish()` ile mevcut Activity'yi sonlandırma.
- Activity Result API kullanarak sonuç alma.
- `registerForActivityResult()` ve `ActivityResultContracts.StartActivityForResult()` yapılarını kullanma.
- `RESULT_OK` ve `RESULT_CANCELED` sonuç kodlarını yönetme.
