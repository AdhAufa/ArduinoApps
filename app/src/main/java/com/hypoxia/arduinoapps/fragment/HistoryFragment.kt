package com.hypoxia.arduinoapps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.hypoxia.arduinoapps.adapter.HistoryAdapter
import com.hypoxia.arduinoapps.adapter.HistoryListener
import com.hypoxia.arduinoapps.contracts.HistoryFragmentContract
import com.hypoxia.arduinoapps.databinding.FragmentHistoryBinding
import com.hypoxia.arduinoapps.model.History
import com.hypoxia.arduinoapps.presenters.FragmentHistoryPresenter
import com.hypoxia.arduinoapps.webservices.Constants


class HistoryFragment : Fragment(), HistoryFragmentContract.View {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var presenter : HistoryFragmentContract.Presenter? = null
    private lateinit var historyAdapter: HistoryAdapter
    private var listHistory : List<History> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        presenter = FragmentHistoryPresenter(this)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getHistory()
    }

    private fun getHistory(){
        val token = Constants.getToken(requireActivity())
        presenter?.getHistory(token)
    }


    override fun attachHistoryToRecycler(listHistory: List<History>) {
        this.listHistory = listHistory
        binding.RVHistory.apply {
            historyAdapter = HistoryAdapter(listHistory, object : HistoryListener{
                override fun onHistoryClickListener(history: History) {
                    showToast("history id : ${history.id}")
                }

                override fun deleteHistory(id: Int) {
                    val token = Constants.getToken(requireActivity())
                    presenter?.deleteWishlist(token, id)
                }
            })
            layoutManager = GridLayoutManager(activity, 2)
            adapter = historyAdapter
            historyAdapter.updateData(listHistory)
        }
    }
    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
    override fun showEmpty() {
        binding.empty.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideEmpty() {
        binding.empty.apply {
            visibility = View.GONE
        }
    }

    override fun onItemDelete(history: History) {
        listHistory = listHistory.filterNot { it.id == history.id }
        historyAdapter.updateData(listHistory)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}