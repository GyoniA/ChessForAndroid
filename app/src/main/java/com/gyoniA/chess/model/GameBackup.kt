package com.gyoniA.chess.model

import android.graphics.Point
import android.widget.TextView
import com.gyoniA.chess.view.ChessView
import java.util.HashMap

data class GameBackup(
                      //var tab: Tabla? = null,
                      //var feherBabuk: HashMap<String, Babu>,

                      //var feketeBabuk: HashMap<String, Babu>,
                      var feherBabuk: HashMap<Int, Babu>,

                      var feketeBabuk: HashMap<Int, Babu>,

                      var feherKiraly: Kiraly? = null,

                      var feketeKiraly: Kiraly? = null,


                      var kivalasztott: Babu? = null,

                      var feherJonE: Boolean = true,

                      var jatekmod: Int = 0,
                      var feherOraIdeje: Int,
                      var feketeOraIdeje: Int,

                      var megyAJatek: Boolean = false) {

}