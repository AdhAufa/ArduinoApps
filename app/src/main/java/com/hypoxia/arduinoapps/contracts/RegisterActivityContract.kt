package com.hypoxia.arduinoapps.contracts

interface RegisterActivityContract {
    interface RegisterActivityView{
        fun showToast(message : String)
        fun successRegister()
        fun showLoading()
        fun hideLoading()
    }

    interface RegisterActivityPresenter{
        fun register (name : String,username : String,gender : String, noHp : String, email: String, password : String)
        fun destroy()
    }
}