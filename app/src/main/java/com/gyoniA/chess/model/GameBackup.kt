package com.gyoniA.chess.model

import android.widget.TextView
import com.gyoniA.chess.view.ChessView

data class GameBackup(var view: ChessView? = null,

                      var tab: Tabla? = null,

                      var kivalasztott: Babu? = null,

                      var feherJonE: Boolean = true,

                      var jatekmod: Int = 0,
                      var feherOraIdeje: Int,
                      var whiteTV: TextView? = null,
                      var feketeOraIdeje: Int,
                      var blackTV: TextView? = null,

                      var megyAJatek: Boolean = false) {

}