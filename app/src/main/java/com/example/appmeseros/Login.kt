package com.example.appmeseros

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("mensaje", "intrgreracion compkleta")
        analytics.logEvent("ini", bundle)
    }


    private fun setup() {
        val btnSignUp: Button = findViewById(R.id.registrar)
        val btnLogIn: Button = findViewById(R.id.ingresar)
        val edtEmail: EditText = findViewById(R.id.etCorreo)
        val edtPassword: EditText = findViewById(R.id.etContra)

        btnSignUp.setOnClickListener {
            if (edtEmail.text.isNotEmpty() && edtPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                ).addOnCompleteListener {

                    if (it.isSuccessful) {

                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            } else {
                showAlert()
            }
        }

        btnLogIn.setOnClickListener {

            if (edtEmail.text.isNotEmpty() && edtPassword.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful)
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    else
                        showAlert()
                }
            } else
                showAlert()
        }
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}