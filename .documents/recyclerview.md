# RecyclerView

## Genel Bakış

`RecyclerView`, Android uygulamalarında büyük veya dinamik veri kümelerini verimli şekilde listelemek ve görüntülemek için kullanılan bir View bileşenidir.

`ListView` ile karşılaştırıldığında daha esnek bir yapı sunar. `ListView` temel olarak dikey ve doğrusal listeleme için tasarlanmışken RecyclerView, farklı yerleşim düzenleri ve kaydırma yönleri oluşturulmasına olanak tanır.

RecyclerView'ın temel özelliklerinden biri, ekranda görünmeyen veya artık kullanılmayan item görünümlerini yeniden kullanarak bellek kullanımını optimize etmesidir. Bu işlem, her veri öğesi için sürekli yeni View oluşturulmasının önüne geçer.

RecyclerView üç temel bileşen üzerine kuruludur:

- **Adapter:** Verileri ViewHolder bileşenlerine bağlar ve liste öğelerinin yönetimini sağlar.
- **ViewHolder:** Her bir liste öğesinin View referanslarını tutar.
- **LayoutManager:** Liste öğelerinin ekranda nasıl yerleştirileceğini ve hangi yönde akacağını belirler.

## RecyclerView ve ListView Karşılaştırması

| Özellik | ListView | RecyclerView |
| --- | --- | --- |
| Listeleme | Dikey, doğrusal | Dikey, yatay, grid ve staggered grid |
| ViewHolder | Kullanımı önerilir, zorunlu değildir | Zorunludur |
| View yeniden kullanımı | `convertView` ile | RecyclerView tarafından yönetilir |
| LayoutManager | Bulunmaz | Bulunur |
| Animasyon ve güncelleme | Daha sınırlı | Daha esnek |
| Özelleştirme | Daha sınırlı | Daha geniş |

RecyclerView, farklı listeleme ihtiyaçlarında daha fazla kontrol ve esneklik sağlar.

## LayoutManager

`LayoutManager`, RecyclerView içerisindeki item'ların nasıl konumlandırılacağını ve nasıl bir akış izleyeceğini belirler.

RecyclerView kütüphanesi üç temel LayoutManager sunar.

### LinearLayoutManager

Item'ları tek bir doğrultuda sıralar. Dikey veya yatay listeleme yapılabilir.

- **Vertical:** Item'lar alt alta sıralanır. ListView'ın klasik görünümüne benzer.
- **Horizontal:** Item'lar yan yana sıralanır ve yatay kaydırma yapılabilir.

Kullanım alanları: Haber listeleri, kişi listeleri, yatay içerik alanları ve standart liste görünümleri.

### GridLayoutManager

Item'ları satır ve sütunlardan oluşan bir grid düzeninde görüntüler.

Grid yapısında sütun sayısı veya yatay kullanımda satır sayısı belirlenebilir.

Kullanım alanları: Ürün katalogları, fotoğraf galerileri ve kategori listeleri.

### StaggeredGridLayoutManager

Item'ları grid düzeninde ancak farklı boyutlarda görüntülemeye olanak tanır. Böylece her item aynı yükseklikte veya genişlikte olmak zorunda değildir.

Kullanım alanları: Görsel ağırlıklı içerikler, Pinterest benzeri düzenler ve farklı boyutlarda kartların bulunduğu arayüzler.

## RecyclerView Bileşenleri

### ViewHolder

RecyclerView içerisindeki her item, bir ViewHolder nesnesi tarafından temsil edilir.

ViewHolder, item layout içerisindeki View referanslarını tutar. RecyclerView yeni bir item görünümüne ihtiyaç duyduğunda uygun ViewHolder nesnesini yeniden kullanabilir.

ViewHolder oluşturmak için `RecyclerView.ViewHolder` sınıfından türeyen bir sınıf tanımlanır.

```kotlin
class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: TextView = itemView.findViewById(R.id.tvName)
    val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
}
```

ViewHolder kullanımı, `findViewById()` çağrılarının her veri bağlama işleminde tekrarlanmasını önlemeye yardımcı olur.

### Adapter

Adapter, veri kaynağı ile RecyclerView arasında bağlantı kurar. Verilerin ViewHolder içerisinde görüntülenmesini ve liste öğelerinin yönetilmesini sağlar.

RecyclerView için özel bir adapter oluşturulurken `RecyclerView.Adapter` sınıfından türetilir.

Temel olarak aşağıdaki metotlar kullanılır:

| Metot | Görevi |
| --- | --- |
| `onCreateViewHolder()` | Yeni bir ViewHolder oluşturur ve item layout'u inflate eder. |
| `onBindViewHolder()` | İlgili pozisyondaki veriyi ViewHolder içerisindeki View bileşenlerine bağlar. |
| `getItemCount()` | Adapter içerisindeki toplam item sayısını döndürür. |

Adapter, veri listesindeki değişiklikleri RecyclerView'a bildirmekten de sorumludur.

### Item Güncellemeleri: DiffUtil ve ListAdapter

Liste içerisindeki veriler değiştiğinde RecyclerView'a hangi öğelerin eklendiğini, silindiğini veya güncellendiğini bildirmek gerekir.

Bunun için `notifyItemInserted()`, `notifyItemRemoved()` ve `notifyItemChanged()` gibi metotlar kullanılabilir.

Daha gelişmiş veri güncelleme senaryolarında `DiffUtil` ve `ListAdapter` tercih edilebilir.

- **DiffUtil:** İki liste arasındaki farklılıkları hesaplar ve gerekli güncellemelerin belirlenmesini sağlar.
- **ListAdapter:** DiffUtil altyapısını kullanarak liste güncellemelerini kolaylaştırır. Liste farklılıklarını arka planda hesaplar.

Yeni liste verileriyle çalışırken ListAdapter, manuel bildirim yönetimine göre daha pratik bir alternatif sunar.

## RecyclerView Kullanımı İçin Gereksinimler

RecyclerView kullanabilmek için temel olarak şu bileşenlere ihtiyaç vardır:

1. **Dependency:** RecyclerView kütüphanesinin projeye eklenmesi.
2. **RecyclerView:** Listeyi görüntüleyecek View bileşeninin layout içerisine eklenmesi.
3. **ViewHolder:** Her bir liste öğesinin View bileşenlerini tutacak sınıfın oluşturulması.
4. **Adapter:** Veri kaynağını ViewHolder bileşenlerine bağlayacak adapter sınıfının oluşturulması.
5. **LayoutManager:** Liste öğelerinin yerleşimini ve akış yönünü belirleyecek yapının tanımlanması.

### Dependency Ekleme

RecyclerView, AndroidX kütüphanesi olarak projeye dependency şeklinde eklenir.

`app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("androidx.recyclerview:recyclerview:1.4.0")
}
```

> Not: Bu doküman hazırlanırken RecyclerView kütüphanesinin güncel kararlı sürümü 1.4.0'dır. Projelerde kullanılan sürüm, AndroidX sürüm bilgilerine göre güncellenebilir.

## RecyclerView ve Pagination

Büyük veri kümelerinde tüm verileri aynı anda yüklemek yerine verileri parçalar hâlinde yüklemek tercih edilebilir.

Bu yaklaşım **pagination (sayfalama)** olarak adlandırılır.

RecyclerView ile sayfalama farklı şekillerde uygulanabilir:

- Kaydırma konumunu takip ederek yeni veri grupları yüklemek.
- API veya veritabanından sayfa bazlı veri almak.
- Android Jetpack Paging kütüphanesini kullanmak.

Burada önemli bir ayrım vardır: RecyclerView'ın kendisi otomatik olarak sayfalama yapmaz. Sayfalama davranışı uygulama veya Paging kütüphanesi tarafından yönetilir.

## RecyclerView ve Filtreleme

RecyclerView üzerinde arama ve filtreleme işlemleri uygulanabilir.

Filtreleme sırasında veri kaynağı, kullanıcının arama sorgusuna göre filtrelenir. Ardından güncellenmiş liste adapter'a iletilir.

Bu işlemde:

- Filtreleme sonucunda elde edilen liste adapter'a aktarılır.
- Adapter, yeni listeye göre item'ları görüntüler.
- Liste değişiklikleri `notifyDataSetChanged()` veya daha verimli güncelleme yöntemleriyle RecyclerView'a bildirilir.

Daha gelişmiş uygulamalarda `ListAdapter` ve `DiffUtil` kullanılarak liste değişiklikleri daha kontrollü yönetilebilir.

## Güncel Android Yaklaşımı

RecyclerView, XML tabanlı ve View tabanlı Android arayüzlerinde listeleme için kullanılmaya devam etmektedir.

Bununla birlikte Android geliştirme ekosisteminde Jetpack Compose, yeni arayüz geliştirmeleri için önerilen modern yaklaşımdır. Compose tarafında listeleme ihtiyaçları `LazyColumn` ve `LazyRow` gibi bileşenlerle karşılanabilir.

RecyclerView ise mevcut View tabanlı projelerde ve klasik Android UI geliştirmelerinde geçerliliğini korumaktadır.

## Kaynaklar

- [Create dynamic lists with RecyclerView – Android Developers](https://developer.android.com/develop/ui/views/layout/recyclerview)
- [RecyclerView API Reference](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView)
- [RecyclerView Release Notes](https://developer.android.com/jetpack/androidx/releases/recyclerview)
- [AndroidX Versions](https://developer.android.com/jetpack/androidx/versions)