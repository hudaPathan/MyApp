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
import com.example.myapp.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var btRegister : Button
    private lateinit var btLogin : Button

    private lateinit var edtUsername : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtName : EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtMobNo : EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        edtEmail=findViewById(R.id.etEmail)
        edtName=findViewById(R.id.etName)
        edtUsername=findViewById(R.id.etUsername)
        edtPassword=findViewById(R.id.etPassword)
        edtMobNo=findViewById(R.id.etMobNo)
        btRegister= findViewById(R.id.btnRegister)
        btLogin= findViewById(R.id.btnLogin)

        btLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btRegister.setOnClickListener {
             val email = edtEmail.text.toString()
             val password = edtPassword.text.toString()
             val name = edtName.text.toString()
             val username = edtUsername.text.toString()
             val mobNo = edtMobNo.text.toString()

             if (username.isEmpty())
             {
                 edtUsername.error="Username required"
                 edtUsername.requestFocus();
                 return@setOnClickListener
             }
             else  if (password.isEmpty())
             {
                 edtPassword.error="Username required"
                 edtPassword.requestFocus();
                 return@setOnClickListener
             }
             else  if (name.isEmpty())
             {
                 edtName.error="Username required"
                 edtName.requestFocus();
                 return@setOnClickListener
             }
             else  if (email.isEmpty())
             {
                 edtEmail.error="Username required"
                 edtEmail.requestFocus();
                 return@setOnClickListener
             }
             else  if (mobNo.isEmpty())
             {
                 edtMobNo.error="Username required"
                 edtName.requestFocus();
                 return@setOnClickListener
             }
             else
             {
                 RetrofitClient.instance.register(username, password, name, email, mobNo).enqueue(object: Callback<DefaultResponse>{
                     override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                         Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                     }

                     override fun onResponse(
                         call: Call<DefaultResponse>,
                         response: Response<DefaultResponse>
                     ) {
                         //Toast.makeText(applicationContext, response.body()?.error, Toast.LENGTH_LONG).show()
                         Toast.makeText(applicationContext, "Error Message" + response.body()?.error.toString() + "status: "+ response.body()?.status.toString(), Toast.LENGTH_LONG).show()

                     }
                 })
            }

        }}

    }
