package com.example.myapp.models

/* "status": 200,
    "successMsg": "Login successfull",*/

data class LoginResponse(val status:Int, val successMsg:String, val UserData: UserData)
