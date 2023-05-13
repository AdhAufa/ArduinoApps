package com.example.arduinoapps.fragment

import android.os.Bundle
import android.provider.Telephony.Mms.Rate
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.arduinoapps.R
import com.example.arduinoapps.contracts.DetectionFragmentContract
import com.example.arduinoapps.databinding.FragmentDetectionBinding
import com.example.arduinoapps.databinding.FragmentHomeBinding
import com.example.arduinoapps.model.History
import com.example.arduinoapps.model.PredictResponse
import com.example.arduinoapps.presenters.FragmentDetectionPresenter
import com.example.arduinoapps.room.MyDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class DetectionFragment : Fragment(), DetectionFragmentContract.DetectionFragmentView {

    lateinit var history : History
    //instance db
    lateinit var myDb : MyDatabase

    private var heartmeter = 0.0
    private var oxymeter = 0.0

    //presenter
    private var presenter : DetectionFragmentContract.DetectionFragmentPresenter? = null

    //inisialisasi variabel untuk menampung id view
    private var _binding : FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetectionBinding.inflate(inflater, container, false)
        firebaseConection()
        presenter = FragmentDetectionPresenter(this)
//        myDb = MyDatabase.getInstance(requireContext())!!
        buttonDetectClicked()
        return binding.root
    }


    private fun firebaseConection(){
        //memanggil firebaseDatabase sebagai object
        val koneksi = FirebaseDatabase.getInstance().reference

        //membaca value dari realtime database firebase
        koneksi.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // variable untuk menampung data dari firebase
                val heartRate : String = snapshot.child("Heartrate").getValue().toString()
                val spo : String = snapshot.child("Oxymeter").getValue().toString()

                val heartRateStatus = snapshot.child("Heartrate").getValue().toString().toInt()
                val spoStatus = snapshot.child("Oxymeter").getValue().toString().toInt()

                oxymeter = snapshot.child("Oxymeter").getValue().toString().toDouble()
                heartmeter = snapshot.child("Heartrate").getValue().toString().toDouble()

                //menampilkan data ke view
                binding.apply {
                    tvNilaiHeartrate.setText(heartRate)
                    tvNilaiSpo.setText(spo)
                    TvStatus.setText(setStatus(heartRateStatus))
                    TvStatusSpo.setText(setStatus(spoStatus))
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setStatus(value : Int) : String{
        var status = "normal"
        if(value >= 100){
            status = "high"
        }else if(value <= 60){
            status = "Low"
        }else if(value > 60 && value <100 ){
            status = "normal"
        }
        return status
    }

    private fun saveHistory(){
        
    }

    private fun predict(){
        val jsonObject = JSONObject()
        jsonObject.put("oxygen", oxymeter)
        jsonObject.put("heart", heartmeter)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        presenter?.detect(requestBody)
    }

    private fun buttonDetectClicked(){
        binding.BtnDetect.setOnClickListener(){
            // ini bisa sekalian ngepost + save ke history
            predict()
            println("Oxymeter : ${oxymeter} & HeartRate : ${heartmeter}")
        }
    }

    override fun successDetect(data: PredictResponse) {
        binding.TVResultHypoxia.apply {
            setText(data.hypoxia.toString())
        }
        binding.TVResultCategory.apply {
            setText(data.category)
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroy()
    }

}