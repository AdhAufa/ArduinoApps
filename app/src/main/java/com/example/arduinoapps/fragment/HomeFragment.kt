package com.example.arduinoapps.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arduinoapps.activity.DetailDisorderActivity
import com.example.arduinoapps.activity.DetailTipsActivity
import com.example.arduinoapps.activity.ProfileActivity
import com.example.arduinoapps.adapter.DisorderAdapter
import com.example.arduinoapps.adapter.TipsAdapter
import com.example.arduinoapps.databinding.FragmentHomeBinding
import com.example.arduinoapps.model.Disorder
import com.example.arduinoapps.model.Tips
import com.example.arduinoapps.webservices.Constants
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore
    private lateinit var tipsArrayList : ArrayList<Tips>
    private lateinit var tipsAdapter: TipsAdapter
    private lateinit var disorderAdapter: DisorderAdapter
    private lateinit var listDisorder : ArrayList<Disorder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        showTipsToRv()
        showDisorderToRv()
        hideProgressBar()
        goToProfile()
        setName()
        return binding.root
    }

    private fun setName(){
        val name = Constants.getName(requireActivity())
        binding.TvName.text = name
    }

    private fun goToProfile(){
        binding.TvName.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
    }

    fun showTipsToRv(){
        tipsArrayList = arrayListOf()
        tipsAdapter = TipsAdapter(tipsArrayList, object: TipsAdapter.onTipsClick{
            override fun onTipsClickListener(tips: Tips) {
                startActivity(Intent(activity, DetailTipsActivity::class.java).apply {
                    putExtra("tips", tips as Serializable)
                })
            }
        })

        db.collection("tips")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        tipsArrayList.add(dc.document.toObject(Tips::class.java))
                    }
                }
                tipsAdapter.notifyDataSetChanged()
            }

        binding.RvTips.apply {
            adapter = tipsAdapter
            val mLayout = LinearLayoutManager(activity)
            mLayout.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayout
        }

    }

    fun showDisorderToRv(){
        listDisorder = arrayListOf()
        disorderAdapter = DisorderAdapter(listDisorder, object : DisorderAdapter.onDisorderClick{
            override fun onDisorderClickListener(disorder: Disorder) {
                startActivity(Intent(activity, DetailDisorderActivity::class.java).apply {
                    putExtra("disorder", disorder as Serializable)
                })
            }
        })


        db.collection("disease")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        listDisorder.add(dc.document.toObject(Disorder::class.java))
                        println("data : ${dc.document}")
                        println("List : ${listDisorder}")

                    }
                }
                disorderAdapter.notifyDataSetChanged()
            }

        binding.RvDisorder.apply {
            adapter = disorderAdapter
            val mLayout = LinearLayoutManager(activity)
            mLayout.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayout
        }
    }

    private fun hideProgressBar() {
        binding.loading.visibility = View.INVISIBLE
    }
}