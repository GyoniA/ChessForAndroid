package com.gyoniA.chess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.GyoniA.chess.R
import com.GyoniA.chess.databinding.FragmentStartMenuBinding

class StartMenuFragment : Fragment() {
    private lateinit var binding : FragmentStartMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_menuFragment)
        }
        binding.buttonLoadGame.setOnClickListener {
            findNavController().navigate(R.id.action_startMenuFragment_to_loadGameFragment)
        }
    }
}