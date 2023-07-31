package com.hypoxia.arduinoapps.contracts

import com.hypoxia.arduinoapps.model.History

interface HistoryFragmentContract {
    interface View{
        fun attachHistoryToRecycler(listHistory : List<History>)
        fun showToast(message: String)
        fun showEmpty()
        fun hideEmpty()
        fun onItemDelete(history : History)
    }

    interface Presenter{
        fun getHistory(token : String)
        fun deleteWishlist(token : String, id : Int)
        fun destroy()
    }

}