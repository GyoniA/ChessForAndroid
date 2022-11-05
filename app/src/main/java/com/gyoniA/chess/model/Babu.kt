package com.gyoniA.chess.model

import android.graphics.Point
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*


fun Point.getX(): Int =  this.x
fun Point.getY(): Int =  this.y
fun Point.translate(dx: Int, dy: Int) {
    this.x += dx
    this.y += dy
}

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
abstract class Babu{

    @Transient @JsonIgnore var tab: Tabla
    var pozicio: Point
    var feherE: Boolean
    var lepettEMar = false
    @Transient @JsonIgnore var lepesiLehetosegek: LinkedList<Point> = LinkedList<Point>()
    @Transient @JsonIgnore var utesiLehetosegek: LinkedList<Point> = LinkedList<Point>()
    var KepIndex: Int

    constructor() {
        this.pozicio = Point(0, 0)
        this.feherE = true
        this.tab = Tabla()
        this.KepIndex = 0
    }

    constructor(x: Int, y: Int, iFeherE: Boolean, tab: Tabla = Tabla(), k: Int){
        pozicio = Point(x, y)
        feherE = iFeherE
        KepIndex = k
        this.tab = tab
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
    fun GetImageIndex(): Int {
        return KepIndex
    }

    //ha nem lehet sehova se lepni hamissal ter vissza, beallitja a lepesiLehetosegek listat
    abstract fun HovaLephet(): Boolean

    open fun Lepes(ujX: Int, ujY: Int): Boolean {
        val celpont = Point(ujX, ujY)
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