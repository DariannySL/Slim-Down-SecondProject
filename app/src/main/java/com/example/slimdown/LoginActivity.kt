package com.example.slimdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.example.slimdown.Class.Users
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        link_singIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java).apply { }
            startActivity(intent)
        }

        login()
    }

    private fun login() {
        title = "Iniciar Sesion"

        btn_login.setOnClickListener {
            if(emailLTextView.text.isNotEmpty() && passwordLTexView.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailLTextView.text.toString(),
                        passwordLTexView.text.toString()).addOnCompleteListener() {

                        if (it.isSuccessful) {
                            showProgress()
                        }
                        else {
                            showAlert()
                        }

                    }
            }
            else {
                inputValidate()
            }
        }
    }

    private fun inputValidate() {
        when {
            TextUtils.isEmpty(emailLTextView.text) -> {
                emailLTextView.error = "Este campo es requerido"
                false
            }

            TextUtils.isEmpty(passwordLTexView.text) -> {
                emailLTextView.error = "Este campo es requerido"
                false
            }

            else -> true
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El correo o la contraseña no coinciden, intente de nuevo")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        super.moveTaskToBack(true)
    }

    private fun showProgress() {
        val intent = Intent(this, ProgressActivity::class.java)
        startActivity(intent)
    }

//    fun validateShowApproach()
//    {
//        FirebaseServices().getUserById(FirebaseAuth.getInstance().currentUser!!.uid, object : IResult<Users> {
//
//            override fun onSuccess(items: Users?) {
//
//                if(items?.actualWeight == null || items.lostWeight == null) {
//                    showApproach()
//                }
//                else
//                {
//                    showProgress()
//                }
//            }
//
//            override fun onError(exception: Exception) {
//
//            }
//        })
//    }

//    private fun showApproach() {
//        val intent = Intent(this, ApproachActivity::class.java).apply { }
//        startActivity(intent)
//    }
}