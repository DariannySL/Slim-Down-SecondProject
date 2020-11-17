package com.example.slimdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_progress.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class AddProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_progress)

        addProgressButton.setOnClickListener {
            if(weightTextNumber.text.isNotEmpty())
            {
                addProgress()
            }
            else {
                Toast.makeText(this, "No se admiten campos vacios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addProgress(){

        val datePicker = datePickerView
        val date = Date(datePicker.year - 1900, datePicker.month, datePicker.dayOfMonth)
        val weight = weightTextNumber.text.toString().toFloat()

        if(weightTextNumber.text.isNotEmpty()) {

            FirebaseServices().addWeight(FirebaseAuth.getInstance().currentUser!!.uid, weight, date)
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun inputValidate() {
        when {
            TextUtils.isEmpty(weightTextNumber.text) -> {
                emailLTextView.error = "Este campo es requerido"
                false
            }

            else -> true
        }
    }
}