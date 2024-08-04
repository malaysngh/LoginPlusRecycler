package com.example.loginplusrecycler

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginplusrecycler.databinding.ActivityForgotPasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityForgotPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets



        }
        auth=Firebase.auth
        auth=FirebaseAuth.getInstance()
        binding.btnForgotPassword.setOnClickListener {
            val email= binding.etEmailFP.text.toString()
            if (checkEmail()){

                auth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Email Sent!",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }
            }


        }
    }
    private fun checkEmail():Boolean{
        val email: String = binding.etEmailFP.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.etEmailFP.error = "Email is required"
            return false

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailFP.error="Check email format"
            return false

        }
        return true
    }
}