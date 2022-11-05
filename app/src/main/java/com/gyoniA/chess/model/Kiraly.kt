package com.gyoniA.chess.model

import android.graphics.Point
import java.util.*

class Kiraly : Babu {
    constructor(i: Int, j: Int, b: Boolean, tabla: Tabla?, k: Int) : super(i, j, b, tabla!!, k)
    constructor() : super()

    override fun HovaLephet(): Boolean {
        lepesiLehetosegek.clear()
        utesiLehetosegek.clear()
        val x = pozicio.x
        val y = pozicio.y
        val iranyok = LinkedList<Point>()
        iranyok.add(Point(x, y + 1))
        iranyok.add(Point(x, y - 1))
        iranyok.add(Point(x + 1, y + 1))
        iranyok.add(Point(x + 1, y - 1))
        iranyok.add(Point(x + 1, y))
        iranyok.add(Point(x - 1, y + 1))
        iranyok.add(Point(x - 1, y - 1))
        iranyok.add(Point(x - 1, y))
        if (feherE) {
            for (p in iranyok) {
                if (TablanVanE(p)) {
                    if (!tab.GetFeherBabuk().containsKey(p)) {
                        if (!tab.GetFeketeBabuk().containsKey(p)) {
                            lepesiLehetosegek.add(p)
                        } else {
                            utesiLehetosegek.add(p)
                        }
                    }
                }
            }
            //s�ncol�s
            if (!lepettEMar) {
                val jobbBastya = tab.GetFeherBabuk()[Point(7, 7)]
                if (jobbBastya != null && !jobbBastya.lepettEMar) {
                    jobbBastya.HovaLephet()
                    if (jobbBastya.GetLepesiLehetosegek().contains(Point(5, 7))) {
                        lepesiLehetosegek.add(Point(6, 7))
                    }
                }
                val ballBastya = tab.GetFeherBabuk()[Point(0, 7)]
                if (ballBastya != null && !ballBastya.lepettEMar) {
                    ballBastya.HovaLephet()
                    if (ballBastya.GetLepesiLehetosegek().contains(Point(3, 7))) {
                        lepesiLehetosegek.add(Point(2, 7))
                    }
                }
            }
        } else {
            for (p in iranyok) {
                if (TablanVanE(p)) {
                    if (!tab.GetFeketeBabuk().containsKey(p)) {
                        if (!tab.GetFeherBabuk().containsKey(p)) {
                            lepesiLehetosegek.add(p)
                        } else {
                            utesiLehetosegek.add(p)
                        }
                    }
                }
            }
            //s�ncol�s
            if (!lepettEMar) {
                val jobbBastya = tab.GetFeketeBabuk()[Point(7, 0)]
                if (jobbBastya != null && !jobbBastya.lepettEMar) {
                    jobbBastya.HovaLephet()
                    if (jobbBastya.GetLepesiLehetosegek().contains(Point(5, 0))) {
                        lepesiLehetosegek.add(Point(6, 0))
                    }
                }
                val ballBastya = tab.GetFeketeBabuk()[Point(0, 0)]
                if (ballBastya != null && !ballBastya.lepettEMar) {
                    ballBastya.HovaLephet()
                    if (ballBastya.GetLepesiLehetosegek().contains(Point(3, 0))) {
                        lepesiLehetosegek.add(Point(2, 0))
                    }
                }
            }
        }
        return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty()
    }

    override fun Lepes(ujX: Int, ujY: Int): Boolean {
        val celpont = Point(ujX, ujY)
        if (lepesiLehetosegek.contains(celpont)) { //megnezi hogy tud-e lepni a megadott helyre
            if (feherE) {
                tab.GetFeherBabuk()[celpont] = this
                tab.GetFeherBabuk().remove(pozicio)
                if (!lepettEMar) {
                    if (ujX == 6 && ujY == 7) {
                        val jobbBastya = tab.GetFeherBabuk()[Point(7, 7)]
                        tab.GetFeherBabuk()[Point(5, 7)] = jobbBastya!!
                        tab.GetFeherBabuk().remove(Point(7, 7))
                        jobbBastya.pozicio = Point(5, 7)
                    } else if (ujX == 2 && ujY == 7) {
                        val ballBastya = tab.GetFeherBabuk()[Point(0, 7)]
                        tab.GetFeherBabuk()[Point(3, 7)] = ballBastya!!
                        tab.GetFeherBabuk().remove(Point(0, 7))
                        ballBastya.pozicio = Point(3, 7)
                    }
                }
            } else {
                tab.GetFeketeBabuk()[celpont] = this
                tab.GetFeketeBabuk().remove(pozicio)
                if (!lepettEMar) {
                    if (ujX == 6 && ujY == 0) {
                        val jobbBastya = tab.GetFeketeBabuk()[Point(7, 0)]
                        tab.GetFeketeBabuk()[Point(5, 0)] = jobbBastya!!
                        tab.GetFeketeBabuk().remove(Point(7, 0))
                        jobbBastya.pozicio = Point(5, 0)
                    } else if (ujX == 2 && ujY == 0) {
                        val ballBastya = tab.GetFeketeBabuk()[Point(0, 0)]
                        tab.GetFeketeBabuk()[Point(3, 0)] = ballBastya!!
                        tab.GetFeketeBabuk().remove(Point(0, 0))
                        ballBastya.pozicio = Point(3, 0)
                    }
                }
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
    }
}