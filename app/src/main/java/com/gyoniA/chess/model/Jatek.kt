package com.gyoniA.chess.model

import android.graphics.Point
import android.widget.TextView
import com.gyoniA.chess.view.ChessView

class Jatek : Thread() {
    var view:ChessView? = null

    var tab: Tabla? = null

    var kivalasztott: Babu? = null

    @Volatile
    var feherJonE = true

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

    fun convertPointToIndex(point: Point): Int {
        return point.x * 1000 + point.y
    }

    fun convertPointMapToIntMap(map: HashMap<Point, Babu>): HashMap<Int, Babu> {
        val intMap = HashMap<Int, Babu>()
        for (key in map.keys) {
            intMap[convertPointToIndex(key)] = map[key]!!
        }
        return intMap
    }

    fun convertPointMapToStringMap(map: HashMap<Point, Babu>): HashMap<String, Babu> {
        val stringMap = HashMap<String, Babu>()
        for (key in map.keys) {
            stringMap[key.toString()] = map[key]!!
        }
        return stringMap
    }

    fun convertIntMapToPointMap(map: HashMap<Int, Babu>): HashMap<Point, Babu> {
        val pointMap = HashMap<Point, Babu>()
        for (key in map.keys) {
            pointMap[Point(key / 1000, key % 1000)] = map[key]!!
        }
        return pointMap
    }

    fun convertStringToPoint(key: String): Point {
        val data = key?.substringAfter("(", "")?.substringBefore(")", "")
        val coordinates = data?.split(",")
        val x = coordinates!![0].trim().toInt()
        val y = coordinates!![1].trim().toInt()
        return Point(x, y)
    }

    fun convertStringMapToPointMap(map: HashMap<String, Babu>): HashMap<Point, Babu> {
        val pointMap = HashMap<Point, Babu>()
        for (key in map.keys) {
            pointMap[convertStringToPoint(key)] = map[key]!!
        }
        return pointMap
    }

    fun backupGame(): GameBackup{
        return GameBackup(
            //tab?.let { convertPointMapToStringMap(it.feherBabuk) }!!,
            //tab?.let { convertPointMapToStringMap(it.feketeBabuk) }!!,
            tab?.let { convertPointMapToIntMap(it.feherBabuk) }!!,
            tab?.let { convertPointMapToIntMap(it.feketeBabuk) }!!,
            tab?.feherKiraly,
            tab?.feketeKiraly,

            kivalasztott,

            feherJonE,

            jatekmod,
            feherOra.ido,
            feketeOra.ido,

            megyAJatek)
    }

    fun restoreFromBackup(gameBackup: GameBackup){
        tab = Tabla()
        //tab!!.feherBabuk = convertStringMapToPointMap(gameBackup.feherBabuk)
        //tab!!.feketeBabuk = convertStringMapToPointMap(gameBackup.feketeBabuk)
        tab!!.feherBabuk = convertIntMapToPointMap(gameBackup.feherBabuk)
        tab!!.feketeBabuk = convertIntMapToPointMap(gameBackup.feketeBabuk)
        tab!!.feherKiraly = gameBackup.feherKiraly
        tab!!.feketeKiraly = gameBackup.feketeKiraly

        kivalasztott = gameBackup.kivalasztott

        feherJonE = gameBackup.feherJonE

        jatekmod = gameBackup.jatekmod

        feherOra.Szunet()
        feherOra.isRunning = false
        feketeOra.Szunet()
        feketeOra.isRunning = false

        feherOra = Ora(true, gameBackup.feherOraIdeje, this)
        feketeOra = Ora(false, gameBackup.feherOraIdeje, this)

        feherOra.start()
        feketeOra.start()

        megyAJatek = gameBackup.megyAJatek
        rob1 = RobotJatekos(true, tab!!)
        rob2 = RobotJatekos(false, tab!!)

        for (b in tab!!.feherBabuk.values) {
            b.tab = tab!!
        }
        for (b in tab!!.feketeBabuk.values) {
            b.tab = tab!!
        }

        tab!!.feherKiraly?.tab = tab!!
        tab!!.feketeKiraly?.tab = tab!!

        if (feherJonE) {
            feherOra.Indit()
            feketeOra.Szunet()
        } else {
            feketeOra.Indit()
            feherOra.Szunet()
        }
    }
}