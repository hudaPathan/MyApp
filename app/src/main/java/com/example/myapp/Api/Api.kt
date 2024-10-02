package com.example.myapp.Api

import com.example.myapp.models.DefaultResponse
import com.example.myapp.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

public interface Api {
    @FormUrlEncoded
    @POST("register")

    fun register(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("mob_no") mob_no:String,
        ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email:String,
        @Field("password") password:String,

    ): Call<LoginResponse>
}