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
            val bundle = Bundle()
            bundle.putString("filename", arguments?.getString("filename"))
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment0, bundle)

            //findNavController().navigate(R.id.action_menuFragment_to_gameFragment0)
        }
        binding.buttonPvAI.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("filename", arguments?.getString("filename"))
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment1, bundle)
            //findNavController().navigate(R.id.action_menuFragment_to_gameFragment1)
        }
        binding.buttonAIvAI.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("filename", arguments?.getString("filename"))
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment2, bundle)
            //findNavController().navigate(R.id.action_menuFragment_to_gameFragment2)
        }
    }
}