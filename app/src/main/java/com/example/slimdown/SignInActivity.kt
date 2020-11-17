package com.example.slimdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_approach.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signIn()

        link_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply { }
            startActivity(intent)
        }

    }

    private fun signIn() {
        title = "Registrarse"

        btn_singIn.setOnClickListener {

            if(nameTextView.text.isNotEmpty() && lastNameTextView.text.isNotEmpty() &&
                emailTextView.text.isNotEmpty() && passwordTextView.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailTextView.text.toString(),
                    passwordTextView.text.toString()).addOnCompleteListener() {

                        if (it.isSuccessful) {

                            val id = it.result?.user?.uid?: ""
                            val name = nameTextView.text.toString()
                            val lastName = lastNameTextView.text.toString()

                            FirebaseServices().addUser(id, name, lastName, 0f, 0f)

                            showApproach(id, name, lastName)
                        }
                        else {
                            showAlert()
                        }
                    }
            }
            else
            {
                inputValidate()
            }
        }
    }

    private fun inputValidate() {
        when {
            TextUtils.isEmpty(nameTextView.text) -> {
                nameTextView.error = "Este campo es requerido"
                false
            }

            TextUtils.isEmpty(lastNameTextView.text) -> {
                lastNameTextView.error = "Este campo es requerido"
                false
            }

            TextUtils.isEmpty(emailTextView.text) -> {
                emailTextView.error = "Este campo es requerido"
                false
            }

            TextUtils.isEmpty(passwordTextView.text) -> {
                passwordTextView.error = "Este campo es requerido"
                false
            }

            else -> true
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Uno de los campos es incorrecto, intente de nuevo")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showApproach(id: String, name: String, lastName: String) {
        val intent = Intent(this, ApproachActivity::class.java)

        intent.putExtra("id", id)
        intent.putExtra("name", name)
        intent.putExtra("lastName", lastName)

        startActivity(intent)
    }
}