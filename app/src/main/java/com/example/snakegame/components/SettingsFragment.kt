package com.example.snakegame.components

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snakegame.R
import com.example.snakegame.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentSettingsBinding
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
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }

        return binding.root
    }

}