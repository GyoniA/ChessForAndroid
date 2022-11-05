package com.gyoniA.chess.view

import android.R.attr.data
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.GyoniA.chess.R
import com.GyoniA.chess.databinding.FragmentGameBinding
import com.gyoniA.chess.MainActivity
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class GameFragment : Fragment() {
    private lateinit var binding : FragmentGameBinding

    lateinit var image: Bitmap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).listener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onDefaultTexture()//sets default texture for pieces

        binding.chessView.changeGameMode(arguments?.getInt("gameMode") ?: 0)
        (context as MainActivity).setDefaultGameMode(arguments?.getInt("gameMode") ?: 0)

        binding.chessView.whoseTurn = binding.textWhoseTurn
        binding.chessView.setWhiteTimeView(binding.textWhitesTime)
        binding.chessView.setBlackTimeView(binding.textBlacksTime)
    }

    fun runUIOperation(runnable: Runnable) {
        activity?.runOnUiThread(runnable)
    }
    fun endGame(result: Int){
        when (result) {
            -1 -> {
                Toast.makeText(context, "Black won!", Toast.LENGTH_SHORT).show()}

            0 -> {
                Toast.makeText(context, "Draw!", Toast.LENGTH_SHORT).show()}

            1 -> {
                Toast.makeText(context, "White won!", Toast.LENGTH_SHORT).show()}
        }
        findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
    }

    fun onGameModeChanged(i: Int) {
        binding.chessView.changeGameMode(i)
    }

    fun onSaveGame() {
        try {//TODO add file chooser
            val outputStreamWriter = OutputStreamWriter(context?.openFileOutput("save1.txt", Context.MODE_PRIVATE))
            outputStreamWriter.write(binding.chessView.getSaveData())
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    fun onLoadGame() {
        try {//TODO add file chooser
            val inputStreamReader = InputStreamReader(context?.openFileInput("save1.txt"))
            val saveData = inputStreamReader.readText()
            inputStreamReader.close()
            binding.chessView.loadFromSaveData(saveData)
        } catch (e: IOException) {
            Log.e("Exception", "File read failed: $e")
        }
    }

    fun onDefaultTexture() {
        image = BitmapFactory.decodeResource(context?.resources!!, R.mipmap.chess_pieces)
        binding.chessView.setImage(image)
    }

    fun onAlternateTexture() {
        image = BitmapFactory.decodeResource(context?.resources!!, R.mipmap.alternate_chess_pieces)
        binding.chessView.setImage(image)
    }


}