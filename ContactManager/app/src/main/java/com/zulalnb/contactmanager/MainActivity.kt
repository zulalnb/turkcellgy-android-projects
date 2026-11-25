package com.zulalnb.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
 Uygulama açılışında bir liste olacak ve bu listede kaydedilen kişilerin listesi bulunacak.
 Listenin bulunduğu ekranda ekranın sağ üst köşesindeki Yeni Kişi Ekle butonuna basıldığında yeni kişi ekleme sayfası açılacak.
 Yeni ekleme sayfasında kişilerin ad, soyad, eposta bilgileri alınacak ve kaydet butonuna basıldığında bilgiler veritabanına kaydedilecek.
 Kayıt işlemi sonrası giriş ekranına geri dönülecek ve yeni kişi listeye eklenecek.
 Listede veriler alfabetik olarak sıralı gelecek.
 Listedeki elemana basıldığında düzenleme ekranına gidilecek ve kişiler güncellenecek ve bilgiler silinip güncellenebilecek.
 Düzenleme ekranı yeni ekleme ekranı ile aynı tasarıma sahip olacak.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var btnNewContact: Button
    private lateinit var rvContacts: RecyclerView

    private lateinit var contactDAO: ContactDAO
    private lateinit var adapter: ContactAdapter
    private var contactsList: ArrayList<Contact> = ArrayList()

    private val pageSize = 20
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        contactDAO = ContactDAO(this)

        btnNewContact = findViewById(R.id.btnNewContact)
        btnNewContact.setOnClickListener {
            val intent = Intent(this, ContactFormActivity::class.java)
            startActivity(intent)
        }

        rvContacts = findViewById(R.id.rvContacts)
        val layoutManager = LinearLayoutManager(this)
        rvContacts.layoutManager = layoutManager
        rvContacts.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        setupAdapter()
        setupScrollListener(layoutManager)
    }

    override fun onResume() {
        super.onResume()
        resetAndLoadFirstPage()
    }

    private fun setupAdapter() {
        adapter = ContactAdapter(contactsList) { contact ->
            val intent = Intent(this, ContactFormActivity::class.java).apply {
                putExtra("id", contact.id)
            }
            startActivity(intent)
        }
        rvContacts.adapter = adapter
    }

    private fun setupScrollListener(layoutManager: LinearLayoutManager) {
        rvContacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadNextPage()
                    }
                }
            }
        })
    }

    private fun resetAndLoadFirstPage() {
        isLoading = true
        isLastPage = false

        val firstPage = contactDAO.getByOffset(limit = pageSize, offset = 0)
        isLastPage = firstPage.size < pageSize

        adapter.setContacts(firstPage)
        isLoading = false
    }

    private fun loadNextPage() {
        isLoading = true
        val offset = rvContacts.adapter?.itemCount ?: 0
        val nextPage = contactDAO.getByOffset(limit = pageSize, offset = offset)

        if (nextPage.isEmpty()) {
            isLastPage = true
        } else {
            isLastPage = nextPage.size < pageSize
            adapter.appendContacts(nextPage)
        }
        isLoading = false
    }
}
