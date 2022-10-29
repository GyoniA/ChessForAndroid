package com.gyoniA.chess.model

class Ora(private val feherE: Boolean, private var ido: Int, j: Jatek, jf: String) :
    Thread() {
    private var idoBackup: Int
    private var megyAzOra: Boolean
    private val jat: Jatek
    private var textfield: String

    init {
        idoBackup = ido
        jat = j
        textfield = jf
        if (feherE) {
            textfield = "White player's time: " + ido + "s"
        } else {
            textfield = "Black player's time: " + ido + "s"
        }
        megyAzOra = false
    }

    fun Szunet() {
        megyAzOra = false
    }

    fun Indit() {
        megyAzOra = true
    }

    fun IdoMentes() {
        idoBackup = ido
    }

    fun IdoVisszaallitas() {
        ido = idoBackup
    }

    override fun run() {
        var vanMegIdo = true
        while (vanMegIdo) {
            try {
                if (megyAzOra) {
                    if (ido != 0) {
                        ido--
                        if (feherE) {
                            textfield = "White player's time: " + ido + "s"
                        } else {
                            textfield = "Black player's time: " + ido + "s"
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