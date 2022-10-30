package com.gyoniA.chess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.GyoniA.chess.R
import com.GyoniA.chess.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private lateinit var binding : FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPvP.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment0)
        }
        binding.buttonPvAI.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment1)
        }
        binding.buttonAIvAI.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment2)
        }
    }
}