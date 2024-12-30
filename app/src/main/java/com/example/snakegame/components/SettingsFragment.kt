package com.example.snakegame.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snakegame.R
import com.example.snakegame.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    interface OnResumeButtonClickListener {
        fun onResumeButtonClicked()
    }

    private var listener: OnResumeButtonClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure that the host Activity implements the interface
        if (context is OnResumeButtonClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.buttonQuit.setOnClickListener {
            val intent = Intent(requireContext(), StartActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }

        binding.buttonContinue.setOnClickListener {
            listener?.onResumeButtonClicked()
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }

        return binding.root
    }

}