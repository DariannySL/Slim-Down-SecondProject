package com.example.slimdown

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_approach.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class ApproachActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approach)

        var intent = intent

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val lastName = intent.getStringExtra("lastName")

        helloNameTextView.text = "Hola, ${name}"

        if (id != null && name != null && lastName != null) {
            btn_approach.setOnClickListener {
                if (actualWeightTextView.text.isNotEmpty() && lostWeightTextView.text.isNotEmpty()) {
                    addApproach(id, name, lastName)
                }
                else {
                    Toast.makeText(this, "No se admiten campos vacios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addApproach(id: String, name: String, lastName: String) {

        if(actualWeightTextView.text.isNotEmpty() && lostWeightTextView.text.isNotEmpty()) {

            FirebaseServices().updateUser(id, name, lastName,
                actualWeightTextView.text.toString().toFloat(),
                lostWeightTextView.text.toString().toFloat())

            showProgress()
        }
    }

    private fun inputValidate() {
        when {
            TextUtils.isEmpty(actualWeightTextView.text) -> {
                nameTextView.error = "Este campo es requerido"
                false
            }

            TextUtils.isEmpty(lostWeightTextView.text) -> {
                lastNameTextView.error = "Este campo es requerido"
                false
            }

            else -> true
        }
    }

    override fun onBackPressed() {
        super.moveTaskToBack(true)
    }

    private fun showProgress() {
        val intent = Intent(this, ProgressActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }


}