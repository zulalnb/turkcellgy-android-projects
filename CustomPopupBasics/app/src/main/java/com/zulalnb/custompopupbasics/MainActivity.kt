
package com.zulalnb.custompopupbasics

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val btnShowWarning = findViewById<Button>(R.id.btnShowWarning)

        btnShowWarning.setOnClickListener {
            showWarningPopup()
        }
    }

    private fun showWarningPopup() {
        showCustomPopup(
            title = "İşleme Devam Edilsin mi?",
            message = "Bu işlemi gerçekleştirmek istediğinizden emin misiniz?",
            onConfirm = ::onConfirm
        )
    }

    private fun onConfirm() {
        Toast.makeText(
            this,
            "İşlem başarıyla onaylandı.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCustomPopup(
        title: String,
        message: String,
        onConfirm: () -> Unit
    ) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_warning, null)

        val warningPopup = PopupWindow(
            popupView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        /* DEPRECATED
        val warningPopup = PopupWindow(
            popupView,
            windowManager.defaultDisplay.width,
            windowManager.defaultDisplay.height
        )
         */

        val tvPopupTitle = popupView.findViewById<TextView>(R.id.tvPopupTitle)
        val tvPopupMessage = popupView.findViewById<TextView>(R.id.tvPopupMessage)
        val btnPopupCancel = popupView.findViewById<Button>(R.id.btnPopupCancel)
        val btnPopupConfirm = popupView.findViewById<Button>(R.id.btnPopupConfirm)

        tvPopupTitle.text = title
        tvPopupMessage.text = message

        btnPopupCancel.setOnClickListener {
            warningPopup.dismiss()
        }

        btnPopupConfirm.setOnClickListener {
            onConfirm()
            warningPopup.dismiss()
        }

        warningPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0)
    }
}
