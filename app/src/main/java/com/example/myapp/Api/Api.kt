package com.example.myapp.Api

import android.media.tv.TvContract.Channels.Logo
import com.example.myapp.models.DefaultResponse
import com.example.myapp.models.LoginResponse
import com.example.myapp.models.UserDetailsResponse
import okhttp3.Address
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

public interface Api {
    @FormUrlEncoded
    @POST("login_v3_api/register")

    fun register(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("mob_no") mob_no:String,
        ): Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login_v3_api/login")
    fun login(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("ip") ip:String,
        ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("merchant_api/get_additional_user_info")
    fun info(
        @Header("X-Requested-With") authToken:String,
        @Field("merchant_id") merchant_id: String,
    ): Call<UserDetailsResponse>
}