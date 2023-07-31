package com.example.arduinoapps.contracts

import com.example.arduinoapps.model.History

interface GraphFragmentContract {

    interface View{
        fun attachHistoryToGraph(listHistory : List<History>)
        fun showToast(message: String)
        fun showEmpty()
        fun hideEmpty()
    }

    interface Presenter{
        fun getHistory(token : String)
        fun destroy()
    }

}