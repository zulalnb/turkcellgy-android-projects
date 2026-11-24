# Back Press Alert

Bu uygulama, Android'de AlertDialog kullanarak kullanıcıya bilgilendirme ve onay pencereleri göstermeyi öğrenmek amacıyla hazırlanmıştır.

Projede özellikle cihazın geri tuşuna basıldığında uygulamadan çıkmadan önce kullanıcıya onay soran bir AlertDialog oluşturulması ele alınmaktadır.

## Ekran Görüntüsü

<p align="center">
    <img src="./screenshots/home.gif" alt="Back Press Alert uygulama ekranı" width="300" />
</p>

## Konu: AlertDialog

AlertDialog, Android uygulamalarında kullanıcıya bir mesaj göstermek ve belirli bir işlem için onay veya seçim almak amacıyla kullanılan diyalog penceresidir.

Genellikle silme işlemlerini onaylama, uygulamadan çıkma, önemli uyarıları gösterme veya kullanıcıya seçenek sunma gibi durumlarda tercih edilir.

### AlertDialog Oluşturma

Android'de AlertDialog oluşturmak için `AlertDialog.Builder` sınıfı kullanılır.

```kotlin
val alertBuilder = AlertDialog.Builder(this)

alertBuilder.setTitle("Çıkış")
alertBuilder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?")

alertBuilder.setPositiveButton("Evet") { _, _ ->
    finish()
}

alertBuilder.setNegativeButton("Hayır", null)

alertBuilder.create().show()
```

- `setTitle()`: Diyalog penceresinin başlığını belirler.
- `setMessage()`: Kullanıcıya gösterilecek mesajı belirler.
- `setPositiveButton()`: Onay butonunu oluşturur.
- `setNegativeButton()`: İptal butonunu oluşturur.
- `create()`: AlertDialog nesnesini oluşturur.
- `show()`: Diyalog penceresini görüntüler.

### Positive ve Negative Button

**Positive Button**

Kullanıcının onayladığı işlemi gerçekleştirmek için kullanılır.

```kotlin
alertBuilder.setPositiveButton("Evet") { _, _ ->
    finish()
}
```

Burada `finish()` metodu mevcut Activity'yi sonlandırır.

**Negative Button**

Kullanıcının işlemi iptal etmesini sağlar.

```kotlin
alertBuilder.setNegativeButton("Hayır", null)
```

İkinci parametreye `null` verilmesi, özel bir click listener tanımlanmadığını belirtir. Butona basıldığında diyalog varsayılan olarak kapanır.

## Back Press Handling

Android uygulamalarında kullanıcı cihazın geri tuşuna bastığında mevcut ekrandan çıkmak veya önceki ekrana dönmek ister.

Bazı durumlarda doğrudan çıkış yapmak yerine kullanıcıdan onay alınması istenebilir.

AndroidX Activity kütüphanesinde geri tuşu davranışını yönetmek için `OnBackPressedDispatcher` ve `OnBackPressedCallback` yapıları bulunur.

Bu projede `addCallback()` extension fonksiyonu kullanılarak geri tuşu olayı dinlenmiştir.

```kotlin
onBackPressedDispatcher.addCallback(this) {
    showAlert()
}
```

Kullanıcı geri tuşuna bastığında `showAlert()` metodu çağrılır ve çıkış onay penceresi görüntülenir.

### Activity'yi Sonlandırma

Kullanıcı çıkışı onayladığında `finish()` metodu çağrılır.

```kotlin
fun showAlert() {
    val alertBuilder = AlertDialog.Builder(this)

    alertBuilder.setTitle("Çıkış")
    alertBuilder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?")

    alertBuilder.setPositiveButton("Evet") { _, _ ->
        finish()
    }

    alertBuilder.setNegativeButton("Hayır", null)

    alertBuilder.create().show()
}
```

Kullanıcı Hayır seçeneğine bastığında diyalog kapanır ve Activity açık kalır.

## Uygulama Akışı

1. Activity içerisinde `onBackPressedDispatcher.addCallback()` ile geri tuşu olayı dinlenir.
2. Kullanıcı geri tuşuna bastığında `showAlert()` metodu çağrılır.
3. `AlertDialog.Builder` ile çıkış onay penceresi oluşturulur.
4. Kullanıcı Evet seçeneğine bastığında `finish()` ile Activity sonlandırılır.
5. Kullanıcı Hayır seçeneğine bastığında diyalog kapatılır ve uygulamada kalmaya devam eder.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- AndroidX Activity
- AppCompat
- AlertDialog
- OnBackPressedDispatcher

## Öğrenme Kazanımları

- Android'de AlertDialog oluşturma.
- `AlertDialog.Builder` sınıfını kullanma.
- Diyalog penceresine başlık ve mesaj ekleme.
- Positive ve Negative Button tanımlama.
- Butonlara click listener ekleme.
- `finish()` metoduyla Activity'yi sonlandırma.
- `OnBackPressedDispatcher` ile geri tuşu davranışını yönetme.
- Kullanıcıdan uygulamadan çıkış onayı alma.
