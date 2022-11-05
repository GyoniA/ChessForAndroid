package com.gyoniA.chess.model

import android.graphics.Point

class Gyalog(i: Int, j: Int, b: Boolean, tabla: Tabla?, k: Int) :
    Babu(i, j, b, tabla!!, k) {
    override fun HovaLephet(): Boolean {
        lepesiLehetosegek.clear()
        utesiLehetosegek.clear()
        val x = pozicio.x
        val y = pozicio.y
        if (feherE) {
            val balraFel = Point(x - 1, y - 1)
            val jobbraFel = Point(x + 1, y - 1)
            val egyetFel = Point(x, y - 1)
            if (TablanVanE(balraFel) && tab.GetFeketeBabuk().containsKey(balraFel)) {
                utesiLehetosegek.add(balraFel)
            }
            if (TablanVanE(jobbraFel) && tab.GetFeketeBabuk().containsKey(jobbraFel)) {
                utesiLehetosegek.add(jobbraFel)
            }
            if (TablanVanE(egyetFel) && !tab.GetFeketeBabuk()
                    .containsKey(egyetFel) && !tab.GetFeherBabuk().containsKey(egyetFel)
            ) {
                lepesiLehetosegek.add(egyetFel)
            }
            if (!lepettEMar) {
                val kettotFel = Point(x, y - 2)
                if (TablanVanE(kettotFel) && !tab.GetFeketeBabuk()
                        .containsKey(kettotFel) && !tab.GetFeherBabuk()
                        .containsKey(kettotFel) && !tab.GetFeketeBabuk()
                        .containsKey(egyetFel) && !tab.GetFeherBabuk().containsKey(egyetFel)
                ) {
                    lepesiLehetosegek.add(kettotFel)
                }
            }
        } else {
            val balraLe = Point(x - 1, y + 1)
            val jobbraLe = Point(x + 1, y + 1)
            val egyetLe = Point(x, y + 1)
            if (TablanVanE(balraLe) && tab.GetFeherBabuk().containsKey(balraLe)) {
                utesiLehetosegek.add(balraLe)
            }
            if (TablanVanE(jobbraLe) && tab.GetFeherBabuk().containsKey(jobbraLe)) {
                utesiLehetosegek.add(jobbraLe)
            }
            if (TablanVanE(egyetLe) && !tab.GetFeketeBabuk()
                    .containsKey(egyetLe) && !tab.GetFeherBabuk().containsKey(egyetLe)
            ) {
                lepesiLehetosegek.add(egyetLe)
            }
            if (!lepettEMar) {
                val kettotLe = Point(x, y + 2)
                if (TablanVanE(kettotLe) && !tab.GetFeketeBabuk()
                        .containsKey(kettotLe) && !tab.GetFeherBabuk()
                        .containsKey(kettotLe) && !tab.GetFeketeBabuk()
                        .containsKey(egyetLe) && !tab.GetFeherBabuk().containsKey(egyetLe)
                ) {
                    lepesiLehetosegek.add(kettotLe)
                }
            }
        }
        return !lepesiLehetosegek.isEmpty() || !utesiLehetosegek.isEmpty()
    }

    override fun Lepes(ujX: Int, ujY: Int): Boolean {
        val result = super.Lepes(ujX, ujY)
        if (feherE) {
            if (result && ujY == 0) {
                tab.GetFeherBabuk().put(Point(ujX, ujY), Vezer(ujX, ujY, feherE, tab, 2))
            }
        } else {
            if (result && ujY == 7) {
                tab.GetFeketeBabuk().put(Point(ujX, ujY), Vezer(ujX, ujY, feherE, tab, 3))
            }
        }
        return result
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}