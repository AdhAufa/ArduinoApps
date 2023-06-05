package com.example.arduinoapps.contracts

import android.content.Context

interface LoginActivityContrac {
    interface View{
        fun showToast(message : String)
        fun successLogin()
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter{
        fun login(username : String, password: String, context : Context)
        fun destroy()
    }
}