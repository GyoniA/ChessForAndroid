package com.gyoniA.chess.model

import android.graphics.Point
import java.util.*

class Huszar : Babu {
    constructor(i: Int, j: Int, b: Boolean, tabla: Tabla?, k: Int) : super(i, j, b, tabla!!, k)
    constructor() : super()

    override fun HovaLephet(): Boolean {
        lepesiLehetosegek.clear()
        utesiLehetosegek.clear()
        val x = pozicio.x
        val y = pozicio.y
        val iranyok = LinkedList<Point>()
        iranyok.add(Point(x + 1, y + 2))
        iranyok.add(Point(x + 1, y - 2))
        iranyok.add(Point(x + 2, y + 1))
        iranyok.add(Point(x + 2, y - 1))
        iranyok.add(Point(x - 1, y + 2))
        iranyok.add(Point(x - 1, y - 2))
        iranyok.add(Point(x - 2, y + 1))
        iranyok.add(Point(x - 2, y - 1))
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
        }
        return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}