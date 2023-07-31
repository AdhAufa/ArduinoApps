package com.hypoxia.arduinoapps.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hypoxia.arduinoapps.databinding.ListHistoryBinding
import com.hypoxia.arduinoapps.model.History

class HistoryAdapter (private var listHistory : List<History>,
                      val listener : HistoryListener): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(val binding: ListHistoryBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        holder.binding.apply {
            Heartrate.text = listHistory[position].heart_rate
            TVHeartrate.text = listHistory[position].status_heart_rate
            Spo.text = listHistory[position].oxy_rate
            TvSPo.text = listHistory[position].status_oxy_rate
            Result.text = listHistory[position].result
            TVResult.text = listHistory[position].category
            TVDate.text = listHistory[position].date
        }

        holder.itemView.setOnClickListener {
            listener.onHistoryClickListener(listHistory[position])
        }

        holder.itemView.setOnLongClickListener {
            listener.deleteHistory(listHistory[position].id!!)
            true
        }

    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newListHistory: List<History>) {
        listHistory = newListHistory
        notifyDataSetChanged()
    }
}

interface HistoryListener{
    fun onHistoryClickListener(history: History)
    fun deleteHistory(id : Int)

}