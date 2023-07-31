package com.example.arduinoapps.contracts

import com.example.arduinoapps.model.History
import com.example.arduinoapps.model.User

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