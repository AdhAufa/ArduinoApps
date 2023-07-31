package com.hypoxia.arduinoapps.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hypoxia.arduinoapps.contracts.GraphFragmentContract
import com.hypoxia.arduinoapps.databinding.FragmentGraphBinding
import com.hypoxia.arduinoapps.model.History
import com.hypoxia.arduinoapps.presenters.FragmentGraphPresenter
import com.hypoxia.arduinoapps.webservices.Constants
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

class GraphFragment : Fragment(), GraphFragmentContract.View {
    private var _binding : FragmentGraphBinding? = null
    private val binding get() = _binding!!
    private var presenter : GraphFragmentContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        presenter = FragmentGraphPresenter(this)
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

    override fun attachHistoryToGraph(listHistory: List<History>) {
        val results = mutableListOf<Entry>()
        val oxygens = mutableListOf<Entry>()
        val hearts = mutableListOf<Entry>()

        listHistory.forEachIndexed { index, history ->
            val result = Entry(index.toFloat(), history.result!!.toFloat())
            val oxy = Entry(index.toFloat(), history.oxy_rate!!.toFloat())
            val heart = Entry(index.toFloat(), history.heart_rate!!.toFloat())
            results.add(result)
            oxygens.add(oxy)
            hearts.add(heart)
        }

        val dataSetResult = LineDataSet(results, "Result")
        dataSetResult.color = Color.BLUE
        dataSetResult.valueTextColor = Color.BLUE

        val dataSetOxygen = LineDataSet(oxygens, "Oxygen")
        dataSetOxygen.color = Color.GREEN
        dataSetOxygen.valueTextColor = Color.GREEN

        val dataSetHeart = LineDataSet(hearts, "Heart rate")
        dataSetHeart.color = Color.RED
        dataSetHeart.valueTextColor = Color.RED

        val lineData = LineData(dataSetResult, dataSetOxygen,dataSetHeart )
        binding.lineChart.apply {
            setTouchEnabled(true)
            setPinchZoom(true)
            data = lineData
            invalidate()
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
}