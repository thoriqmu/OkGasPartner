package com.kewirausahaan.okgaspartner.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kewirausahaan.okgaspartner.R
import com.kewirausahaan.okgaspartner.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        binding.registerButton.setOnClickListener {
            val name = binding.inputRegisterName.text.toString().trim()
            val email = binding.inputRegisterEmail.text.toString().trim()
            val phone = binding.inputPhone.text.toString().trim()
            val password = binding.inputRegisterPassword.text.toString().trim()
            val repeatPassword = binding.inputRegisterRepeatPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userMap = mapOf(
                            "name" to name,
                            "email" to email,
                            "phone" to phone,
                            "photo" to "",
                        )
                        database.child("partner").child(uid).setValue(userMap).addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Gagal menyimpan data mitra", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

