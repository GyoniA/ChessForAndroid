package com.gyoniA.chess

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.GyoniA.chess.databinding.FragmentLoadGameBinding


class LoadGameFragment : Fragment() {
    private lateinit var binding : FragmentLoadGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoadGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoad.setOnClickListener {
            val filename = binding.textFileName.text.toString()
            val bundle = Bundle()
            bundle.putString("filename", filename)
            findNavController().navigate(com.GyoniA.chess.R.id.action_loadGameFragment_to_menuFragment, bundle)
            //findNavController().navigate(com.GyoniA.chess.R.id.action_loadGameFragment_to_menuFragment)
        }
    }
}