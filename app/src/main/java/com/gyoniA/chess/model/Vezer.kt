package com.gyoniA.chess.model

import android.graphics.Point

class Vezer(i: Int, j: Int, b: Boolean, tabla: Tabla?, k: Int) :
    Babu(i, j, b, tabla!!, k) {
    override fun HovaLephet(): Boolean {
        lepesiLehetosegek.clear()
        utesiLehetosegek.clear()
        val x = pozicio.x
        val y = pozicio.y
        var poz: Point
        var mehet: Boolean
        val iranyok = arrayOf(
            intArrayOf(1, 0),
            intArrayOf(-1, 0),
            intArrayOf(0, 1),
            intArrayOf(0, -1),
            intArrayOf(1, 1),
            intArrayOf(1, -1),
            intArrayOf(-1, 1),
            intArrayOf(-1, -1)
        )
        if (feherE) {
            for (irany in iranyok) {
                mehet = true
                poz = Point(x, y)
                poz.translate(irany[0], irany[1])
                while (TablanVanE(poz) && mehet) {
                    if (tab.GetFeketeBabuk().containsKey(poz)) {
                        utesiLehetosegek.add(Point(poz))
                        mehet = false
                    } else if (tab.GetFeherBabuk().containsKey(poz)) {
                        mehet = false
                    } else {
                        lepesiLehetosegek.add(Point(poz))
                    }
                    poz.translate(irany[0], irany[1])
                }
            }
        } else {
            for (irany in iranyok) {
                mehet = true
                poz = Point(x, y)
                poz.translate(irany[0], irany[1])
                while (TablanVanE(poz) && mehet) {
                    if (tab.GetFeherBabuk().containsKey(poz)) {
                        utesiLehetosegek.add(Point(poz))
                        mehet = false
                    } else if (tab.GetFeketeBabuk().containsKey(poz)) {
                        mehet = false
                    } else {
                        lepesiLehetosegek.add(Point(poz))
                    }
                    poz.translate(irany[0], irany[1])
                }
            }
        }
        return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}