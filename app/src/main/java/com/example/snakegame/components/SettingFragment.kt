package com.example.snakegame.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snakegame.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding

    interface GameResumeListener {
        fun onResumeButtonClicked()
    }

    private var listener: GameResumeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure that the host Activity implements the interface
        if (context is GameResumeListener) {
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
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.buttonQuit.setOnClickListener {
            val intent = Intent(requireContext(), StartActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }

        binding.buttonResume.setOnClickListener {
            listener?.onResumeButtonClicked()
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }

        return binding.root
    }

}