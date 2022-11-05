package com.gyoniA.chess.model

import com.GyoniA.chess.R


class Ora(private val feherE: Boolean, var ido: Int, j: Jatek) :
    Thread() {
    private var megyAzOra: Boolean
    private val jat: Jatek
    var isRunning: Boolean = true

    init {
        jat = j
        if (feherE) {

            jat.whiteTV?.text = jat.view?.context?.getString(R.string.whitePlayersTime, ido)
        } else {
            jat.blackTV?.text = jat.view?.context?.getString(R.string.blackPlayersTime, ido)
        }
        megyAzOra = false
    }

    fun Szunet() {
        megyAzOra = false
    }

    fun Indit() {
        megyAzOra = true
    }

    override fun run() {
        var vanMegIdo = true
        while (vanMegIdo && isRunning) {
            try {
                if (megyAzOra) {
                    if (ido != 0) {
                        ido--
                        if (feherE) {
                            jat.view?.updateWhiteTimeView(jat.view?.context?.getString(R.string.whitePlayersTime, ido))
                        } else {
                            jat.view?.updateBlackTimeView(jat.view?.context?.getString(R.string.blackPlayersTime, ido))
                        }
                    } else {
                        vanMegIdo = false
                        jat.ElfogyottAzIdo(feherE)
                    }
                }
                sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                currentThread().interrupt()
            }
        }
    }
}