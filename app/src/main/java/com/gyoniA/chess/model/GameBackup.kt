package com.gyoniA.chess.model

import android.widget.TextView
import com.gyoniA.chess.view.ChessView

data class GameBackup(
                      var tab: Tabla? = null,

                      var kivalasztott: Babu? = null,

                      var feherJonE: Boolean = true,

                      var jatekmod: Int = 0,
                      var feherOraIdeje: Int,
                      var feketeOraIdeje: Int,

                      var megyAJatek: Boolean = false) {

}