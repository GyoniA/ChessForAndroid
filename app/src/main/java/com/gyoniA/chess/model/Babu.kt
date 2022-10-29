package com.gyoniA.chess.model

import android.graphics.Bitmap
import android.media.Image
import java.io.Serializable
import java.util.*

abstract class Babu(x: Int, y: Int, iFeherE: Boolean, protected var tab: Tabla, k: Int) :
    Serializable {
    var pozicio: Point
    protected var feherE: Boolean
    var lepettEMar = false
    var lepesiLehetosegek: LinkedList<Point> = LinkedList<Point>()
    var utesiLehetosegek: LinkedList<Point> = LinkedList<Point>()
    protected var KepIndex: Int

    init {
        pozicio = Point(x, y)
        feherE = iFeherE
        KepIndex = k
    }

    fun GetFeherE(): Boolean {
        return feherE
    }

    fun GetPozicio(): Point {
        return pozicio
    }

    fun GetLepesiLehetosegek(): LinkedList<Point> {
        return lepesiLehetosegek
    }

    fun GetUtesiLehetosegek(): LinkedList<Point> {
        return utesiLehetosegek
    }

    //visszadja a babu kepet, amit ki kell rajzolni
    fun GetImage(): Bitmap? {
        return tab.GetKep(KepIndex)
    }

    //ha nem lehet sehova se lepni hamissal ter vissza, beallitja a lepesiLehetosegek listat
    abstract fun HovaLephet(): Boolean

    open fun Lepes(ujX: Int, ujY: Int): Boolean {
        val celpont: Point = Point(ujX, ujY)
        if (lepesiLehetosegek.contains(celpont)) { //megnezi hogy tud-e lepni a megadott helyre
            if (feherE) {
                tab.GetFeherBabuk()[celpont] = this
                tab.GetFeherBabuk().remove(pozicio)
            } else {
                tab.GetFeketeBabuk()[celpont] = this
                tab.GetFeketeBabuk().remove(pozicio)
            }
            pozicio = celpont
            lepettEMar = true
            return true
        } else if (utesiLehetosegek.contains(celpont)) { //megnezi hogy tud-e utni
            if (feherE) {
                tab.GetFeherBabuk()[celpont] = this
                tab.GetFeketeBabuk().remove(celpont)
                tab.GetFeherBabuk().remove(pozicio)
            } else {
                tab.GetFeketeBabuk()[celpont] = this
                tab.GetFeherBabuk().remove(celpont)
                tab.GetFeketeBabuk().remove(pozicio)
            }
            pozicio = celpont
            lepettEMar = true
            return true
        }
        return false //nem tudott oda lepni a babu
    }

    companion object {
        private const val serialVersionUID = 1L
        fun TablanVanE(p: Point): Boolean {
            return p.getX() >= 0 && p.getX() < 8 && p.getY() >= 0 && p.getY() < 8
        }
    }
}