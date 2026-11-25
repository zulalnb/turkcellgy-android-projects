# Spinner Basics

Bu proje, Android'de Adapter yapısını ve Spinner bileşeninin kullanımını öğrenmek amacıyla hazırlanmıştır.

Uygulamada `strings.xml` içerisinde tanımlanan meyve listesi bir `ArrayAdapter` aracılığıyla Spinner bileşenine bağlanır. Kullanıcı listeden bir meyve seçtiğinde seçilen değer ekranda gösterilir.

## Ekran Görüntüsü

<p align="center">
    <img src="./screenshots/home.gif" alt="Spinner Basics uygulama ekranı" width="300" />
</p>

## Ele Alınan Konular

- Adapter kavramı
- ArrayAdapter
- Spinner
- String Array kaynakları
- `ArrayAdapter.createFromResource()`
- `setDropDownViewResource()`
- `OnItemSelectedListener`
- `onItemSelected()`

## 1. Adapter Nedir?

Adapter, veri kaynakları ile kullanıcı arayüzündeki listeleme bileşenleri arasında bağlantı kuran yapıdır.

Veri kaynağındaki öğelerin ilgili UI bileşenlerinde görüntülenmesini sağlar. Böylece verilerin hazırlanması ve arayüzde gösterilmesi birbirinden ayrılır.

Android'de Adapter yapısı özellikle `Spinner`, `ListView` ve `GridView` gibi veri gösteren bileşenlerde kullanılır.

## 2. ArrayAdapter

`ArrayAdapter`, bir Array veya List içerisindeki verileri Android UI bileşenlerine bağlamak için kullanılan Adapter türüdür.

Projede meyve isimleri `strings.xml` dosyasında bir String Array olarak tanımlanmıştır.

```xml
<string-array name="fruits">
    <item>Elma</item>
    <item>Muz</item>
    <item>Portakal</item>
    <item>Çilek</item>
    <item>Karpuz</item>
    <item>Ananas</item>
    <item>Mango</item>
    <item>Üzüm</item>
    <item>Şeftali</item>
    <item>Kiraz</item>
</string-array>
```

Bu kaynağa Kotlin üzerinden `R.array.fruits` referansıyla erişilebilir.

### ArrayAdapter Oluşturma

Projede Adapter, `ArrayAdapter.createFromResource()` metodu kullanılarak oluşturulmuştur.

```kotlin
val adapter = ArrayAdapter.createFromResource(
    this,
    R.array.fruits,
    android.R.layout.simple_spinner_item
)

adapter.setDropDownViewResource(
    android.R.layout.simple_spinner_dropdown_item
)
```

Parametreler:

- **Context:** Adapter'ın kullanılacağı Activity context'ini belirtir.
- **Resource:** Verilerin bulunduğu String Array kaynağını belirtir.
- **Text View Resource:** Spinner kapalı durumdayken seçili öğenin görünümünde kullanılacak hazır layout'u belirtir.

`setDropDownViewResource()` ise liste açıldığında öğelerin nasıl görüntüleneceğini belirler.

Kullanılan hazır layout'lar:

- `simple_spinner_item`: Spinner'ın seçili öğe görünümünde kullanılır.
- `simple_spinner_dropdown_item`: Açılan seçim listesindeki öğelerin görünümünde kullanılır.

## 3. Spinner

`Spinner`, kullanıcıya açılır liste (dropdown) üzerinden seçenekler sunan Android UI bileşenidir.

Kullanıcı Spinner'a dokunduğunda seçenekler görüntülenir ve listeden bir öğe seçebilir.

XML içerisinde Spinner tanımı:

```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:spinnerMode="dropdown" />
```

Adapter, oluşturulduktan sonra Spinner'a atanır:

```kotlin
val spinner = findViewById<Spinner>(R.id.spinner)
spinner.adapter = adapter
```

Bu işlemle Adapter içerisindeki veriler Spinner üzerinde görüntülenebilir hâle gelir.

## 4. Spinner Seçim Olayı

Spinner üzerinde seçilen öğeyi takip etmek için `OnItemSelectedListener` kullanılır.

```kotlin
spinner.onItemSelectedListener =
    object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val selectedFruit = spinner.adapter.getItem(position)
            tvSelectedFruit.text = "Seçilen meyve: $selectedFruit"
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
```

`OnItemSelectedListener` iki metot içerir:

- **onItemSelected():** Bir öğe seçildiğinde çağrılır.
- **onNothingSelected():** Herhangi bir öğe seçili olmadığında çağrılır.

`onItemSelected()` parametreleri:

- `parent`: Seçimin yapıldığı AdapterView nesnesi.
- `view`: Seçilen öğenin View nesnesi.
- `position`: Seçilen öğenin listedeki indeksi.
- `id`: Seçilen öğenin Adapter tarafından sağlanan kimliği.

Projede `position` üzerinden seçilen öğeye erişilir ve sonuç `TextView` üzerinde gösterilir.

## Uygulama Akışı

1. Meyve listesi `strings.xml` içerisinde tanımlanır.
2. `ArrayAdapter.createFromResource()` ile Adapter oluşturulur.
3. Dropdown görünümü `setDropDownViewResource()` ile belirlenir.
4. Adapter, Spinner bileşenine atanır.
5. `OnItemSelectedListener` ile seçim olayı dinlenir.
6. Seçilen meyve `TextView` üzerinde gösterilir.

## Kullanılan Teknolojiler

- Kotlin
- Android SDK
- XML Layout
- ConstraintLayout
- ArrayAdapter
- Spinner
- AndroidX AppCompat
- AndroidX Core KTX

## Öğrenme Kazanımları

- Adapter yapısının amacını ve çalışma mantığını anlama.
- ArrayAdapter ile veri kaynağını UI bileşenlerine bağlama.
- String Array kaynaklarını Kotlin üzerinden kullanma.
- Spinner ile açılır seçim listesi oluşturma.
- Dropdown görünümünü özelleştirme.
- OnItemSelectedListener ile seçim olaylarını yönetme.
