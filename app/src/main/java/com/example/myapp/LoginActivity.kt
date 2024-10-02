package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp.Api.RetrofitClient
import com.example.myapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var btLogin : Button
    private lateinit var btRegister : Button
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btLogin= findViewById(R.id.btnLogin)
        btRegister= findViewById(R.id.btnregister)
        btRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


        edtEmail= findViewById(R.id.Email)
        edtPassword= findViewById(R.id.Password)

        btLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()


            if (email.isEmpty()) {
                edtEmail.error = "Email required"
                edtEmail.requestFocus();
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edtPassword.error = "Password required"
                edtPassword.requestFocus();
                return@setOnClickListener
            }
            RetrofitClient.instance.login(email, password).enqueue(object : Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                        if (response.body()?.status==200)
                        {
                            Toast.makeText(applicationContext, "status: "+response.body()?.status.toString(), Toast.LENGTH_LONG).show()
                            Toast.makeText(applicationContext, "success Message"+response.body()?.successMsg.toString(), Toast.LENGTH_LONG).show()

                        }
                    else
                        {
                            Toast.makeText(applicationContext,response.body()?.successMsg.toString(), Toast.LENGTH_LONG).show()
                        }
                }
            })
        }
    }
}