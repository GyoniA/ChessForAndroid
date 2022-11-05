package com.gyoniA.chess.model

import android.graphics.Point
import java.util.*

class Tabla{
    var feherBabuk = HashMap<Point, Babu>()
    var feketeBabuk = HashMap<Point, Babu>()
    var feherKiraly: Kiraly? = null
    var feketeKiraly: Kiraly? = null

    fun BabukAlaphelyzetbe() {
        feherBabuk.clear()
        feketeBabuk.clear()
        feketeBabuk[Point(0, 0)] = Bastya(0, 0, false, this, 9)
        feketeBabuk[Point(1, 0)] =
            Huszar(1, 0, false, this, 7)
        feketeBabuk[Point(2, 0)] = Futo(2, 0, false, this, 5)
        feketeBabuk[Point(3, 0)] =
            Vezer(3, 0, false, this, 3)
        feketeKiraly = Kiraly(4, 0, false, this, 1)
        feketeBabuk[Point(4, 0)] = feketeKiraly!!
        feketeBabuk[Point(5, 0)] = Futo(5, 0, false, this, 5)
        feketeBabuk[Point(6, 0)] =
            Huszar(6, 0, false, this, 7)
        feketeBabuk[Point(7, 0)] = Bastya(7, 0, false, this, 9)
        for (i in 0..7) {
            feketeBabuk[Point(i, 1)] = Gyalog(i, 1, false, this, 11)
        }
        feherBabuk[Point(0, 7)] = Bastya(0, 7, true, this, 8)
        feherBabuk[Point(1, 7)] =
            Huszar(1, 7, true, this, 6)
        feherBabuk[Point(2, 7)] = Futo(2, 7, true, this, 4)
        feherBabuk[Point(3, 7)] =
            Vezer(3, 7, true, this, 2)
        feherKiraly = Kiraly(4, 7, true, this, 0)
        feherBabuk[Point(4, 7)] = feherKiraly!!
        feherBabuk[Point(5, 7)] = Futo(5, 7, true, this, 4)
        feherBabuk[Point(6, 7)] =
            Huszar(6, 7, true, this, 6)
        feherBabuk[Point(7, 7)] = Bastya(7, 7, true, this, 8)
        for (i in 0..7) {
            feherBabuk[Point(i, 6)] = Gyalog(i, 6, true, this, 10)
        }
    }

    fun GetFeherBabuk(): HashMap<Point, Babu> {
        return feherBabuk
    }

    fun GetFeketeBabuk(): HashMap<Point, Babu> {
        return feketeBabuk
    }

    fun LepesLegalizalas(temp: Babu) {
        temp.HovaLephet()
        val tempLepettE = temp.lepettEMar
        val legalisLepesek = LinkedList<Point>()
        for (poz in temp.lepesiLehetosegek) {
            val feherMasolat = HashMap(feherBabuk)
            val feketeMasolat = HashMap(feketeBabuk)
            val tempPoz = Point(temp.GetPozicio())
            temp.Lepes(poz.x, poz.y)
            var legalis = true
            if (temp.GetFeherE()) {
                for (b in feketeBabuk.values) {
                    b.HovaLephet()
                    for (p in b.GetUtesiLehetosegek()) {
                        if (p.equals(feherKiraly!!.GetPozicio())) {
                            legalis = false
                        }
                    }
                }
            } else {
                for (b in feherBabuk.values) {
                    b.HovaLephet()
                    for (p in b.GetUtesiLehetosegek()) {
                        if (p.equals(feketeKiraly!!.GetPozicio())) {
                            legalis = false
                        }
                    }
                }
            }
            if (legalis) {
                legalisLepesek.add(poz)
            }
            feherBabuk = feherMasolat
            feketeBabuk = feketeMasolat
            temp.pozicio = tempPoz
        }
        val legalisUtesek = LinkedList<Point>()
        for (poz in temp.GetUtesiLehetosegek()) {
            val feherMasolat = HashMap(feherBabuk)
            val feketeMasolat = HashMap(feketeBabuk)
            val tempPoz = Point(temp.GetPozicio())
            temp.Lepes(poz.x, poz.y)
            var legalis = true
            if (temp.GetFeherE()) {
                for (b in feketeBabuk.values) {
                    b.HovaLephet()
                    for (p in b.GetUtesiLehetosegek()) {
                        if (p.equals(feherKiraly!!.GetPozicio())) {
                            legalis = false
                        }
                    }
                }
            } else {
                for (b in feherBabuk.values) {
                    b.HovaLephet()
                    for (p in b.GetUtesiLehetosegek()) {
                        if (p.equals(feketeKiraly!!.GetPozicio())) {
                            legalis = false
                        }
                    }
                }
            }
            if (legalis) {
                legalisUtesek.add(poz)
            }
            feherBabuk = feherMasolat
            feketeBabuk = feketeMasolat
            temp.pozicio = tempPoz
        }
        temp.lepesiLehetosegek = legalisLepesek
        temp.utesiLehetosegek = legalisUtesek
        temp.lepettEMar = tempLepettE
    }

    fun VesztettE(feherE: Boolean): Int {
        if (feherBabuk.size == 1 && feketeBabuk.size == 1) {
            return 2
        }
        if (feherE) {
            val feherek: Array<Any> = feherBabuk.values.toTypedArray()
            for (i in feherek.indices) {
                val b = feherek[i] as Babu
                b.HovaLephet()
                LepesLegalizalas(b)
                if (!b.GetLepesiLehetosegek().isEmpty() || !b.GetUtesiLehetosegek().isEmpty()) {
                    return 0 //nem vesztett
                }
            }
            val feketek: Array<Any> = feketeBabuk.values.toTypedArray()
            for (i in feketek.indices) {
                val b = feketek[i] as Babu
                b.HovaLephet()
                LepesLegalizalas(b)
                if (b.GetUtesiLehetosegek().contains(feherKiraly!!.GetPozicio())) {
                    return 1 //vesztett
                }
            }
        } else {
            val feketek: Array<Any> = feketeBabuk.values.toTypedArray()
            for (i in feketek.indices) {
                val b = feketek[i] as Babu
                b.HovaLephet()
                LepesLegalizalas(b)
                if (!b.GetLepesiLehetosegek().isEmpty() || !b.GetUtesiLehetosegek().isEmpty()) {
                    return 0 //nem vesztett
                }
            }
            val feherek: Array<Any> = feherBabuk.values.toTypedArray()
            for (i in feherek.indices) {
                val b = feherek[i] as Babu
                b.HovaLephet()
                LepesLegalizalas(b)
                if (b.GetUtesiLehetosegek().contains(feketeKiraly!!.GetPozicio())) {
                    return 1 //vesztett
                }
            }
        }
        return 2 //dontetlen
    }
}