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
import com.example.loginplusrecycler.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private var passwordVisible:Boolean=false
    private var confirmPasswordVisible:Boolean=false
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var  auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityRegisterBinding.inflate(layoutInflater)
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
                binding.etPassword.inputType=InputType.TYPE_CLASS_TEXT ; TYPE_TEXT_VARIATION_PASSWORD
                binding.ivEyeP.setImageResource(R.drawable.baseline_visibility_24)
            }
            else{
                passwordVisible=true
                binding.etPassword.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivEyeP.setImageResource(R.drawable.baseline_visibility_off_24)
            }
            binding.etPassword.setSelection(binding.etPassword.length())

        }
        binding.ivEyeCP.setOnClickListener {
            if (confirmPasswordVisible){
                confirmPasswordVisible=false
                binding.etConPass.inputType=InputType.TYPE_CLASS_TEXT ; TYPE_TEXT_VARIATION_PASSWORD
                binding.ivEyeCP.setImageResource(R.drawable.baseline_visibility_24)
            }
            else{
                confirmPasswordVisible=true
                binding.etConPass.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivEyeCP.setImageResource(R.drawable.baseline_visibility_off_24)


            }
            binding.etConPass.setSelection(binding.etConPass.length())

        }
        auth=Firebase.auth
        auth=FirebaseAuth.getInstance()
        binding.tvDonthaveacc.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
           if (checkAllField()){
               val email=binding.etEmail.text.toString()
               val password=binding.etPassword.text.toString()

               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                   if (it.isSuccessful){
                       auth.signOut()
                       Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show()
                       val intent=Intent(this,LoginActivity::class.java)
                       startActivity(intent)
                       finish()

                   }
                   else{
                       Log.e("error: ", it.exception.toString())
                   }
               }

           }

        }
    }
    private fun checkAllField():Boolean{
        val email:String=binding.etEmail.text.toString()
        val password:String=binding.etPassword.text.toString()
        val name:String=binding.etFullName.text.toString()
        val confirmPassword:String=binding.etConPass.text.toString()
        val mobile:String=binding.etMobile.text.toString()

        if (TextUtils.isEmpty(name)){
            binding.etFullName.error = "Full name is required"
            return false
        }
        if (TextUtils.isEmpty(email)){
            binding.etEmail.error = "Email is required"
            return false

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Check email format"
            return false

        }
        if (TextUtils.isEmpty(mobile)){
            binding.etMobile.error = "Mobile No. is required"
            return false

        }
        if (TextUtils.isEmpty(password)){
            binding.etPassword.error = "Password is required"
            return false
        }
        if (confirmPassword.isEmpty()|| password != confirmPassword){
            binding.etConPass.error = "Password Mismatched"
            return false

        }
        if (password.length<6) {
            binding.etPassword.error = "Password must be >= 6 Characters"
            return false
        }

            return true
    }


}