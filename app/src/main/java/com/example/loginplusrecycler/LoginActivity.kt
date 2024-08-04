package com.example.loginplusrecycler


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginplusrecycler.databinding.ActivityLoginBinding

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private var passwordVisible:Boolean=false
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ivEyeP.setOnClickListener {
            if (passwordVisible){
                passwordVisible=false
                binding.et2.inputType= InputType.TYPE_CLASS_TEXT ; TYPE_TEXT_VARIATION_PASSWORD
                binding.ivEyeP.setImageResource(R.drawable.baseline_visibility_24)
            }
            else{
                passwordVisible=true
                binding.et2.inputType= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivEyeP.setImageResource(R.drawable.baseline_visibility_off_24)


            }
            binding.et2.setSelection(binding.et2.length())

        }
//
        binding.tvForgot.setOnClickListener{
            val intent=Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvDonthaveacc.setOnClickListener{
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        auth = Firebase.auth
        auth=FirebaseAuth.getInstance()
//
        binding.btnSignIn.setOnClickListener {
            val email: String = binding.et1.text.toString()
            val password: String = binding.et2.text.toString()
            if (checkAllField()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Signed in successfully", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else{
                        Log.e("error: ",it.exception.toString())
                    }               }


            }
        }
    }

    private fun checkAllField(): Boolean {
        val email: String = binding.et1.text.toString()
        val password: String = binding.et2.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.et1.error = "Email is required"
            return false

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.et1.error="Check email format"
            return false

        }
        if (TextUtils.isEmpty(password)) {
            binding.et2.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.et2.error = "Password must be >= 6 Characters"
            return false

        }
        return true

    }
}