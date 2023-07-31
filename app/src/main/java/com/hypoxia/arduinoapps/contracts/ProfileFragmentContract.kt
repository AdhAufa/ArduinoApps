package com.hypoxia.arduinoapps.contracts

import com.hypoxia.arduinoapps.model.User

interface ProfileFragmentContract {
    interface View{
        fun showUserToView(user : User)
        fun showToast(message: String)
    }

    interface Presenter{
        fun getUser(token : String)
        fun destroy()
    }
}