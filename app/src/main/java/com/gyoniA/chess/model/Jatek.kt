package com.gyoniA.chess.model

import android.widget.TextView
import com.gyoniA.chess.view.ChessView

class Jatek : Thread() {
    var view:ChessView? = null

    var tab: Tabla? = null

    var kivalasztott: Babu? = null

    @Volatile
    var feherJonE = true
    private var feherJonEBackup = true

    @Volatile
    var jatekmod = 0
    var feherOra: Ora
    @Volatile
    var whiteTV: TextView? = null
    var feketeOra: Ora
    @Volatile
    var blackTV: TextView? = null

    @Volatile
    var megyAJatek = false
    private var rob1: RobotJatekos? = null
    private var rob2: RobotJatekos? = null

    init {
        jatekmod = 0
        tab = Tabla()
        tab!!.BabukAlaphelyzetbe()
        feherOra = Ora(true, 300, this)
        feketeOra = Ora(false, 300, this)
        feherOra.start()
        feketeOra.start()
        if (feherJonE) {
            feherOra.Indit()
            feketeOra.Szunet()
        } else {
            feketeOra.Indit()
            feherOra.Szunet()
        }
    }

    fun onTouch(tX: Int, tY: Int) {
        if (kivalasztott != null) {
            kivalasztott!!.HovaLephet()
            if (kivalasztott!!.Lepes(tX, tY)) {
                feherJonE = !feherJonE
                if (feherJonE) {
                    feherOra.Indit()
                    feketeOra.Szunet()
                } else {
                    feketeOra.Indit()
                    feherOra.Szunet()
                }
            }
            kivalasztott = null
        } else {
            val p = Point(tX, tY)
            if (feherJonE) {
                if (tab!!.GetFeherBabuk().containsKey(p)) {
                    kivalasztott = tab!!.GetFeherBabuk()[p]
                    if (kivalasztott != null && kivalasztott!!.HovaLephet()) {
                        tab!!.LepesLegalizalas(kivalasztott!!)
                    } else {
                        kivalasztott = null
                    }
                }
            } else {
                if (tab!!.GetFeketeBabuk().containsKey(p)) {
                    kivalasztott = tab!!.GetFeketeBabuk()[p]
                    if (kivalasztott != null && kivalasztott!!.HovaLephet()) {
                        tab!!.LepesLegalizalas(kivalasztott!!)
                    } else {
                        kivalasztott = null
                    }
                }
            }
            if (kivalasztott != null && kivalasztott!!.GetLepesiLehetosegek()
                    .isEmpty() && kivalasztott!!.GetUtesiLehetosegek().isEmpty()
            ) {
                kivalasztott = null
            }
        }
        view?.invalidate()
    }
//TODO change to menu item

/* private inner class MenuActionListener : java.awt.event.ActionListener {
     fun actionPerformed(e: java.awt.event.ActionEvent) {
         val command: String = e.getActionCommand()
         val filenev = "sakk.ser"
         if (command == "Mentes") {
             val out: ObjectOutputStream
             try {
                 out = ObjectOutputStream(FileOutputStream(filenev))
                 out.writeObject(tab)
                 feherJonEBackup = feherJonE
                 feherOra!!.IdoMentes()
                 feketeOra!!.IdoMentes()
                 out.close()
             } catch (e1: FileNotFoundException) {
                 e1.printStackTrace()
             } catch (e2: IOException) {
                 e2.printStackTrace()
             }
         } else if (command == "Visszatoltes") {
             val `in`: ObjectInputStream
             try {
                 `in` = ObjectInputStream(FileInputStream(filenev))
                 tab = `in`.readObject() as Tabla
                 rob1!!.SetTabla(tab!!)
                 rob2!!.SetTabla(tab!!)
                 feherJonE = feherJonEBackup
                 feherOra!!.IdoVisszaallitas()
                 feketeOra!!.IdoVisszaallitas()
                 if (feherJonE) {
                     kijon = "A feh�r j�t�kos j�n"
                 } else {
                     kijon = "A fekete j�t�kos j�n"
                 }
                 tb.repaint()
                 `in`.close()
             } catch (e1: FileNotFoundException) {
                 e1.printStackTrace()
             } catch (e2: IOException) {
                 e2.printStackTrace()
             } catch (e3: ClassNotFoundException) {
                 e3.printStackTrace()
             }
         }
     }
 }*/

 override fun run() {
     rob1 = RobotJatekos(true, tab!!)
     rob2 = RobotJatekos(false, tab!!)
     megyAJatek = true
     while (megyAJatek) {
         try {
             if (jatekmod != 0) {
                 if (jatekmod == 1) {
                     if (!feherJonE) {
                         rob2!!.LepesGeneralas()
                         feherJonE = true
                         feherOra.Indit()
                         feketeOra.Szunet()
                         view?.invalidate()
                         view?.checkEndGame()
                         VegeVanMar()
                     }
                 } else {
                     if (feherJonE) {
                         rob1!!.LepesGeneralas()
                         feherJonE = false
                         feketeOra.Indit()
                         feherOra.Szunet()
                         view?.invalidate()
                     } else {
                         rob2!!.LepesGeneralas()
                         feherJonE = true
                         feherOra.Indit()
                         feketeOra.Szunet()
                         view?.invalidate()
                         view?.checkEndGame()
                     }
                     VegeVanMar()
                 }
             }
             sleep(100)
         } catch (e: InterruptedException) {
             e.printStackTrace()
             currentThread().interrupt()
         }
     }
 }

 fun ElfogyottAzIdo(feherE: Boolean) {
     if (feherE) {
         megyAJatek = false
         view?.checkEndGame(-1)
     } else {
         megyAJatek = false
         view?.checkEndGame(1)
     }
 }

 fun VegeVanMar(): Int {
     var allas = tab!!.VesztettE(true)
     if (allas == 1) {
         feherOra.Szunet()
         feketeOra.Szunet()
         megyAJatek = false
         return -1//black won
     } else if (allas == 2) {
         feherOra.Szunet()
         feketeOra.Szunet()
         megyAJatek = false
         return 0//draw
     } else {
         allas = tab!!.VesztettE(false)
         if (allas == 1) {
             feherOra.Szunet()
             feketeOra.Szunet()
             megyAJatek = false
             return 1//white won
         } else if (allas == 2) {
             feherOra.Szunet()
             feketeOra.Szunet()
             megyAJatek = false
             return 0//draw
         }
     }
     return 2//game is not over
 }

    fun setWhiteTimeView(whiteTime: TextView) {
        whiteTV = whiteTime
    }

    fun setBlackTimeView(blackTime: TextView) {
        blackTV = blackTime
    }

    fun backupGame(): GameBackup{
        return GameBackup(view,
        tab,

        kivalasztott,

        feherJonE,

        jatekmod,
        feherOra.ido,
        whiteTV,
        feketeOra.ido,
        blackTV,

        megyAJatek)
    }

    fun restoreFromBackup(gameBackup: GameBackup){
        view = gameBackup.view
        tab = gameBackup.tab

        kivalasztott = gameBackup.kivalasztott

        feherJonE = gameBackup.feherJonE

        jatekmod = gameBackup.jatekmod
        feherOra = Ora(true, gameBackup.feherOraIdeje, this)
        whiteTV = gameBackup.whiteTV
        feketeOra = Ora(false, gameBackup.feherOraIdeje, this)
        blackTV = gameBackup.blackTV

        megyAJatek = gameBackup.megyAJatek
        rob1 = RobotJatekos(true, tab!!)
        rob2 = RobotJatekos(false, tab!!)
    }
}