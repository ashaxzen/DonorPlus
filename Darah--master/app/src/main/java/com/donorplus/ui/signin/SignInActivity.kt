package com.donorplus.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.donorplus.R
import com.donorplus.databinding.ActivitySignInBinding
import com.donorplus.ui.main.MainActivity
import com.donorplus.ui.signup.SignUpActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            auth = FirebaseAuth.getInstance()
            btnLgLogin.setOnClickListener {
                val email = edtLgEmail.text.toString()
                val password = edtLgPassword.text.toString()

                if (email.isEmpty()) {
                    edtLgEmail.error = "Email Harus Di Isi"
                    edtLgEmail.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtLgEmail.error = "Email Tidak Valid"
                    edtLgEmail.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    edtLgPassword.error = "Password Tidak Boleh Kosong"
                    edtLgPassword.requestFocus()
                    return@setOnClickListener
                }

                if (password.length < 8) {
                    edtLgPassword.error = "Password Tidak Boleh Kurang Dari 8"
                    edtLgPassword.requestFocus()
                    return@setOnClickListener
                }

                loginFirebase(email, password)
            }

            tvRegister.setOnClickListener {
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(intent)
            }


        }


    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat Datang ${email}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}