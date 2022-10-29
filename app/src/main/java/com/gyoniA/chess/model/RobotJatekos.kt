package com.gyoniA.chess.model

import java.util.*

class RobotJatekos(private val feherE: Boolean, private var tab: Tabla) {
    fun SetTabla(t: Tabla) {
        tab = t
    }

    fun LepesGeneralas() {
        val rand = Random()
        if (feherE) {
            val feherek: Array<Any> = tab.GetFeherBabuk().values.toTypedArray()
            for (i in feherek.indices) {
                val b = feherek[i] as Babu
                b.HovaLephet()
                tab.LepesLegalizalas(b)
                val ut = b.GetUtesiLehetosegek()
                if (!ut.isEmpty()) {
                    val p = ut[rand.nextInt(ut.size)]
                    b.Lepes(p.x, p.y)
                    return
                }
                val lep = b.GetLepesiLehetosegek()
                if (!lep.isEmpty()) {
                    val p = lep[rand.nextInt(lep.size)]
                    b.Lepes(p.x, p.y)
                    return
                }
            }
        } else {
            val feketek: Array<Any> = tab.GetFeketeBabuk().values.toTypedArray()
            for (i in feketek.indices) {
                val b = feketek[i] as Babu
                b.HovaLephet()
                tab.LepesLegalizalas(b)
                val ut = b.GetUtesiLehetosegek()
                if (!ut.isEmpty()) {
                    val p = ut[rand.nextInt(ut.size)]
                    b.Lepes(p.x, p.y)
                    return
                }
                val lep = b.GetLepesiLehetosegek()
                if (!lep.isEmpty()) {
                    val p = lep[rand.nextInt(lep.size)]
                    b.Lepes(p.x, p.y)
                    return
                }
            }
        }
    }
}