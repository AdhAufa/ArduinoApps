package com.hypoxia.arduinoapps.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hypoxia.arduinoapps.activity.LoginActivity
import com.hypoxia.arduinoapps.activity.ProfileActivity
import com.hypoxia.arduinoapps.contracts.ProfileFragmentContract
import com.hypoxia.arduinoapps.databinding.FragmentProfileBinding
import com.hypoxia.arduinoapps.model.User
import com.hypoxia.arduinoapps.presenters.FragmentProfilePresenter
import com.hypoxia.arduinoapps.webservices.Constants

class ProfileFragment : Fragment(), ProfileFragmentContract.View {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var presenter : ProfileFragmentContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        presenter = FragmentProfilePresenter(this)
        btnLogout()
        detailProfile()
        return binding.root
    }

    private fun btnLogout(){
        binding.BtnLogout.setOnClickListener {
            showAlertDialogue()
        }
    }

    private fun detailProfile(){
        binding.TvDetail.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
    }

    private fun logout(){
        val intent = Intent(activity, LoginActivity::class.java).also {
            Constants.clearToken(requireActivity())
        }
        activity?.startActivity(intent)
        activity?.finish()
    }

    private fun showAlertDialogue(){
        binding.BtnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure ?")

            builder.setPositiveButton("Yes") { dialog, which ->
                logout()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }
    }


    override fun showUserToView(user: User) {
        binding.apply {
            TvName.text = user.nama_user
            TvEmail.text = user.email
            TvUsername.text = user.username
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun getProfile() {
        val token = Constants.getToken(requireActivity())
        presenter?.getUser(token)
    }

    override fun onResume() {
        super.onResume()
        getProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}