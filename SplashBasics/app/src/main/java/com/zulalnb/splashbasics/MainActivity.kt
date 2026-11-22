
package com.zulalnb.splashbasics

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Splash ekranı, uygulama açılırken kullanıcıya gösterilen başlangıç ekranıdır.
// Genellikle uygulamanın logosunu veya markasını içerir.
//
// Uygulama açılışında gerekli hazırlıkların yapılması ve verilerin yüklenmesi
// sırasında kullanıcıya bir bekleme ekranı sunmak amacıyla kullanılabilir.

class MainActivity : AppCompatActivity() {

    // Kullanılacak splash ekranı yöntemini belirler.
    // LEGACY: XML ile oluşturulan özel splash ekranı.
    // MODERN: AndroidX SplashScreen API ile oluşturulan sistem splash ekranı.
    private enum class SplashMode {
        LEGACY,
        MODERN
    }

    // Uygulama için gerekli işlemin tamamlanıp tamamlanmadığını belirtir.
    var isActionCompleted = false

    // Splash ekranının minimum gösterim süresinin tamamlanıp tamamlanmadığını belirtir.
    var isSplashDelayCompleted = false

    // Kullanılacak yöntemi buradan değiştirebilirsin.
    private val splashMode = SplashMode.MODERN

    // LEGACY yöntemi için XML içerisindeki splash ekranını tutar.
    // Nullable (?) olarak tanımlanmıştır çünkü yalnızca LEGACY modunda atanır.
    private var legacySplashView: ConstraintLayout? = null

    // Handler, belirli işlemleri ana iş parçacığında (Main Thread) çalıştırmamızı sağlar.
    // Looper.getMainLooper() uygulamanın ana iş parçacığının Looper nesnesini verir.
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {

        // MODERN yöntemde AndroidX SplashScreen API kullanılır.
        // installSplashScreen(), super.onCreate() çağrısından önce çalıştırılmalıdır.
        if (splashMode == SplashMode.MODERN) {

            // Android'in sistem splash ekranını etkinleştirir.
            val splashScreen = installSplashScreen()

            // Bu koşul true olduğu sürece sistem splash ekranı gösterilmeye devam eder.
            // Her iki işlem de tamamlandığında koşul false döner ve splash ekranı kapanır.
            splashScreen.setKeepOnScreenCondition {
                !isActionCompleted || !isSplashDelayCompleted
            }

        } else {

            // LEGACY yöntemde kullanılacak temayı ayarlar.
            // setTheme(), super.onCreate() çağrısından önce yapılmalıdır.
            setTheme(R.style.Theme_SplashBasics)
        }

        super.onCreate(savedInstanceState)

        // Uygulamanın sistem çubuklarının (status bar ve navigation bar)
        // arkasına kadar çizilmesini sağlar.
        enableEdgeToEdge()

        // Seçilen splash ekranı yöntemine göre ilgili layout yüklenir.
        when (splashMode) {

            SplashMode.LEGACY -> {

                // Özel olarak hazırladığımız XML splash ekranını yükler.
                setContentView(R.layout.activity_main_legacy)

                // XML içerisindeki splash ekranı bileşenini bulup değişkene atar.
                legacySplashView = findViewById(R.id.splashScreen)
            }

            SplashMode.MODERN -> {

                // AndroidX SplashScreen API kullanıldığında doğrudan ana ekran yüklenir.
                // Splash ekranının görünümü ve davranışı tema üzerinden yönetilir.
                setContentView(R.layout.activity_main)
            }
        }

        // Sistem çubuklarının kapladığı alanı hesaplar.
        // Böylece içerik status bar ve navigation bar ile çakışmaz.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // View bileşeninin iç boşluklarını sistem çubuklarının boyutlarına göre ayarlar.
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Gerekli işlemi simüle eden görevi başlatır.
        startLongRunningTask()

        // Splash ekranının minimum gösterim süresini başlatır.
        startSplashDelay()
    }

    // Uygulamanın açılışında yapılacak uzun süren bir işlemi simüle eder.
    private fun startLongRunningTask() {

        // 1000 milisaniye (1 saniye) sonra çalışır.
        handler.postDelayed({

            // İşlemin tamamlandığını belirtir.
            isActionCompleted = true

            // LEGACY modunda splash ekranının kapanması gerekip gerekmediğini kontrol eder.
            updateLegacySplashVisibility()

        }, 1000)
    }

    // Splash ekranının belirli bir süre boyunca gösterilmesini sağlar.
    private fun startSplashDelay() {

        // 2500 milisaniye (2,5 saniye) sonra çalışır.
        handler.postDelayed({

            // Bekleme süresinin tamamlandığını belirtir.
            isSplashDelayCompleted = true

            // LEGACY modunda splash ekranının kapanması gerekip gerekmediğini kontrol eder.
            updateLegacySplashVisibility()

        }, 2500)
    }

    // Yalnızca LEGACY yöntemde kullanılan splash ekranının görünürlüğünü yönetir.
    private fun updateLegacySplashVisibility() {

        // Hem gerekli işlem tamamlandıysa hem de minimum bekleme süresi dolduysa
        // özel splash ekranını gizler.
        if (
            splashMode == SplashMode.LEGACY &&
            isActionCompleted &&
            isSplashDelayCompleted
        ) {
            // GONE, bileşeni hem görünmez yapar hem de layout içerisindeki alanını kaldırır.
            legacySplashView?.visibility = View.GONE
        }
    }
}
