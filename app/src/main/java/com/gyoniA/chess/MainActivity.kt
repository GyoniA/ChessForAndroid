package com.gyoniA.chess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuCompat
import com.GyoniA.chess.R
import com.GyoniA.chess.databinding.ActivityMainBinding
import com.gyoniA.chess.view.GameFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var gameMode: Int? = null
    var listener: GameFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun setDefaultGameMode(mode: Int) {
        gameMode = mode
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        MenuCompat.setGroupDividerEnabled(menu, true);
        gameMode?.let {
            when(gameMode) {
                0 -> onOptionsItemSelected(menu.findItem(R.id.action_pvp)!!)
                1 -> onOptionsItemSelected(menu.findItem(R.id.action_pvai)!!)
                2 -> onOptionsItemSelected(menu.findItem(R.id.action_aivai)!!)
                else -> {}
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                listener?.onSaveGame()
                true
            }
            R.id.action_load -> {
                listener?.onLoadGame()
                true
            }

            R.id.action_pvp -> {
                item.isChecked = true
                gameMode = 0
                listener?.onGameModeChanged(0)
                true
            }

            R.id.action_pvai -> {
                item.isChecked = true
                gameMode = 1
                listener?.onGameModeChanged(1)
                true
            }

            R.id.action_aivai -> {
                item.isChecked = true
                gameMode = 2
                listener?.onGameModeChanged(2)
                true
            }

            R.id.action_default_texture -> {
                item.isChecked = true
                listener?.onDefaultTexture()
                true
            }

            R.id.action_alternate_texture -> {
                item.isChecked = true
                listener?.onAlternateTexture()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}