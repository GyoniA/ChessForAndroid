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
        tab = Tabla("chess_pieces.png")
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
        }/*
        var menuItem: javax.swing.JMenuItem = javax.swing.JMenuItem(
            "Ment�s",
            javax.swing.UIManager.getIcon("FileView.floppyDriveIcon")
        )
        menuItem.setActionCommand("Mentes")
        val mal = j.MenuActionListener()
        menuItem.addActionListener(mal)
        menu.add(menuItem)
        menuItem = javax.swing.JMenuItem(
            "Visszat�lt�s",
            javax.swing.UIManager.getIcon("FileView.hardDriveIcon")
        )
        menuItem.setActionCommand("Visszatoltes")
        menuItem.addActionListener(mal)
        menu.add(menuItem)
        val jal = j.JatekmodActionListener()
        menu.addSeparator()
        val group: javax.swing.ButtonGroup = javax.swing.ButtonGroup()
        var rbMenuItem: javax.swing.JRadioButtonMenuItem
        rbMenuItem = javax.swing.JRadioButtonMenuItem("Ember ember ellen")
        rbMenuItem.setActionCommand("0")
        rbMenuItem.addActionListener(jal)
        if (j.jatekmod == 0) {
            rbMenuItem.setSelected(true)
        }
        group.add(rbMenuItem)
        menu.add(rbMenuItem)
        rbMenuItem = javax.swing.JRadioButtonMenuItem(
            "Ember g�p ellen",
            javax.swing.UIManager.getIcon("FileView.computerIcon")
        )
        rbMenuItem.setActionCommand("1")
        rbMenuItem.addActionListener(jal)
        if (j.jatekmod == 1) {
            rbMenuItem.setSelected(true)
        }
        group.add(rbMenuItem)
        menu.add(rbMenuItem)
        rbMenuItem = javax.swing.JRadioButtonMenuItem(
            "G�p g�p ellen",
            javax.swing.UIManager.getIcon("FileView.computerIcon")
        )
        rbMenuItem.setActionCommand("2")
        rbMenuItem.addActionListener(jal)
        if (j.jatekmod == 2) {
            rbMenuItem.setSelected(true)
        }
        group.add(rbMenuItem)
        menu.add(rbMenuItem)
        tb.setJMenuBar(menuBar)
        tb.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
        tb.setResizable(false)
        tb.pack()
        tb.setVisible(true)*///TODO change to menu
    }

    fun OnTouch(tX: Int, tY: Int) {
        if (kivalasztott != null) {
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
/*
 private inner class JatekmodActionListener : java.awt.event.ActionListener {
     override fun actionPerformed(e: java.awt.event.ActionEvent) {
         jatekmod = e.getActionCommand().toInt()
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
}