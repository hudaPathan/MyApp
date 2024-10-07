package com.example.myapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    val intent1= Intent(this,UserDetails::class.java)
        edtEmail= findViewById(R.id.Email)
        edtPassword= findViewById(R.id.Password)

        btLogin.setOnClickListener {
            val username = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val ip="192.168.1.1"


            if (username.isEmpty()) {
                edtEmail.error = "Email required"
                edtEmail.requestFocus();
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edtPassword.error = "Password required"
                edtPassword.requestFocus();
                return@setOnClickListener
            }
            RetrofitClient.instance.login(username, password,ip ).enqueue(object : Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                        if (response.body()?.status==200)
                        {

                            if (response.body()?.UserData?.id.isNullOrEmpty())
                        {
                            Toast.makeText(applicationContext, "Wrong Credentials", Toast.LENGTH_LONG).show()
                        }
                            else
                        {
                            Toast.makeText(applicationContext, "Logged In Successfully as:"+response.body()?.UserData?.name.toString(), Toast.LENGTH_LONG).show()
                            saveMerchantId(this@LoginActivity,response.body()?.UserData?.id.toString(), response.body()?.UserData?.name.toString())
                           // Toast.makeText(applicationContext, "From Shared Prefrence: "+ getMerchantId(this@LoginActivity), Toast.LENGTH_LONG).show()

                            startActivity(intent1)



                        }
                        }
                    else
                        {
                            Toast.makeText(applicationContext,response.body()?.successMsg.toString(), Toast.LENGTH_LONG).show()
                        }
                }
            })
        }
    }


    fun saveMerchantId(context: Context, merchantId: String, name: String) {
        // Get the SharedPreferences instance
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        // Edit the SharedPreferences to save the value
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("merchant_id", merchantId)
        editor.putString("name", name)

        // Apply changes asynchronously
        editor.apply()
    }


}