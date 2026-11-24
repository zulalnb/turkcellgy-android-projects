# Activity Lifecycle

Bu proje, Android Activity yaşam döngüsünün Android Studio Debug araçları kullanılarak incelenmesi amacıyla hazırlanmıştır.

Activity'nin farklı yaşam döngüsü metotları override edilerek uygulama başlatıldığında, ön plana geldiğinde ve arka plana geçtiğinde gerçekleşen callback'ler gözlemlenebilir.

## Ele Alınan Konular

- Activity Lifecycle
- `onCreate()`
- `onStart()`
- `onResume()`
- `onPause()`
- Android Studio Debug ve Breakpoints

## Activity Lifecycle Metotları

Projede `MainActivity` içerisinde aşağıdaki yaşam döngüsü metotları override edilmiştir:

```kotlin
override fun onStart() {
    super.onStart()
}

override fun onResume() {
    super.onResume()
}

override fun onPause() {
    super.onPause()
}
```

`onCreate()` metodu da Activity'nin başlangıç işlemlerini gerçekleştirir. Bu metotlar üzerinde breakpoint tanımlanarak uygulamanın yaşam döngüsü adım adım takip edilebilir.

## Uygulama Akışı

1. Uygulama başlatılır ve `onCreate()` çağrılır.
2. Activity görünür hâle gelirken `onStart()` çalışır.
3. Activity kullanıcı etkileşimine hazır olduğunda `onResume()` çağrılır.
4. Başka bir ekrana veya uygulamaya geçildiğinde `onPause()` çalışır.
5. Debug araçları üzerinden callback metotlarının çağrılma sırası incelenir.

## Kullanılan Teknolojiler

- Kotlin
- Android Studio
- Android SDK
- AndroidX AppCompat
- ConstraintLayout

## Öğrenme Kazanımları

- Activity yaşam döngüsü metotlarının çalışma sırasını gözlemlemek.
- Kullanıcı etkileşimlerinin Activity yaşam döngüsüne etkisini incelemek.
- Breakpoint ve Debug araçlarını kullanarak uygulama akışını takip etmek.

## İlgili Doküman

Activity yaşam döngüsü metotlarının görevleri ve çalışma süreçleri hakkında daha ayrıntılı bilgi için:

[Activity Yapısı ve Yaşam Döngüsü](../.documents/activity-lifeycycle.md)