package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapp.Api.RetrofitClient
import com.example.myapp.models.LoginResponse
import com.example.myapp.models.UserDetailsResponse
import okhttp3.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetails : AppCompatActivity() {    private lateinit var txtname :TextView
    private lateinit var txtAddress: TextView
   private lateinit var txtPhone: TextView
    private lateinit var imgProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtname=findViewById(R.id.name)
        txtPhone= findViewById(R.id.phone)
        txtAddress= findViewById(R.id.address)
        imgProfile=findViewById(R.id.logoimage)


        val (merchantId, name) = getMerchantId(this@UserDetails)
        txtname.setText(name.toString())


        RetrofitClient.instance.info("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudF9pZCI6IjQxMyJ9.zRIu9DQXJjIKaFdUzbQvbBH1bbzZzyA0tiO0nXS3VI8",merchantId.toString()).enqueue(object : Callback<UserDetailsResponse>{


            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                //Toast.makeText(applicationContext, response.body()?.status.toString(), Toast.LENGTH_LONG).show()
               txtAddress.setText(response.body()?.UserData?.address)
                txtPhone.setText(response.body()?.UserData?.phone)


                Glide.with(applicationContext)
                    .load(response.body()?.UserData?.logo)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(imgProfile)


            }
        })


    }
        fun getMerchantId(context: Context):Pair<String?, String?> {
            // Get the SharedPreferences instance
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
            // Retrieve the value. If it doesn't exist, it returns null.
            val merchantId = sharedPreferences.getString("merchant_id", null)
            val name = sharedPreferences.getString("name", null)
            return Pair(merchantId, name)
        }    }




