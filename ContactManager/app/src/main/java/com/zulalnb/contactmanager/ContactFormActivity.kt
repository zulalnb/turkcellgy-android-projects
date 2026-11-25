package com.zulalnb.contactmanager

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactFormActivity : AppCompatActivity() {

    private lateinit var btnBack: Button
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText

    private var contact: Contact? = null
    private lateinit var contactDAO: ContactDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        contactDAO = ContactDAO(this)

        btnBack = findViewById(R.id.btnBack)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)

        btnBack.setOnClickListener {
            finish()
        }

        val contactId = intent.getIntExtra("id", -1)

        if (contactId == -1) {
            contact = null
            btnDelete.visibility = View.GONE
        } else {
            contact = contactDAO.getById(contactId)
            contact?.let { c ->
                etFirstName.setText(c.firstName)
                etLastName.setText(c.lastName)
                etEmail.setText(c.email)
            }

            btnDelete.visibility = View.VISIBLE
        }

        btnSave.setOnClickListener {
            saveContact()
        }

        btnDelete.setOnClickListener {
            deleteContact()
        }
    }

    private fun saveContact() {
        hideKeyboard()

        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showErrorDialog("Lütfen tüm alanları doldurunuz.")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorDialog("Lütfen geçerli bir e-posta adresi giriniz.")
            return
        }

        if (contactDAO.isEmailExists(email, excludeId = contact?.id)) {
            showErrorDialog("Bu e-posta adresi zaten kayıtlı.")
            return
        }

        setFormEnabled(false)

        try {
            if (contact == null) {
                val newContact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
                val result = contactDAO.add(newContact)
                if (result == -1L) {
                    showErrorDialog("Kişi eklenirken bir hata oluştu.")
                } else {
                    contact = contactDAO.getById(result.toInt())
                    btnDelete.visibility = View.VISIBLE
                    Toast.makeText(this, "Kişi eklendi.", Toast.LENGTH_SHORT).show()
                }
            } else {
                val updatedContact = Contact(
                    id = contact?.id,
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
                val rowsUpdated = contactDAO.update(updatedContact)
                if (rowsUpdated <= 0) {
                    showErrorDialog("Kişi güncellenirken bir hata oluştu.")
                } else {
                    contact = updatedContact
                    btnDelete.visibility = View.VISIBLE
                    Toast.makeText(this, "Kişi güncellendi.", Toast.LENGTH_SHORT).show()
                }
            }
        } finally {
            setFormEnabled(true)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        val currentView = currentFocus ?: findViewById(R.id.main)
        inputMethodManager?.hideSoftInputFromWindow(currentView.windowToken, 0)
    }

    private fun setFormEnabled(enabled: Boolean) {
        etFirstName.isEnabled = enabled
        etLastName.isEnabled = enabled
        etEmail.isEnabled = enabled
        btnSave.isEnabled = enabled
        btnDelete.isEnabled = enabled
        btnBack.isEnabled = enabled
    }

    private fun deleteContact() {
        val currentContact = contact
        if (currentContact?.id == null) {
            showErrorDialog("Silinecek kişi bulunamadı.")
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Kişiyi Sil")
            .setMessage("Bu kişiyi silmek istediğinizden emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                val rowsDeleted = contactDAO.delete(currentContact.id!!)
                if (rowsDeleted <= 0) {
                    showErrorDialog("Kişi silinirken bir hata oluştu.")
                } else {
                    Toast.makeText(applicationContext, "Kişi silindi.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Hata")
            .setMessage(message)
            .setPositiveButton("Tamam", null)
            .show()
    }
}
