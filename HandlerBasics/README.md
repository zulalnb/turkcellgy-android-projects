# Handler Basics

Bu uygulama, Android'de Handler ve Looper yapılarını, mesaj kuyruğunu ve Runnable nesnelerinin belirli bir thread üzerinde çalıştırılmasını öğrenmek amacıyla hazırlanmıştır.

Projede `Handler` kullanılarak iki farklı işlem gerçekleştirilmiştir. İlk işlem hemen çalıştırılırken ikinci işlem belirlenen süre kadar geciktirilerek çalıştırılır.

## Konu: Handler

Android uygulamalarında işlemler thread'ler üzerinde yürütülür. Kullanıcı arayüzü işlemleri ise Main Thread (UI Thread) üzerinde gerçekleştirilir.

Handler, mesajları ve Runnable nesnelerini belirli bir thread'in mesaj kuyruğuna göndermek için kullanılan bir yapıdır.

Özellikle belirli bir thread üzerinde bir işlemi daha sonra çalıştırmak, geciktirmek veya mesaj iletmek gerektiğinde kullanılabilir.

### Handler ve Looper İlişkisi

Handler ve Looper birlikte çalışır.

- **Looper:** İlgili thread'in mesaj kuyruğunu sürekli kontrol eder ve kuyruktaki işlemlerin çalıştırılmasını sağlar.
- **MessageQueue:** Thread üzerinde çalıştırılmayı bekleyen Message ve Runnable nesnelerini tutar.
- **Handler:** Mesajları veya Runnable nesnelerini ilgili MessageQueue içerisine gönderir.

Çalışma yapısı genel olarak şöyledir:

1. Bir thread için Looper oluşturulur ve çalıştırılır.
2. Handler, bu Looper ile ilişkilendirilir.
3. Handler üzerinden mesaj veya Runnable kuyruğa eklenir.
4. Looper, kuyruğa gelen işlemleri sırayla ele alır.
5. İlgili işlemler bağlı oldukları thread üzerinde çalıştırılır.

## Main Thread Üzerinde Handler Kullanımı

Android'de kullanıcı arayüzü işlemlerinin Main Thread üzerinde gerçekleştirilmesi gerekir.

Bu projede Main Thread'e bağlı bir Handler oluşturmak için `Looper.getMainLooper()` kullanılmıştır.

```kotlin
Handler(Looper.getMainLooper()).post {
    Toast.makeText(
        this,
        "Post işlemi çalıştırıldı.",
        Toast.LENGTH_SHORT
    ).show()
}
```

- `Looper.getMainLooper()`: Uygulamanın ana thread'ine ait Looper nesnesini döndürür.
- `Handler()`: Belirtilen Looper ile ilişkili Handler oluşturur.
- `post()`: Runnable nesnesini ilgili thread'in mesaj kuyruğuna ekler.
- `Toast.makeText()`: Kullanıcıya kısa süreli bir mesaj gösterir.

`post()` ile gönderilen Runnable, kuyrukta kendisinden önce bulunan işlemler tamamlandığında çalıştırılır.

Bu örnekte işlem Main Thread üzerinde çalıştırılır.

## post() Metodu

`post()`, Handler aracılığıyla bir Runnable nesnesini ilgili thread'in mesaj kuyruğuna eklemek için kullanılır.

```kotlin
val handler = Handler(Looper.getMainLooper())

handler.post {
    // Main Thread üzerinde çalışacak işlemler
}
```

Runnable, mesaj kuyruğuna eklendikten sonra Looper tarafından işlenir.

`post()` çağrısı işlemin mutlaka çağrıldığı anda çalışacağı anlamına gelmez. Çalışma zamanı, ilgili thread'in mesaj kuyruğundaki diğer işlemlere bağlıdır.

## postDelayed() Metodu

Bazı işlemlerin hemen değil, belirli bir süre geçtikten sonra çalıştırılması gerekebilir.

Handler içerisinde bunun için `postDelayed()` metodu bulunur.

```kotlin
Handler(Looper.getMainLooper()).postDelayed({

    Toast.makeText(
        this,
        "Delayed işlemi çalıştırıldı.",
        Toast.LENGTH_SHORT
    ).show()

}, 6000)
```

- `postDelayed()`: Runnable nesnesini belirtilen süre kadar gecikmeli çalıştırılmak üzere kuyruğa ekler.
- İlk parametre: Çalıştırılacak Runnable nesnesidir.
- İkinci parametre: Gecikme süresidir ve milisaniye cinsinden belirtilir.

Bu örnekte Toast mesajının yaklaşık 6 saniye sonra gösterilmesi planlanmıştır.

Gecikme süresi kesin bir çalışma zamanı garantisi değildir. Runnable, belirtilen süreden sonra thread'in mesaj kuyruğu uygun olduğunda çalıştırılır.

## Handler ile Arka Plan İşlemleri

Handler kullanırken önemli bir ayrım bulunur: Handler, kendi başına yeni bir thread oluşturmaz ve bir işlemi otomatik olarak arka plana taşımaz.

Handler hangi Looper ile ilişkilendirilmişse gönderilen işlemler o Looper'a ait thread üzerinde çalışır.

Örneğin:

```kotlin
val mainHandler = Handler(Looper.getMainLooper())

mainHandler.post {
    // Main Thread üzerinde çalışır.
}
```

Bu örnekte Runnable Main Thread üzerinde çalışır.

Uzun süren işlemler Main Thread üzerinde gerçekleştirilirse kullanıcı arayüzünün yanıt vermemesi veya uygulamanın donması gibi sorunlar ortaya çıkabilir.

Bunun yerine uzun süren işlemler arka plan thread'lerinde yürütülmeli, sonuçların arayüze aktarılması gerektiğinde Main Thread kullanılmalıdır.

## Uygulama Akışı

1. Uygulama `MainActivity` içerisinde başlatılır.
2. `Handler(Looper.getMainLooper())` ile Main Thread'e bağlı bir Handler oluşturulur.
3. `post()` metodu ile ilk Toast mesajı kuyruğa eklenir.
4. Looper, Runnable nesnesini işleyerek ilk Toast mesajını gösterir.
5. `postDelayed()` metodu ile ikinci Toast mesajı 6000 milisaniye gecikmeli olarak kuyruğa eklenir.
6. Gecikme süresi tamamlandıktan ve Main Thread uygun olduğunda ikinci Toast mesajı gösterilir.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- Handler
- Looper
- MessageQueue
- Runnable
- Toast

## Öğrenme Kazanımları

- Android'de Handler kavramını öğrenme.
- Handler ve Looper arasındaki ilişkiyi anlama.
- MessageQueue çalışma mantığını öğrenme.
- Runnable nesnelerini Handler ile çalıştırma.
- `Looper.getMainLooper()` kullanma.
- `post()` metodu ile işlem kuyruğa ekleme.
- `postDelayed()` ile gecikmeli işlem çalıştırma.
- Main Thread üzerinde işlem yürütme mantığını anlama.
- UI Thread ile arka plan işlemleri arasındaki farkı kavrama.